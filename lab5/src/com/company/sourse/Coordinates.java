package com.company.sourse;

public class Coordinates {
    private Double x; //Поле не может быть null
    private long y; //Значение поля должно быть больше -666
    public Coordinates(Double x, Long y){
        this.x = x;
        this.y = y;
    }
}
