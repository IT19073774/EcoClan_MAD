package com.example.ecoclan_v2;

public class DataList {

    String ReqID, Name, Address, Contact, Category, Date, Time, CollectorEmail, Weight;

    public DataList() {
    }

    public DataList(String reqID, String name, String address, String contact, String category, String date, String time, String collectorEmail, String weight) {
        ReqID = reqID;
        Name = name;
        Address = address;
        Contact = contact;
        Category = category;
        Date = date;
        Time = time;
        CollectorEmail = collectorEmail;
        Weight = weight;
    }

    public String getReqID() {
        return ReqID;
    }

    public void setReqID(String reqID) {
        ReqID = reqID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getCollectorEmail() {
        return CollectorEmail;
    }

    public void setCollectorEmail(String collectorEmail) {
        CollectorEmail = collectorEmail;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }
}
