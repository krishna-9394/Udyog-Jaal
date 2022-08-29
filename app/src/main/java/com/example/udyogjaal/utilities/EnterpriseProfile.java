package com.example.udyogjaal.utilities;

import java.util.ArrayList;

public class EnterpriseProfile {
    private String enterpise_name,monthly_salary,contact_number;
    private ArrayList<String> field_name;
    private ArrayList<Boolean> field_status;

    public EnterpriseProfile(String enterpise_name, String monthly_salary, String contact_number, ArrayList<String> field_name, ArrayList<Boolean> field_status) {
        this.enterpise_name = enterpise_name;
        this.monthly_salary = monthly_salary;
        this.contact_number = contact_number;
        this.field_name = field_name;
        this.field_status = field_status;
    }

    public String getEnterpise_name() {
        return enterpise_name;
    }

    public void setEnterpise_name(String enterpise_name) {
        this.enterpise_name = enterpise_name;
    }

    public String getMonthly_salary() {
        return monthly_salary;
    }

    public void setMonthly_salary(String monthly_salary) {
        this.monthly_salary = monthly_salary;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
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
}
