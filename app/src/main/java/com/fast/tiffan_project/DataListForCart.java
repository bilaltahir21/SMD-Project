package com.fast.tiffan_project;

public class DataListForCart {
    String URI, itemName, UnitPrice;
    private int Quantity, Discount;


    public DataListForCart() {

    }

    public DataListForCart(String URI, String itemName, String unitPrice, int Discount) {
        this.URI = URI;
        this.itemName = itemName;
        this.Discount = Discount;
        UnitPrice = unitPrice;
        Quantity = 1;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

}
