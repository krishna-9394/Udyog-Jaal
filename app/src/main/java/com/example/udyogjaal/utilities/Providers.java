package com.example.udyogjaal.utilities;

import java.util.ArrayList;

public class Providers{
    private String enterprise_name,monthly_salary;
    private ArrayList<Boolean> field_status, job_category_status, languages_known_status, skill_status;
    public Providers() {
    }
    public Providers(String enterprise_name, String monthly_salary, ArrayList<Boolean> field_status, ArrayList<Boolean> job_category_status, ArrayList<Boolean> languages_known_status, ArrayList<Boolean> skill_status) {
        this.enterprise_name = enterprise_name;
        this.monthly_salary = monthly_salary;
        this.field_status = field_status;
        this.job_category_status = job_category_status;
        this.languages_known_status = languages_known_status;
        this.skill_status = skill_status;
    }

    public String getEnterprise_name() {
        return enterprise_name;
    }

    public void setEnterprise_name(String enterprise_name) {
        this.enterprise_name = enterprise_name;
    }

    public String getMonthly_salary() {
        return monthly_salary;
    }

    public void setMonthly_salary(String monthly_salary) {
        this.monthly_salary = monthly_salary;
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
