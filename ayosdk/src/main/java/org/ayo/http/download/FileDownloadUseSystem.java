package org.ayo.http.download;


import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

public class FileDownloadUseSystem {
	@SuppressLint("NewApi")
	public static void download(Context context, String url, String savedFileName, String title, String desc) {
		DownloadManager downloadManager = (DownloadManager) context
				.getSystemService(Context.DOWNLOAD_SERVICE);
		String apkUrl = url; // "http://img.meilishuo.net/css/images/AndroidShare/Meilishuo_3.6.1_10006.apk";
		DownloadManager.Request request = new DownloadManager.Request(
				Uri.parse(apkUrl));
		request.setDestinationInExternalPublicDir(
				Environment.DIRECTORY_DOWNLOADS,
				savedFileName);
		request.setTitle(title);
		request.setDescription(desc);
		// request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		// request.setMimeType("application/com.trinea.download.file");
		long downloadId = downloadManager.enqueue(request);

	}
}
