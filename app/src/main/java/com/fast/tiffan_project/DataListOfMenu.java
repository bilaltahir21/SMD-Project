package com.fast.tiffan_project;

public class DataListOfMenu {
    String URI,cardview1_text,cardview2_text,menu_title,menu_description,price,price_description;

    public  DataListOfMenu()
    {

    }
    public DataListOfMenu(String URI, String cardview1_text, String cardview2_text, String menu_title, String menu_description, String price, String price_description) {
        this.URI = URI;
        this.cardview1_text = cardview1_text;
        this.cardview2_text = cardview2_text;
        this.menu_title = menu_title;
        this.menu_description = menu_description;
        this.price = price;
        this.price_description = price_description;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getCardview1_text() {
        return cardview1_text;
    }

    public void setCardview1_text(String cardview1_text) {
        this.cardview1_text = cardview1_text;
    }

    public String getCardview2_text() {
        return cardview2_text;
    }

    public void setCardview2_text(String cardview2_text) {
        this.cardview2_text = cardview2_text;
    }

    public String getMenu_title() {
        return menu_title;
    }

    public void setMenu_title(String menu_title) {
        this.menu_title = menu_title;
    }

    public String getMenu_description() {
        return menu_description;
    }

    public void setMenu_description(String menu_description) {
        this.menu_description = menu_description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice_description() {
        return price_description;
    }

    public void setPrice_description(String price_description) {
        this.price_description = price_description;
    }
}
