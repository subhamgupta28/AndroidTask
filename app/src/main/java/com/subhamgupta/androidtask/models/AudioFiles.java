package com.subhamgupta.androidtask.models;

import java.util.Date;

public class AudioFiles {
    private String fileName;
    private String duration;
    private String filePath;
    private Date creationDate;

    public AudioFiles() {
    }

    public AudioFiles(String fileName, String duration, String filePath, Date creationDate) {
        this.fileName = fileName;
        this.duration = duration;
        this.filePath = filePath;
        this.creationDate = creationDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
