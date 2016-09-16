package com.snowy.demo.zhttp;

import android.content.Context;

import com.snowy.okhttp.BaseResponse;
import com.snowy.okhttp.OKHttpConfig;
import com.snowy.okhttp.OKHttpManager;
import com.snowy.okhttp.download.DownLoadTask;
import com.snowy.okhttp.download.DownloadManager;
import com.snowy.okhttp.download.DownloadTaskListener;

import java.io.File;

import okhttp3.Cache;

public class OkHttpUtils {

	public static void initOkHttpConfig(Context context) {
		// 自定义缓存目录和大小
		File cacheFile = new File(context.getCacheDir(), "okcache");
		Cache cache = new Cache(cacheFile, 100 * 1024 * 1024);// 100mb

		// 程序初始化时，初始okhttp配置
		OKHttpConfig OKHttpConfig = new OKHttpConfig.Builder()
				.setBaseResponseClass(BaseResponse.class)
				// .setLogLevel(HttpLoggingInterceptor.Level.BODY)// log level
				.setConnectTimeout(10) // connect time out
				.setReadTimeout(10) // read time out
				.setWriteTimeout(10) // write time out
				.setCacheTime(1000) // cache time
				.setCache(cache) // cache
				.build();
		OKHttpManager.init(context, OKHttpConfig);

		DownloadManager.init(context);
	}

	/**
	 * 下载文件，断点续传
	 * 
	 * @param id
	 * @param url
	 * @param saveDirPath
	 * @param fileName
	 * @param listener
	 * @param downloadManager
	 */
	public static void downloadFileWithBroken(String id, String url,
			String saveDirPath, String fileName, DownloadTaskListener listener,
			DownloadManager downloadManager) {
		DownLoadTask task = new DownLoadTask.Builder().setId(id).setUrl(url)
				.setListener(listener).setSaveDirPath(saveDirPath)
				.setFileName(fileName).build();
		downloadManager.addDownloadTask(task);
	}
}
