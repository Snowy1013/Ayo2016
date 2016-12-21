package com.snowy.demo.zhttp.download;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.snowy.demo.zhttp.OkHttpUtils;
import com.snowy.okhttp.download.DownLoadTask;
import com.snowy.okhttp.download.DownloadManager;
import com.snowy.okhttp.download.DownloadTaskListener;

import org.ayo.eventbus.EventBus;

import java.io.File;

/**
 * Created by zx on 16-9-16.
 */
public class DownloadService extends Service implements DownloadTaskListener {

    private DownloadManager downloadManager;
    private String saveDirPath;

    private DownloadEvent event;

    private final int START = 0;
    private final int PAUSE = 1;
    private final int CONTINNUE = 2;
    private final int CANCEL = 3;
    private final int DOWNLOADING = 4;
    private final int ERROR = 5;
    private final int DOWNLOADSUCCESS = 6;

    @Override
    public void onCreate() {
        super.onCreate();
        downloadManager = DownloadManager.getInstance();
        saveDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ayo";
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int status = intent.getIntExtra("status", 0);
        String id = intent.getStringExtra("id");
        String url = intent.getStringExtra("url");
        String name = intent.getStringExtra("name");
        switch (status) {
            case START:
                OkHttpUtils.downloadFileWithBroken(id, url, saveDirPath, name, this, downloadManager);
                break;
            case PAUSE:
                downloadManager.pause(id);
                break;
            case CONTINNUE:
                downloadManager.resume(id);
                break;
            case CANCEL:
                downloadManager.cancel(id);
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(DownLoadTask downloadTask) {

    }

    @Override
    public void onDownloading(DownLoadTask downloadTask, long completedSize, long totalSize, String percent) {
        event = new DownloadEvent(DOWNLOADING, downloadTask.getId());
        event.setPercent(percent);
        EventBus.getDefault().post(event);
    }

    @Override
    public void onPause(DownLoadTask downloadTask, long completedSize, long totalSize, String percent) {
        event = new DownloadEvent(PAUSE, downloadTask.getId());
        event.setPercent(percent);
        EventBus.getDefault().post(event);
    }

    @Override
    public void onCancel(DownLoadTask downloadTask) {
        event = new DownloadEvent(CANCEL, downloadTask.getId());
        EventBus.getDefault().post(event);
    }

    @Override
    public void onDownloadSuccess(DownLoadTask downloadTask, File file) {
        event = new DownloadEvent(DOWNLOADSUCCESS, downloadTask.getId());
        event.setFile(file);
        EventBus.getDefault().post(event);
    }

    @Override
    public void onError(DownLoadTask downloadTask, int errorCode) {
        event = new DownloadEvent(ERROR, downloadTask.getId());
        event.setErrorCode(errorCode);
        EventBus.getDefault().post(event);
    }
}
