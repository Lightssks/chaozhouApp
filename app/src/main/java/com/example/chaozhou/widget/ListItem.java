package com.example.chaozhou.widget;

/**
 * Created by Administrator on 2018/3/16 0016.
 */

public class ListItem {

    private String listNameID;
    private String listContentID;

    public ListItem(int imageId, String listNameID, String listContentID, int imageButtonId, String imageButtonTextId) {
        this.listNameID = listNameID;
        this.listContentID = listContentID;
        this.imageId = imageId;
        this.imageButtonId = imageButtonId;
        this.imageButtonTextId = imageButtonTextId;
    }

    public String getListNameID() {
        return listNameID;

    }

    public void setListNameID(String listNameID) {
        this.listNameID = listNameID;
    }

    public String getListContentID() {
        return listContentID;
    }

    public void setListContentID(String listContentID) {
        this.listContentID = listContentID;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getImageButtonId() {
        return imageButtonId;
    }

    public void setImageButtonId(int imageButtonId) {
        this.imageButtonId = imageButtonId;
    }

    public String getImageButtonTextId() {
        return imageButtonTextId;
    }

    public void setImageButtonTextId(String imageButtonTextId) {
        this.imageButtonTextId = imageButtonTextId;
    }

    private int imageId;
    private int imageButtonId;
    private String imageButtonTextId;

}
