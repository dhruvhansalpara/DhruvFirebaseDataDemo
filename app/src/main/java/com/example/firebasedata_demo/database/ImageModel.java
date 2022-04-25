package com.example.firebasedata_demo.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ImageData")
public class ImageModel {

    @PrimaryKey(autoGenerate = true)
     int id;

     int loginId ;
     String imageurl;


    public ImageModel(String imageurl, int loginId) {
        this.imageurl = imageurl;
        this.loginId = loginId;
    }



    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
