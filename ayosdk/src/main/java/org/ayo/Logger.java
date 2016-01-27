package org.ayo;

import org.ayo.file.Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Environment;
import android.util.Log;

/**
 * log utils
 * 
 */
public class Logger {
	/**
	 * develop phase
	 */
	public static final int DEVELOP = 0;
	/**
	 * inner test phase
	 */
	public static final int DEBUG = 1;
	/**
	 * public test phase
	 */
	public static final int BATE = 2;
	/**
	 * release phase
	 */
	public static final int RELEASE = 3;

	/**
	 * current phase
	 */
	private static int currentStage = DEVELOP;

	private static String path;
	private static File file;
	private static FileOutputStream outputStream;
	private static String pattern = "yyyy-MM-dd HH:mm:ss";
	
	
	
	public static void init(int logLevel){
		currentStage = logLevel;
		
		//创建log.txt：给Logger使用
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// File externalStorageDirectory =
			// Environment.getExternalStorageDirectory();
			path = Files.path.getFileInRoot("log.txt");
			File directory = new File(path);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			file = new File(new File(path), "log.txt");
			Log.i("SDCAEDTAG", path);
			try {
				outputStream = new FileOutputStream(file, true);
			} catch (FileNotFoundException e) {

			}
		} else {
			
		}
	}

	public static void info(String msg) {
		info("genius", msg);
	}

	public static void info(String tag, String msg) {
		switch (currentStage) {
		case DEVELOP:
			// 控制台输出
			Log.i(tag, msg);
			break;
		case DEBUG:
			// 在应用下面创建目录存放日志
			Log.i(tag, msg);
			logToFile(tag, msg);
			break;
		case BATE:
			// 写日志到sdcard
			logToFile(tag, msg);
			break;
		case RELEASE:
			// 一般不做日志记录
			break;
		}
	}
	
	
	private static void logToFile(String tag, String msg){
		Date date = new Date();
		String time = new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			if (outputStream != null) {
				try {
					outputStream.write(time.getBytes());
					outputStream.write(("    " + tag + "\r\n")
							.getBytes());

					outputStream.write(msg.getBytes());
					outputStream.write("\r\n".getBytes());
					outputStream.flush();
				} catch (IOException e) {

				}
			} else {
				Log.i("SDCAEDTAG", "file is null");
			}
		}
	}
	
	
}
