package com.example.androidcodes.photoeditingapp.Frames.CustomGallery;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by peacock on 28/6/16.
 */
public class Albums implements Serializable {

    private String albumName, albumImgPath;

    private int albumImgCount;

    private ArrayList<String> allAlbumImgs;

    public Albums(String albumName, String albumImgPath, int albumImgCount,
                  ArrayList<String> allAlbumImgs) {

        this.albumName = albumName;

        this.albumImgPath = albumImgPath;

        this.albumImgCount = albumImgCount;

        this.allAlbumImgs = allAlbumImgs;

    }

    public String getAlbumName() {

        return albumName;

    }

    public void setAlbumName(String albumName) {

        this.albumName = albumName;

    }

    public String getAlbumImgPath() {

        return albumImgPath;

    }

    public void setAlbumImgPath(String albumImgPath) {

        this.albumImgPath = albumImgPath;

    }

    public int getAlbumImgCount() {

        return albumImgCount;

    }

    public void setAlbumImgCount(int albumImgCount) {

        this.albumImgCount = albumImgCount;

    }

    public ArrayList<String> getAllAlbumImgs() {

        return allAlbumImgs;

    }

    public void setAllAlbumImgs(ArrayList<String> allAlbumImgs) {

        this.allAlbumImgs = allAlbumImgs;

    }
}
