package com.company.exceptions;

public class IncorrectWingspanException extends Exception{
    @Override
    public String toString(){
        return "Ошибка. Кол-во крыльев должно быть больше нуля ";
    }
}
