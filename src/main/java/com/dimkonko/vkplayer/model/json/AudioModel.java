package com.dimkonko.vkplayer.model.json;

public final class AudioModel {

    private final String aid;
    private final String artist;
    private final String title;
    private final String url;
    private final int duration; //203
    private String lyricsId; // "125325"
    private int genre; // 15

    public AudioModel(String aid, String artist, String title, String url, int duration) {
        this.aid = aid;
        this.artist = artist;
        this.title = title;
        this.url = url;
        this.duration = duration;
    }

    public String getSid() {
        return aid;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public int getDuration() {
        return duration;
    }

    public String getLyricsId() {
        return lyricsId;
    }

    public int getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return artist + " - " + title;
    }
}
