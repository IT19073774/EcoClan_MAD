package com.example.ecoclan_v2;

public class TransactionList {

    String Amount, Date, Material, Email, Type, Weight,Color;

    public TransactionList() {
    }

    public TransactionList(String amount, String date, String material, String email, String type, String weight, String color) {
        Amount = amount;
        Date = date;
        Material = material;
        Email = email;
        Type = type;
        Weight = weight;
        Color = color;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }
}
