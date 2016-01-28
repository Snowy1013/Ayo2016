package org.ayo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import org.ayo.lang.Lang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;


/**
 * This class provided an interface which would be implemented by any third party image library.
 * Now the implementation is UniveralImageDownloader
 * 
 */
public class VanGogh {
	
	public static void main(String[] args) {
		ImageView iv = null;
		Context context = null;
		
		VanGogh.init(context);
		
		VanGogh.paper(iv)
			   .imageEmpty(1)
			   .imageError(2)
			   .imageLoading(3)
			   .paint("http://xxx.jpg", null, null);
	}
	
	
	private static int BIG_LOADING = 0;
	private static int BIG_ERROR = 0;
	private static int BIG_EMPTY = 0;
	private static int MIDDLE_LOADING = 0;
	private static int MIDDLE_ERROR = 0;
	private static int MIDDLE_EMPTY = 0;
	private static int SMALL_LOADING = 0;
	private static int SMALL_ERROR = 0;
	private static int SMALL_EMPTY = 0;
	
	/**
	 * init the module with some default base parameters
	 * @param context
	 */
	public static void init(Context context){
		DisplayImageOptions.Builder opt = new DisplayImageOptions.Builder();
		//opt.bitmapConfig(Bitmap.Config.ALPHA_8);
		opt.cacheInMemory(true);
		opt.cacheOnDisk(true);
		opt.considerExifParams(true);
		//opt.decodingOptions();- //?????
		//opt.delayBeforeLoading(delayInMillis);
		//opt.delayBeforeLoading(300);
		opt.displayer(new FadeInBitmapDisplayer(500));//CircleBitmapDisplayer, RoundedBitmapDisplayer, RoundedVignetteBitmapDisplayer, SimpleBitmapDisplayer
		//opt.extraForDownloader(Object);
		//opt.handler(Handler);
		//opt.imageScaleType(ImageView.ScaleType)

		//opt.preProcessor(BitmapProcessor);
		//opt.postProcessor(BitmapProcessor);
		//opt.resetViewBeforeLoading(true);
		//opt.showImageOnLoading(int|Drawable);
		//opt.showImageOnFail(int|Drawable);
		//opt.showImageForEmptyUri(int|Drawable);

		ImageLoaderConfiguration config = 
				new ImageLoaderConfiguration.Builder(context)
						.threadPriority(Thread.NORM_PRIORITY - 2)
						.denyCacheImageMultipleSizesInMemory()
					//.diskCacheFileNameGenerator(new SimpleFileNameGenerator())
						.memoryCacheExtraOptions(480, 800)
						.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
						.memoryCacheSize(2 * 1024 * 1024)
						.diskCacheSize(50 * 1024 * 1024) // 100
						.diskCacheFileNameGenerator(new Md5FileNameGenerator())
						.diskCacheFileCount(500)
						.tasksProcessingOrder(QueueProcessingType.LIFO)
						.writeDebugLogs() // Remove
						.threadPoolSize(2)
						.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout-5s, readTimeout-30s
						.defaultDisplayImageOptions(opt.build())
						.build();
		ImageLoader.getInstance().init(config);
	}
	
	/**
	 * init the replace image for big image
	 */
	public static void initImageBig(int loading, int error, int empty){
		BIG_LOADING = loading;
		BIG_ERROR = error;
		BIG_EMPTY = empty;
	}
	
	/**
	 * init the replace image for middle image
	 */
	public static void initImageMiddle(int loading, int error, int empty){
		MIDDLE_LOADING = loading;
		MIDDLE_ERROR = error;
		MIDDLE_EMPTY = empty;
	}
	
	/**
	 * init the replace image for small image
	 */
	public static void initImageSmall(int loading, int error, int empty){
		SMALL_LOADING = loading;
		SMALL_ERROR = error;
		SMALL_EMPTY = empty;
	}
	
	/**
	 * init the replace image for big image
	 */
	public static void initImageBig(int img){
		initImageBig(img, img, img);
	}
	
	/**
	 * init the replace image for middle image
	 */
	public static void initImageMiddle(int img){
		initImageMiddle(img, img, img);
	}
	
	/**
	 * init the replace image for small image
	 */
	public static void initImageSmall(int img){
		initImageSmall(img, img, img);
	}
	
	
	DisplayImageOptions options;
	DisplayImageOptions.Builder b = new DisplayImageOptions.Builder();
	
	ImageView iv;
	private VanGogh(ImageView iv){
		this.iv = iv;
		b = new DisplayImageOptions.Builder();
	}
	
	public VanGogh imageLoading(int resId){
		b.showImageOnLoading(resId);
		return this;
	}
	
	public VanGogh imageError(int resId){
		b.showImageOnFail(resId);
		return this;
	}
	
	public VanGogh imageEmpty(int resId){
		b.showImageForEmptyUri(resId);
		return this;
	}
	
	public static VanGogh paper(ImageView iv){
		return new VanGogh(iv);
	}
	
	public void paint(String url, ImageLoadingListener listener, ImageLoadingProgressListener progressListener){
		
		options = b.cacheInMemory(true)
				//.cacheOnDisk(true)
				//.considerExifParams(true)
				//.displayer(new FadeInBitmapDisplayer(1500))
				.build();
		
		if(iv == null || b == null){
			//throw new RuntimeException("Illegal Url");
		}

		if(Lang.isEmpty(url)){
			//throw new RuntimeException("Url is null);
			return;
		}
		
		if(isNotLocalPathOrRemotePath(url)){
			//throw new RuntimeException("Url is either local path nor remote url, cannot load image：" + url);
			return;
		}
		
		if(isLocalPath(url)){
			url = Uri.fromFile(new File(url)).toString();
			url = URLDecoder.decode(url);  /// Uri.fromFile would encdoe the chinese charators in url, should be decoded back
		}
		ImageLoader.getInstance().displayImage(url, iv, options, listener, progressListener);
	}
	
	public void paintBigImage(String url, ImageLoadingListener listener){
		
		this.imageEmpty(BIG_EMPTY)
		   .imageError(BIG_ERROR)
		   .imageLoading(BIG_LOADING)
		   .paint(url, listener, null);
	}
	
	public void paintMiddleImage(String url, ImageLoadingListener listener){
		
		this.imageEmpty(MIDDLE_EMPTY)
		   .imageError(MIDDLE_ERROR)
		   .imageLoading(MIDDLE_LOADING)
		   .paint(url, listener,null);
	}

	public void paintSmallImage(String url, ImageLoadingListener listener){
		
		this.imageEmpty(SMALL_EMPTY)
		   .imageError(SMALL_ERROR)
		   .imageLoading(SMALL_LOADING)
		   .paint(url, listener,null);
	}
	
	
	public static boolean isLocalPath(String url){
		if(!url.contains("http://") && !url.contains("https://") && !url.contains("file://") && !url.contains("files://")){
			return true;
		}else{
			return false;
		}
	}
	
	private static boolean isNotLocalPathOrRemotePath(String url){
		if(url == null || url.equals("")){
			return true;
		}
		if(!url.startsWith("/") && !url.startsWith("http")){
			return true;
		}else{
			return false;
		}
	}
	
	
	////---------------------------------------------------
	public static void loadImage(String url, final String savePath, final ImageLoadingListener listener){
		
		File f = ImageLoader.getInstance().getDiskCache().get(url);
		if(f != null && f.exists()){
			f.renameTo(new File(savePath));
			if(listener != null) {
				listener.onLoadingComplete(url, null, null);
			}
			return;
		}
		//loadImage(uri, targetImageSize, options, listener, progressListener)
		ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				if(listener != null) {
					listener.onLoadingStarted(imageUri, view);
				}
			}
			
			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				if(listener != null) {
					listener.onLoadingFailed(imageUri, view, failReason);
				}
			}
			
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				try {
					File f = new File(savePath);
					if(!f.exists()){
						f.createNewFile();
					}
					FileOutputStream out = new FileOutputStream(f);
					
					loadedImage.compress(CompressFormat.PNG, 100, out);
					out.flush();
					out.close();
					if(listener != null) {
						listener.onLoadingComplete(imageUri, view, loadedImage);
					}
				} catch (Exception e) {
					e.printStackTrace();
					if(listener != null) {
						listener.onLoadingFailed(imageUri, view, new FailReason(FailReason.FailType.IO_ERROR, e));
					}
				}
				
			}
			
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				if(listener != null) {
					listener.onLoadingCancelled(imageUri, view);
				}
			}
		});
	}
	
	
	///--------------------------
	
	public static int getExifOrientation(String filepath) {  
	    int degree = 0;  
	    ExifInterface exif = null;  
	    try {  
	        exif = new ExifInterface(filepath);  
	    } catch (IOException ex) {  
	        ex.printStackTrace();
	    }  
	    if (exif != null) {  
		    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);  
	        if (orientation != -1) {  
	            switch(orientation) {  
	                case ExifInterface.ORIENTATION_ROTATE_90:  
	                    degree = 90;  
	                    break;  
	                case ExifInterface.ORIENTATION_ROTATE_180:  
	                    degree = 180;  
	                    break;  
	                case ExifInterface.ORIENTATION_ROTATE_270:  
	                    degree = 270;  
	                    break;  
	            }  
	        }  
	    }  
	    return degree;  
	}  
	
	/**
	 * rotate bitmap
	 *
	 * @param bm
	 * @param degree
	 * @return new bitmap which is rotate from bm
	 */
	public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
		if(degree == 0 || degree == -1){
			return bm;
		}
	    Bitmap returnBm = null;
	  
	    // generate the matrix
	    Matrix matrix = new Matrix();
	    matrix.postRotate(degree);
	    try {
	        returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
	    } catch (OutOfMemoryError e) {
	    	e.printStackTrace();
	    }
	    if (returnBm == null) {
	        returnBm = bm;
	    }
	    if (bm != returnBm) {
	        bm.recycle();
	    }
	    return returnBm;
	}
	
	/**
	 * get the local cache path of the url
	 * @param url
	 * @return
	 */
	public static String getLocalCachePath(String url){
		try {
			return ImageLoader.getInstance().getDiskCache().get(url).getAbsolutePath();
		} catch (Exception e) {
			return "";
		}
	}
}
