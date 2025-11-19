package com.example.tpc_dcsa.model;

public class Company {
    private String id;
    private String name;
    private String location;
    private String roles;
    private String packageAmount;
    private String driveDate;
    private String status;

    public Company() {
        // Default constructor required for Firestore
    }

    public Company(String name, String location, String roles, String packageAmount, String driveDate, String status) {
        this.name = name;
        this.location = location;
        this.roles = roles;
        this.packageAmount = packageAmount;
        this.driveDate = driveDate;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getRoles() { return roles; }
    public void setRoles(String roles) { this.roles = roles; }

    public String getPackageAmount() { return packageAmount; }
    public void setPackageAmount(String packageAmount) { this.packageAmount = packageAmount; }

    public String getDriveDate() { return driveDate; }
    public void setDriveDate(String driveDate) { this.driveDate = driveDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

