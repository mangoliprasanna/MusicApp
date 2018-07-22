package com.example.mango.musicapp;

public class Song {
    private String songName;
    private String songArtist;
    private String songImageUrl;

    Song(String songName, String songArtist, String songImageUrl){
        this.songImageUrl = songImageUrl;
        this.songName = songName;
        this.songArtist = songArtist;
    }

    public void setSongImageUrl(String songImageUrl) {
        this.songImageUrl = songImageUrl;
    }
    public String getSongImageUrl() {
        return songImageUrl;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }
    public String getSongArtist() {
        return songArtist;
    }
    public String getSongName() {
        return songName;
    }

}
