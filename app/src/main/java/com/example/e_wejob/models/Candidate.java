package com.example.e_wejob.models;

import java.util.List;

public class Candidate {
    public int id;
    public String full_name;
    public String email;
    public String phone;
    public String experienceYears;
    public List<Diploma> diplomaList;

    public Candidate(int id, String full_name, String phone, String experienceYears, String email, List<Diploma> diplomaList) {
        this.id = id;
        this.full_name = full_name;
        this.phone = phone;
        this.email = email;
        this.experienceYears = experienceYears;
        this.diplomaList = diplomaList;
    }
}
