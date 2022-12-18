package com.capstone.grapediseaseapp;

public class youtubevideos {

    String videoUrl;
    public youtubevideos(){}

    public youtubevideos(String videoUrl){
        this.videoUrl=videoUrl;
    }
    public  String getVideourl(){
        return videoUrl;
    }
    public void setVideoUrl(String videoUrl){
        this.videoUrl=videoUrl;
    }

}
