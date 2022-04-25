package com.example.firebasedata_demo.database;


import androidx.lifecycle.LiveData;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;


@androidx.room.Dao
public interface Dao {


    @Insert
    void insert(ImageModel model);


    @Delete
    public void deleteMessage(ImageModel imageModel);


    @Insert(onConflict = REPLACE)
    public void insertNewMessage(ImageModel imageModel);

    @Query("SELECT * FROM ImageData ORDER BY imageurl ASC")
    LiveData<List<ImageModel>> getAllImages();


}
