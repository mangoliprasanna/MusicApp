package com.example.mango.musicapp;

public class Artist {
    private String artistsName;
    private String artistsImageUrl;

    public Artist(String artistsName, String artistsImageUrl){
        this.artistsImageUrl = artistsImageUrl;
        this.artistsName = artistsName;
    }

    @Override
    public String toString(){
        return " NAME : " + artistsName;
    }

    public String getArtistsName() {
        return artistsName;
    }
    public String getArtistsImageUrl() {
        return artistsImageUrl;
    }


}
