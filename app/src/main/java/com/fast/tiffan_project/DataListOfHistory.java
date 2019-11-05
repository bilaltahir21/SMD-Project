package com.fast.tiffan_project;

public class DataListOfHistory {
    private String status, Bill, address, date;

    public DataListOfHistory() {
    }

    public DataListOfHistory( String status, String Bill, String address, String date) {
        this.status = status;
        this.Bill = Bill;
        this.address = address;
        this.date = date;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public void setBill(String bill) {
        Bill = bill;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public String getBill() {
        return Bill;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }
}