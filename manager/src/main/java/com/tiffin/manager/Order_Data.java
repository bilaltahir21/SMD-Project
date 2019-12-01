package com.tiffin.manager;

import java.util.ArrayList;

public class Order_Data {
    private String status, address , UserName , Phone;
    private ArrayList<OrderDish> History_CartItems;

    Order_Data(String status, String address, ArrayList<OrderDish> History_CartItems , String UserName , String Phone) {
        this.status = status;
        this.address = address;
        this.History_CartItems = History_CartItems;
        this.UserName = UserName;
        this.Phone = Phone;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public ArrayList<OrderDish> getHistory_CartItems() {
        return History_CartItems;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}