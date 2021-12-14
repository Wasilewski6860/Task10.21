package ru.vsu.baryshev;

public class PCTablet { // Класс планшета
    // Описываются поля планшета
    String nameOfModel = new String();
    int storage;
    int rating;
    int price;

    public PCTablet( String nameOfModel,int storage,int rating,int price){

        this.nameOfModel=nameOfModel;
        this.storage=storage;
        this.rating=rating;
        this.price=price;
    }
    public PCTablet(){

    }




}
