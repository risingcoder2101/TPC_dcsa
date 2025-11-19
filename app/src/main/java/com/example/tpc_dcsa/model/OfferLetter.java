package com.example.tpc_dcsa.model;

public class OfferLetter {
    private String id;
    private String studentId;
    private String companyId;
    private String offerDate;
    private double packageAmount;

    public OfferLetter() {
        // Default constructor required for Firestore
    }

    public OfferLetter(String id, String studentId, String companyId, String offerDate, double packageAmount) {
        this.id = id;
        this.studentId = studentId;
        this.companyId = companyId;
        this.offerDate = offerDate;
        this.packageAmount = packageAmount;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public String getOfferDate() { return offerDate; }
    public void setOfferDate(String offerDate) { this.offerDate = offerDate; }

    public double getPackageAmount() { return packageAmount; }
    public void setPackageAmount(double packageAmount) { this.packageAmount = packageAmount; }
}

