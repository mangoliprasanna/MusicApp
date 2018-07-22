package com.example.mango.musicapp;

import org.json.JSONException;
import org.json.JSONObject;

public class Artist {
    private String artistsName;
    private String artistsImageUrl;
    private String artistUrl;
    private String artistsListeners;
    private String artistSummary;

    public Artist(String artistsName, String artistUrl, String artistsImageUrl, String artistsListeners){
        this.artistsImageUrl = artistsImageUrl;
        this.artistsName = artistsName;
        this.artistUrl = artistUrl;
        this.artistsListeners = artistsListeners;
    }

    public Artist(){

    }

    @Override
    public String toString(){
        return " NAME : " + artistsName;
    }

    public String getArtistSummary() {
        return artistSummary;
    }

    public void setArtistSummary(String artistSummary) {
        this.artistSummary = artistSummary;
    }

    public String getArtistsListeners() {
        return artistsListeners;
    }
    public String getArtistUrl() {
        return artistUrl;
    }
    public String getArtistsName() {
        return artistsName;
    }
    public String getArtistsImageUrl() {
        return artistsImageUrl;
    }
    public void setArtistUrl(String artistUrl) {
        this.artistUrl = artistUrl;
    }

    public void setArtistsListeners(String artistsListeners) {
        this.artistsListeners = artistsListeners;
    }

    public void setArtistsImageUrl(String artistsImageUrl) {
        this.artistsImageUrl = artistsImageUrl;
    }

    public void readArtist(JSONObject artists) throws JSONException{
            this.artistUrl = artists.getString("url");
            this.artistsName = artists.getString("name");
    }

    public void setArtistsName(String artistsName) {
        this.artistsName = artistsName;
    }


}
