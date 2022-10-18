package com.gigawattstechnology.mydiary;

public class ImageMemorialModal {
    String date;
    String status;
    String url;

    public ImageMemorialModal(String date, String status, String url) {
        this.date = date;
        this.status = status;
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }
}
