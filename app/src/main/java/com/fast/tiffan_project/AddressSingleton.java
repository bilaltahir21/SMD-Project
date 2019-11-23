package com.fast.tiffan_project;

public class AddressSingleton {
    private static AddressSingleton single_instance = null;

    private String txt_city;
    private String txt_town;
    private String txt_street;
    private String txt_house;
    private String txt_address;
    private String txt_status;

    public String getStatus() {
        return txt_status;
    }

    public void setStatus(String txt_status) {
        this.txt_status = txt_status;
    }

    private AddressSingleton() {
        txt_city=null;
        txt_town=null;
        txt_street=null;
        txt_house=null;
        txt_address=null;
    }

    public static AddressSingleton get_Instance() {
        if (single_instance == null)
            single_instance = new AddressSingleton();
        return single_instance;
    }

    public String getmCity() {
        return txt_city;
    }

    public void setmCity(String txt_city) {
        this.txt_city = txt_city;
    }

    public String getmTown() {
        return txt_town;
    }

    public void setmTown(String txt_town) {
        this.txt_town = txt_town;
    }

    public String getmStreet() {
        return txt_street;
    }

    public void setmStreet(String txt_street) {
        this.txt_street = txt_street;
    }

    public String getmHouse() {
        return txt_house;
    }

    public void setmHouse(String txt_house) {
        this.txt_house = txt_house;
    }

    public String getmAddress() {
        return txt_address;
    }

    public void setmAddress(String txt_address) {
        this.txt_address = txt_address;
    }
}

