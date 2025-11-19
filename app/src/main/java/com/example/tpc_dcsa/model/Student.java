package com.example.tpc_dcsa.model;

public class Student {
    private String name;
    private String rollNumber;
    private String gender;
    private String batch;
    private String email;
    private String phone;
    private String campus;
    private String percentage;

    public Student() {
        // Default constructor required for Firestore
    }

    public Student(String name, String rollNumber, String gender, String batch, String email, String phone, String campus, String percentage) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.gender = gender;
        this.batch = batch;
        this.email = email;
        this.phone = phone;
        this.campus = campus;
        this.percentage = percentage;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getBatch() { return batch; }
    public void setBatch(String batch) { this.batch = batch; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getCampus() { return campus; }
    public void setCampus(String campus) { this.campus = campus; }
    public String getPercentage() { return percentage; }
    public void setPercentage(String percentage) { this.percentage = percentage; }
}

