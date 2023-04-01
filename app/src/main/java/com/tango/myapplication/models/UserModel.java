package com.tango.myapplication.models;

public class UserModel {
    private String empID;
    private String username;
    private String password;
    private String status;
    private String location;
    private String email;
    private String phone;
    private String address;

    public UserModel() {
    }

    public UserModel(String empID, String username, String location, String email, String phone) {
        this.empID = empID;
        this.username = username;
        this.location = location;
        this.email = email;
        this.phone = phone;
    }

    public UserModel(String username, String password, String status, String email, String phone, String address) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.location = "";
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }
}
