package com.fast.tiffan_project;

import java.util.ArrayList;

public class DataListOfHistory {
    private String status, address;
    private ArrayList<DataListForCart> History_CartItems;

    DataListOfHistory(String status, String address, ArrayList<DataListForCart> History_CartItems) {
        this.status = status;
        this.address = address;
        this.History_CartItems = History_CartItems;
    }

    int getBill() {
        int bill = 0;
        for (int i = 0; i < this.History_CartItems.size(); i++) {
            int unitPrice = Integer.parseInt(this.History_CartItems.get(i).getUnitPrice());
            int discount = this.History_CartItems.get(i).getDiscount();
            int Qty = this.History_CartItems.get(i).getQuantity();
            int discountPrice = ((unitPrice * Qty) * discount) / 100;
            int price = (unitPrice * Qty) - discountPrice;
            bill += price;
        }
        return bill;
    }

    String getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<DataListForCart> getHistory_CartItems() {
        return History_CartItems;
    }
}