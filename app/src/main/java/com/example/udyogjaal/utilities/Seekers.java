package com.example.udyogjaal.utilities;

import java.util.ArrayList;

public class Seekers {
    private String experience,sex,name,contact_number,address,image_url;
    private ArrayList<Boolean> field_status, job_category_status, languages_known_status, skill_status;
    private ArrayList<String> field_name, job_category_name, languages_known_name, skill_name;

    public Seekers() {
    }

    public Seekers(String address, String contact_number, String experience, ArrayList<String> field_name, ArrayList<Boolean> field_status,  String image_url, ArrayList<String> job_category_name, ArrayList<Boolean> job_category_status, ArrayList<String> languages_known_name, ArrayList<Boolean> languages_known_status, String name, String sex, ArrayList<String> skill_name, ArrayList<Boolean> skill_status) {
        this.experience = experience;
        this.sex = sex;
        this.name = name;
        this.contact_number = contact_number;
        this.address = address;
        this.image_url = image_url;
        this.field_status = field_status;
        this.job_category_status = job_category_status;
        this.languages_known_status = languages_known_status;
        this.skill_status = skill_status;
        this.field_name = field_name;
        this.job_category_name = job_category_name;
        this.languages_known_name = languages_known_name;
        this.skill_name = skill_name;
    }

    public String getImage_url() {
        return image_url;
    }
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getContact_number() {
        return contact_number;
    }
    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    public ArrayList<String> getField_name() {
        return field_name;
    }
    public void setField_name(ArrayList<String> field_name) {
        this.field_name = field_name;
    }

    public ArrayList<String> getJob_category_name() {
        return job_category_name;
    }
    public void setJob_category_name(ArrayList<String> job_category_name) {
        this.job_category_name = job_category_name;
    }

    public ArrayList<String> getLanguages_known_name() {
        return languages_known_name;
    }
    public void setLanguages_known_name(ArrayList<String> languages_known_name) {
        this.languages_known_name = languages_known_name;
    }

    public ArrayList<String> getSkill_name() {
        return skill_name;
    }
    public void setSkill_name(ArrayList<String> skill_name) {
        this.skill_name = skill_name;
    }

    public String getExperience() {
        return experience;
    }
    public void setExperience(String experience) {
        this.experience = experience;
    }

    public ArrayList<Boolean> getField_status() {
        return field_status;
    }
    public void setField_status(ArrayList<Boolean> field_status) {
        this.field_status = field_status;
    }

    public ArrayList<Boolean> getJob_category_status() {
        return job_category_status;
    }
    public void setJob_category_status(ArrayList<Boolean> job_category_status) {
        this.job_category_status = job_category_status;
    }

    public ArrayList<Boolean> getLanguages_known_status() {
        return languages_known_status;
    }
    public void setLanguages_known_status(ArrayList<Boolean> languages_known_status) {
        this.languages_known_status = languages_known_status;
    }

    public ArrayList<Boolean> getSkill_status() {
        return skill_status;
    }
    public void setSkill_status(ArrayList<Boolean> skill_status) {
        this.skill_status = skill_status;
    }
}
