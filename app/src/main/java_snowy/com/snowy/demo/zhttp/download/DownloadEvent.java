package com.snowy.demo.zhttp.download;

import java.io.File;

/**
 * Created by zx on 16-9-16.
 */
public class DownloadEvent {
    private int status;
    private String percent;
    private String id;
    private File file;
    private int errorCode;

    public DownloadEvent(int status, String id) {
        this.status = status;
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getStatus() {
        return status;
    }

    public String getPercent() {
        return percent;
    }

    public String getId() {
        return id;
    }

    public File getFile() {
        return file;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
