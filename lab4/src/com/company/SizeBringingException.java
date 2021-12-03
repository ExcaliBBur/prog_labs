package com.company;

public class SizeBringingException extends Exception{
    public String toString(){
        return "Ошибка. Приведение оказалось НЕ маленьким.";
    }
}
