package com.company.utilities;

import com.company.sourse.Color;
import com.company.sourse.Coordinates;
import com.company.sourse.DragonHead;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class ConsoleController {
    Scanner scanner = new Scanner(System.in);
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int age; //Значение поля должно быть больше 0
    private Float wingspan; //Значение поля должно быть больше 0, Поле не может быть null
    private long weight; //Значение поля должно быть больше 0
    private Color color; //Поле не может быть null
    private DragonHead head;
    public ConsoleController(){
        System.out.println("id было сгенерировано автоматически");
        generateId();
        askName();
        askColor();
    }
    public void generateId(){
        id = (long) (Math.random() * 1000);
    }
    public void askName(){
        System.out.print("Введите имя: ");
        name = scanner.nextLine();
    }
    public void askColor(){
        System.out.print("Выберите цвет из доступных: "+ Color.list() + "\n");
        String string = scanner.nextLine();
        if (string.equals("RED")|| string.equals("BLACK") || string.equals("ORANGE") || string.equals("BROWN")){
            System.out.println("Yes.");
        }else System.out.println("No");
    }
    public void creationDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.creationDate = dtf.format(now);
    }
    @Override
    public boolean equals(Object o){
        return this == o;
    }
}
