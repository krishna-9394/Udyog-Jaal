package com.example.udyogjaal.utilities;

import java.util.ArrayList;

public class Providers {
    private String enterprise_name, field, job_type;
    private int monthly_salary;
    private String job_category, available_post;
    private ArrayList<String> requirement;

    public Providers() {
    }

    public Providers(String enterprise_name, String field, String job_type, int monthly_salary, String job_category, String available_post, ArrayList<String> requirement) {
        this.enterprise_name = enterprise_name;
        this.field = field;
        this.job_type = job_type;
        this.monthly_salary = monthly_salary;
        this.job_category = job_category;
        this.available_post = available_post;
        this.requirement = requirement;
    }

    public String getEnterprise_name() {
        return enterprise_name;
    }

    public void setEnterprise_name(String enterprise_name) {
        this.enterprise_name = enterprise_name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public int getMonthly_salary() {
        return monthly_salary;
    }

    public void setMonthly_salary(int monthly_salary) {
        this.monthly_salary = monthly_salary;
    }

    public String getJob_category() {
        return job_category;
    }

    public void setJob_category(String job_category) {
        this.job_category = job_category;
    }

    public String getAvailable_post() {
        return available_post;
    }

    public void setAvailable_post(String available_post) {
        this.available_post = available_post;
    }

    public ArrayList<String> getRequirement() {
        return requirement;
    }

    public void setRequirement(ArrayList<String> requirement) {
        this.requirement = requirement;
    }
}
