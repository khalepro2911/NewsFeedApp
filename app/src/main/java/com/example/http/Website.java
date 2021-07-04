package com.example.http;

public class Website {
    private String title;
    private String URL;
    public Website(String title, String URL){
        this.title = title;
        this.URL = URL;
    }
    public String getTitle() {
        return title;
    }

    public String getURL(){
        return URL;
    }
    public void setURL(String URL){
        this.URL = URL;
    }


    public void setTitle(String title) {
        this.title = title;
    }
}
