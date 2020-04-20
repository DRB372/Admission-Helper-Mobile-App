package com.example.admission;

public class UniData {

    String  Uni_Name, Campus, Admission_Date, Website;
    int Uni_ID;

    public UniData() {
    }
public  UniData(String uni_Name)
{
    Uni_Name=uni_Name;
}
    public UniData(int id,String uni_Name, String campus, String admission_Date, String website) {
       Uni_ID=id;
        Uni_Name = uni_Name;
        Campus = campus;
        Admission_Date = admission_Date;
        Website = website;
    }

    public String getUni_Name() {
        return Uni_Name;
    }

    public void setUni_Name(String uni_Name) {
        Uni_Name = uni_Name;
    }

    public String getCampus() {
        return Campus;
    }

    public void setCampus(String campus) {
        Campus = campus;
    }

    public String getAdmission_Date() {
        return Admission_Date;
    }

    public void setAdmission_Date(String admission_Date) {
        Admission_Date = admission_Date;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }
}
