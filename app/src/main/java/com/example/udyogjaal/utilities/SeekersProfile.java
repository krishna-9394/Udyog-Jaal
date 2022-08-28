package com.example.udyogjaal.utilities;

import java.util.ArrayList;

public class SeekersProfile{
    private String imageUrl, name, experience;
    private ArrayList<String> field_name;
    private ArrayList<Boolean> field_status;

    public SeekersProfile(String imageUrl, String name, String experience, ArrayList<String> field_name, ArrayList<Boolean> field_status) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.experience = experience;
        this.field_name = field_name;
        this.field_status = field_status;
    }

    public SeekersProfile() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public ArrayList<String> getField_name() {
        return field_name;
    }

    public void setField_name(ArrayList<String> field_name) {
        this.field_name = field_name;
    }

    public ArrayList<Boolean> getField_status() {
        return field_status;
    }

    public void setField_status(ArrayList<Boolean> field_status) {
        this.field_status = field_status;
    }

    @Override
    public String toString() {
        return "SeekersProfile{" +
                "imageUrl='" + imageUrl + '\'' +
                ", name='" + name + '\'' +
                ", experience='" + experience + '\'' +
                ", field_name=" + field_name +
                ", field_status=" + field_status +
                '}';
    }
}
