package com.example.udyogjaal.utilities;

import java.util.ArrayList;

public class ProvidersProfile {
    private String name, contact_number;
    private ArrayList<Boolean> field_status;
    private ArrayList<String> field_name;

    public ProvidersProfile() {
    }

    public ProvidersProfile(String name, String contact_number, ArrayList<Boolean> field_status, ArrayList<String> field_name) {
        this.name = name;
        this.contact_number = contact_number;
        this.field_status = field_status;
        this.field_name = field_name;
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

    public ArrayList<Boolean> getField_status() {
        return field_status;
    }

    public void setField_status(ArrayList<Boolean> field_status) {
        this.field_status = field_status;
    }

    public ArrayList<String> getField_name() {
        return field_name;
    }

    public void setField_name(ArrayList<String> field_name) {
        this.field_name = field_name;
    }
}
