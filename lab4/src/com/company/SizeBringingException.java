package com.company;

public class SizeBringingException extends RuntimeException{
    public String toString(){
        return "Ошибка. Приведение оказалось НЕ маленьким.";
    }
}
