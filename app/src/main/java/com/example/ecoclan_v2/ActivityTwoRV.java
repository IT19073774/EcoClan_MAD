package com.example.ecoclan_v2;

import android.widget.Button;

public class ActivityTwoRV {

   private  String UAddress ,UEmail , UFullName,  UPhone;


    private ActivityTwoRV(){
    }

    private ActivityTwoRV(String UAddress, String UEmail, String UFullName, String UPhone) {
        this.UAddress = UAddress;
        this.UEmail = UEmail;
        this.UFullName = UFullName;
        this.UPhone = UPhone;
    }


    public String getUAddress() {
        return UAddress;
    }

    public void setUAddress(String UAddress) {
        this.UAddress = UAddress;
    }

    public String getUEmail() {
        return UEmail;
    }

    public void setUEmail(String UEmail) {
        this.UEmail = UEmail;
    }

    public String getUFullName() {
        return UFullName;
    }

    public void setUFullName(String UFullName) {
        this.UFullName = UFullName;
    }

    public String getUPhone() {
        return UPhone;
    }

    public void setUPhone(String UPhone) {
        this.UPhone = UPhone;
    }
}
