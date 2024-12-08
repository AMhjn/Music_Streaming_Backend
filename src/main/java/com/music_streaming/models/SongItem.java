package com.music_streaming.models;

public class SongItem {
    private String title;
    private String songUrl;
    private String thumbnailUrl;
    private String channelTitle;

    // Constructor
    public SongItem(String title, String songUrl, String thumbnailUrl, String channelTitle) {
        this.title = title;
        this.songUrl = songUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.channelTitle = channelTitle;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }
}
