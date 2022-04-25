package com.example.firebasedata_demo;

public class Model {

    String imageUrl;


    public Model(){

    }

    public Model(String imageUrl,String name){
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
