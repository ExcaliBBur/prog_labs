package com.company.sourse;

public class Coordinates {
    private Double x; //Поле не может быть null
    private long y; //Значение поля должно быть больше -666

    /**
     * Set coordinates
     * @param x x
     * @param y y
     */
    public Coordinates(Double x, Long y){
        this.x = x;
        this.y = y;
    }

    /**
     * Method to get X coordinate
     * @return x
     */
    public Double getX(){
        return x;
    }

    /**
     * Method to get Y coordinate
     * @return y
     */
    public long getY(){
        return y;
    }
    @Override
    public String toString(){
        return "X: "+x +"; Y: "+y;
    }
}
