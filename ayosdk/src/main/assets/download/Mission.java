package download;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.ayo.Configer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by daimajia on 14-1-30.
 */
public class Mission implements Runnable {

	public static int ID = 0;

	private final int MissionID;
	private String Uri;
	private String SaveDir;
	private String SaveName;

	/// 是否需要断点续传
	private boolean needContinue = true;
	
	protected int mDownloaded;
	protected int mFileSize;

	private String mOriginFilename;
	private String mExtension;
	private String mShowName;
	private long mPreviousNotifyTime;

	private int mProgressNotifyInterval = 1;
	private TimeUnit mTimeUnit = TimeUnit.SECONDS;

	private long mLastSecondDownloadTime = 0;
	private long mLastSecondDownload = 0;

	private int mPreviousPercentage = -1;

	protected long mStartTime;
	protected long mEndTime;

	public enum RESULT_STATUS {
		STILL_DOWNLOADING, SUCCESS, FAILED
	}

	private RESULT_STATUS mResultStatus = RESULT_STATUS.STILL_DOWNLOADING;

	private boolean isDone = false;
	private boolean isStarted = false;
	private boolean isPaused = false;
	private boolean isSuccess = false;
	private boolean isCanceled = false;
	private final Object o = new Object();

	private ArrayList<MissionListener> missionListeners;
	private HashMap<String, Object> extras;

	/**
	 * 
	 * @param uri
	 *            the file url you want to download
	 * @param saveDir
	 *            save to which directory
	 */
	public Mission(String uri, String saveDir) {
		MissionID = uri.hashCode(); //ID++;
		Uri = uri;
		SaveDir = saveDir;

		mStartTime = System.currentTimeMillis();
		mPreviousNotifyTime = mStartTime;

		setOriginFileName(AlfredUtils.getFileName(uri));
		setExtension(AlfredUtils.getFileExtension(uri));

		SaveName = getOriginFileName() + "." + getExtension();
		missionListeners = new ArrayList<MissionListener>();
		extras = new HashMap<String, Object>();
		
		init();
	}

	/**
	 * 
	 * @param uri
	 *            the file url you want to download
	 * @param saveDir
	 *            save to which directory
	 * @param saveFilename
	 *            the file name you want to save as
	 */
	public Mission(String uri, String saveDir, String saveFilename) {
		this(uri, saveDir);
		SaveName = getSafeFilename(saveFilename);
	}
	public Mission(String uri, String saveDir, String saveFilename, boolean needContinue) {
		this(uri, saveDir);
		this.needContinue = needContinue;
		SaveName = getSafeFilename(saveFilename);
	}

	public Mission(String uri, String saveDir, String saveFilename,
			String showName) {
		this(uri, saveDir, saveFilename);
		this.mShowName = showName;
	}

	public void addMissionListener(MissionListener listener) {
		missionListeners.add(listener);
	}

	public void removeMissionListener(MissionListener listener) {
		missionListeners.remove(listener);
	}

	public void setProgressNotificateInterval(TimeUnit unit, int interval) {
		mTimeUnit = unit;
		mProgressNotifyInterval = interval;
	}

	public String getSavePath(){
		String dir = getSaveDir();
		if (dir.lastIndexOf(File.separator) != dir.length() - 1) {
			dir += File.separator;
		}
		File f = new File(dir + getSaveName());
		return f.getAbsolutePath();
	}
	
	private void init() {
		int current = 0;
		int total = 0;

		String dir = getSaveDir();
		if (dir.lastIndexOf(File.separator) != dir.length() - 1) {
			dir += File.separator;
		}
		File f = new File(dir + getSaveName());
		System.out.println("下载：本地路径设置为--" + f.getAbsolutePath());
		if (!f.exists()) {
			// /第一次下载
			// setFileSize(httpURLConnection.getContentLength());
			// total = mFileSize;
			// out = getRandomAccessFile(getSaveDir(),getSaveName(), total);
			// current = 0;
			// System.out.println("下载：第一次下载，文件大小--" + total + ", 当前进度--" +
			// current);
			total = 0;
			current = 0;
		} else {
			// /不是第一次下载
			RandomAccessFile out = getRandomAccessFile(getSaveDir(),
					getSaveName(), 0);
			current = getProgress();
			mDownloaded = current;
			try {
				total = (int) out.length();
			} catch (IOException e) {
				e.printStackTrace();
			}
			setFileSize(total);
			System.out.println("下载：非第一次下载，文件大小--" + total + ", 当前进度--"
					+ current);
		}
	}

	/**
	 * 不支持断点续传的下载，使用utils
	 * @param url
	 * @param localPath
	 */
	private void downloadWithoutRandomFile(String url, String localPath){
		HttpUtils http = new HttpUtils();
		url = url.replaceAll(" ","%20");
		http.download(url, localPath, true, false, new RequestCallBack<File>() {

			 @Override
	          public void onStart() {
	              //tvInfo.setText("正在连接...");
				 notifyStart();
	          }
			      
		      @Override
		      public void onLoading(long total, long current, boolean isUploading) {
		           //tvInfo.setText(current + "/" + total);
		    	  setFileSize((int) total);
		    	  mDownloaded = (int) current;
		    	  saveProgress(mDownloaded);
		    	  int progress = (int) (getPercentage()*100/getFilesize());
		    	  System.out.println("下载：通知进度(" + getMissionID() +")--" + progress + "%");
		    	  notifyPercentageChange();
		      }
		      
		      @Override
		      public void onFailure(HttpException error, String msg) {
		    	  try {
		    		  notifyError(error);
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						notifyFinish();
					}
		           
		      }

		      @Override
		      public void onSuccess(ResponseInfo<File> responseInfo) {
		    	  try {
		    		  notifySuccess();
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						notifyFinish();
					}
		      }
		});
	}
	
	@Override
	public void run() {
		
		if(!needContinue){
			downloadWithoutRandomFile(getUri(), getSavePath());
			return;
		}
		
		isStarted = true;
		notifyStart();
		BufferedInputStream in = null;
		RandomAccessFile out = null;
		HttpURLConnection httpURLConnection;
		try {
			httpURLConnection = (HttpURLConnection) new URL(Uri)
					.openConnection();

			int current = 0;
			int total = 0;

			String dir = getSaveDir();
			if (dir.lastIndexOf(File.separator) != dir.length() - 1) {
				dir += File.separator;
			}
			File f = new File(dir + getSaveName());
			System.out.println("下载：本地路径设置为--" + f.getAbsolutePath());
			if (!f.exists() || !needContinue) {
				// /第一次下载
				if(f.exists() && !needContinue){
					f.deleteOnExit();
				}
				int fileSize = httpURLConnection.getContentLength();
				setFileSize(fileSize);
				total = mFileSize;
				System.out.println("下载：第一次下载，文件大小--" + total + ", " + getUri());
				out = getRandomAccessFile(getSaveDir(), getSaveName(), total);
				current = 0;
				System.out.println("下载：第一次下载，文件大小--" + total + ", 当前进度--" + current);
			} else {
				// /不是第一次下载
				out = getRandomAccessFile(getSaveDir(), getSaveName(), 0);
				current = getProgress();
				total = (int) out.length();
				setFileSize(total);
				
				httpURLConnection.setRequestProperty("Range", "bytes="
						+ current + "-" + total);
				System.out.println("下载：非第一次下载，文件大小--" + total + ", 当前进度--"
						+ current);
				
				
			}
			
			//--原来的代码
			// httpURLConnection.setRequestProperty("Range",
			// "bytes=2097152-4194303");
			httpURLConnection.connect();
			in = new BufferedInputStream(httpURLConnection.getInputStream());
			
//			//--因为下载七牛出问题，改的代码
//			httpURLConnection.connect();
//			try {
//				in = new BufferedInputStream(httpURLConnection.getInputStream());
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//				///本来应该断点续传，但下载七牛的图片会抛出个fileNotFound
//				httpURLConnection = (HttpURLConnection) new URL(Uri)
//				.openConnection();
//				setFileSize(httpURLConnection.getContentLength());
//				total = mFileSize;
//				out = getRandomAccessFile(getSaveDir(), getSaveName(), total);
//				current = 0;
//				System.out.println("下载：第一次下载，文件大小--" + total + ", 当前进度--"
//						+ current);
//				in = new BufferedInputStream(httpURLConnection.getInputStream());
//			}
//			//// 修改over

			mDownloaded = current;
			out.seek(current);

			System.out.println("下载：通知进度--" + mDownloaded);
			notifyMetaDataReady();

			notifyPercentageChange();

			byte data[] = new byte[1024];
			int count;

			while (!isCanceled() && (count = in.read(data, 0, 1024)) != -1) {
				out.write(data, 0, count);
				mDownloaded += count;
				System.out.println("下载：通知进度(" + getMissionID() +")--" + mDownloaded);
				saveProgress(mDownloaded);
				notifyPercentageChange();
				notifySpeedChange();
				checkPaused();
			}

			if (isCanceled == false) {
				notifyPercentageChange();
				notifySuccess();
				System.out.println("下载: ok了");
			} else {
				System.out.println("下载: cancel了");
				//
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			notifyError(e);
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
				notifyError(e);
			}
			try {
				
				if(isCanceled){
					clearAfterCancel();
					notifyCancel();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			notifyFinish();
		}
	}

	private void clearAfterCancel(){
		try {
			File f = new File(getSavePath());
			System.out.println("取消下载：" + f.exists());
			if(f.exists()){
				System.out.println("取消下载：删除文件--" + getSavePath());
				if(f.delete()){
					System.out.println("取消下载：删除文件成功");
				}else{
					System.out.println("取消下载：删除文件失败");
				}
			}else{
				System.out.println("取消下载：没文件可删啊");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected FileOutputStream getSafeOutputStream(String directory,
			String filename) {
		String filepath;
		if (directory.lastIndexOf(File.separator) != directory.length() - 1) {
			directory += File.separator;
		}
		File dir = new File(directory);
		dir.mkdirs();
		filepath = directory + filename;
		File file = new File(filepath);
		try {
			file.createNewFile();
			return new FileOutputStream(file.getCanonicalFile().toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error("Can not get an valid output stream");
		}
	}

	protected RandomAccessFile getRandomAccessFile(String directory,
			String filename, int length) {
		String filepath;
		if (directory.lastIndexOf(File.separator) != directory.length() - 1) {
			directory += File.separator;
		}
		File dir = new File(directory);
		dir.mkdirs();
		filepath = directory + filename;
		File file = new File(filepath);
		try {
			if (file.exists()) {

			} else {
				RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
				accessFile.setLength(length);
				return accessFile;
			}
			return new RandomAccessFile(file.getCanonicalFile().toString(),
					"rw");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error("Can not get an valid RandomAccessFile");
		}
	}

	protected String getSafeFilename(String name) {
		return name.replaceAll("[\\\\|\\/|\\:|\\*|\\?|\\\"|\\<|\\>|\\|]", "-");
	}

	protected void checkPaused() {
		synchronized (o) {
			while (isPaused) {
				try {
					notifyPause();
					o.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void pause() {
		if (isPaused() || isDone())
			return;
		isPaused = true;
	}

	public void resume() {
		if (!isPaused() || isDone()) {
			return;
		}
		synchronized (o) {
			isPaused = false;
			o.notifyAll();
		}
		notifyResume();
	}

	public void cancel() {
		isCanceled = true;
		if (isPaused()) {
			resume();
		}
		saveProgress(0);
	}
	
	public final void notifyThingsAlreadDoneBeforeStart(){
		if (missionListeners != null && missionListeners.size() != 0) {
			int currentPercentage = getPercentage();
			for (MissionListener l : missionListeners) {
				l.onPercentageChange(this);
			}
			mPreviousPercentage = currentPercentage;
		}
	}

	protected final void notifyPercentageChange() {
		if (missionListeners != null && missionListeners.size() != 0) {
			int currentPercentage = getPercentage();
			if (mPreviousPercentage != currentPercentage) {
				for (MissionListener l : missionListeners) {
					l.onPercentageChange(this);
				}
				mPreviousPercentage = currentPercentage;
			}
		}
	}

	protected final void notifySpeedChange() {
		if (missionListeners != null && missionListeners.size() != 0) {
			long currentNotifyTime = System.currentTimeMillis();
			long notifyDuration = currentNotifyTime - mPreviousNotifyTime;
			long spend = mTimeUnit.convert(notifyDuration,
					TimeUnit.MILLISECONDS);
			if (spend >= mProgressNotifyInterval) {
				mPreviousNotifyTime = currentNotifyTime;
				for (MissionListener l : missionListeners) {
					l.onSpeedChange(this);
				}
			}
			long speedRecordDuration = currentNotifyTime
					- mLastSecondDownloadTime;
			if (TimeUnit.MILLISECONDS.toSeconds(speedRecordDuration) >= 1) {
				mLastSecondDownloadTime = currentNotifyTime;
				mLastSecondDownload = getDownloaded();
			}
		}
	}

	protected final void notifyStart() {
		if (missionListeners != null && missionListeners.size() != 0) {
			for (MissionListener l : missionListeners) {
				l.onStart(this);
			}
		}
	}

	protected final void notifyPause() {
		if (missionListeners != null && missionListeners.size() != 0) {
			for (MissionListener l : missionListeners) {
				l.onPause(this);
			}
		}
	}

	protected final void notifyResume() {
		if (missionListeners != null && missionListeners.size() != 0) {
			for (MissionListener l : missionListeners) {
				l.onResume(this);
			}
		}
	}

	protected final void notifyCancel() {
		isDone = true;
		if (missionListeners != null && missionListeners.size() != 0) {
			for (MissionListener l : missionListeners) {
				l.onCancel(this);
			}
		}
	}

	protected final void notifyMetaDataReady() {
		if (missionListeners != null && missionListeners.size() != 0) {
			for (MissionListener l : missionListeners) {
				l.onMetaDataPrepared(this);
			}
		}
	}

	protected final void notifySuccess() {
		mResultStatus = RESULT_STATUS.SUCCESS;
		isDone = true;
		isSuccess = true;
		if (missionListeners != null && missionListeners.size() != 0) {
			for (MissionListener l : missionListeners) {
				l.onSuccess(this);
			}
		}
	}

	protected final void notifyError(Exception e) {
		mResultStatus = RESULT_STATUS.FAILED;
		isDone = true;
		isSuccess = false;
		if (missionListeners != null && missionListeners.size() != 0) {
			for (MissionListener l : missionListeners) {
				l.onError(this, e);
			}
		}
	}

	protected final void notifyFinish() {
		mEndTime = System.currentTimeMillis();
		isDone = true;
		
		if (missionListeners != null && missionListeners.size() != 0) {
			for (MissionListener l : missionListeners) {
				l.onFinish(this);
			}
		}
		
		Alfred.getInstance().removeMission(MissionID);
		isStarted = false;
	}

	public String getUri() {
		return Uri;
	}

	public String getSaveDir() {
		return SaveDir;
	}

	public String getSaveName() {
		return SaveName;
	}

	public long getDownloaded() {
		return mDownloaded;
	}

	public long getFilesize() {
		return mFileSize;
	}

	protected void setFileSize(int size) {
		mFileSize = size;
	}

	public long getTimeSpend() {
		if (mEndTime != 0) {
			return mEndTime - mStartTime;
		} else {
			return System.currentTimeMillis() - mStartTime;
		}
	}

	public String getAverageReadableSpeed() {
		return AlfredUtils.getReadableSpeed(getDownloaded(), getTimeSpend(),
				TimeUnit.MILLISECONDS);
	}

	public String getAccurateReadableSpeed() {
		return AlfredUtils.getReadableSize(getDownloaded()
				- mLastSecondDownload, false)
				+ "/s";
	}

	public int getPercentage() {
		if (mFileSize == 0) {
			return 0;
		} else {
			return (int) (mDownloaded * 100.0f / mFileSize);
		}
	}

	public String getReadablePercentage() {
		StringBuilder builder = new StringBuilder();
		builder.append(getPercentage());
		builder.append("%");
		return builder.toString();
	}

	public void setOriginFileName(String name) {
		mOriginFilename = name;
	}

	public String getOriginFileName() {
		return mOriginFilename;
	}

	public void setExtension(String extension) {
		mExtension = extension;
	}

	public String getExtension() {
		return mExtension;
	}

	public String getShowName() {
		if (mShowName == null || mShowName.length() == 0) {
			return getSaveName();
		} else {
			return mShowName;
		}
	}

	public int getMissionID() {
		return MissionID;
	}

	public boolean isDone() {
		return isDone;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public boolean isPaused() {
		return isPaused;
	}
	
	public boolean isStarted() {
		return isStarted;
	}

	public boolean isCanceled() {
		return isCanceled;
	}

	public RESULT_STATUS getResultStatus() {
		return mResultStatus;
	}

	public void addExtraInformation(String key, Object value) {
		extras.put(key, value);
	}

	public void removeExtraInformation(String key) {
		extras.remove(key);
	}

	public Object getExtraInformation(String key) {
		return extras.get(key);
	}

	public interface MissionListener<T extends Mission> {
		/**
		 * when the run() start, Notice: do not call some get method, such as
		 * getFileType, getOriginFileName. Because these information is not
		 * ready, you can get them in onMetaDataPrepared.
		 * 
		 * @param mission
		 */
		public void onStart(T mission);

		/**
		 * when the download file meta information ready, such as the target
		 * file size, file type, video duration, and some other meta
		 * information.
		 * 
		 * @param mission
		 */
		public void onMetaDataPrepared(T mission);

		/**
		 * if you want to update your ui information, you can do in this
		 * function(notice: this function was been called in thread). You can
		 * involk getPercentage(),getAverageReadableSpeed(),and so on.
		 * 
		 * @param mission
		 */
		public void onPercentageChange(T mission);

		public void onSpeedChange(T mission);

		/**
		 * when error occurs, onError() will be called;
		 * 
		 * @param mission
		 *            the mission which trigger the error function
		 * @param e
		 *            the exception.
		 */
		public void onError(T mission, Exception e);

		/**
		 * called when download successfully
		 * 
		 * @param mission
		 */
		public void onSuccess(T mission);

		/**
		 * no matter success or failed, this function will be call in the end.
		 * You can do some clean up or some other things.
		 * 
		 * @param mission
		 */
		public void onFinish(T mission);

		public void onPause(T mission);

		public void onResume(T mission);

		public void onCancel(T mission);
	}

	private int getProgress() {
		return Configer.getInstance().get(Uri, 0);
	}

	private void saveProgress(int progress) {
		Configer.getInstance().put(Uri, progress);
	}
}
