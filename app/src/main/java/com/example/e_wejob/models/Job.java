package com.example.e_wejob.models;

public class Job {

    public int id;
    public int companyId;
    public String title;
    public String salary;
    public String requiredEducationLevel;
    public int requiredExperienceYears;
    public String createdAt;
    public String updatedAt;

    public Job(int id, int companyId, String title, String salary, String requiredEducationLevel, int requiredExperienceYears, String createdAt, String updatedAt) {
        this.id = id;
        this.companyId = companyId;
        this.title = title;
        this.salary = salary;
        this.requiredEducationLevel = requiredEducationLevel;
        this.requiredExperienceYears = requiredExperienceYears;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
