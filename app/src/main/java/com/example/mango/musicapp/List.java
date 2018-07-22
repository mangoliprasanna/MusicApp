package com.example.mango.musicapp;

public class List {
    private String listTile;
    private String listSubTitle;
    private int listImageResourceLeft;
    private int listImageResourceRight;

    List(String listTile, String listSubTitle, int listImageResourceLeft, int listImageResourceRight){
        this.listImageResourceLeft = listImageResourceLeft;
        this.listImageResourceRight = listImageResourceRight;
        this.listSubTitle = listSubTitle;
        this.listTile = listTile;
    }

    public int getListImageResourceRight() {
        return listImageResourceRight;
    }

    public int getListImageResourceLeft() {
        return listImageResourceLeft;
    }

    public String getListTile() {
        return listTile;
    }

    public String getListSubTitle() {
        return listSubTitle;
    }

    public void setListSubTitle(String listSubTitle) {
        this.listSubTitle = listSubTitle;
    }

    public void setListTile(String listTile) {
        this.listTile = listTile;
    }
}
