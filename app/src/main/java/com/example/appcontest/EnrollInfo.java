package com.example.appcontest;

public class EnrollInfo {

    String name;
    String title;
    String contents;
    String price;
    String harvest;
    String date;
    String location;
    String uri;

    public EnrollInfo(){

    }
    public EnrollInfo(String title, String name, String date, String price, String location, String harvest, String contents, String uri){
        this.title = title;
        this.name = name;
        this.date = date;
        this.price = price;
        this.location = location;
        this.harvest = harvest;
        this.contents = contents;
        this.uri = uri;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHarvest() {
        return harvest;
    }

    public void setHarvest(String harvest) {
        this.harvest = harvest;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
