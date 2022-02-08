package com.company.utilities;

import com.company.exceptions.*;
import com.company.sourse.Color;
import com.company.sourse.Coordinates;
import com.company.sourse.DragonHead;


import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataController {
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
    private Double x;
    private long y;
    private String regex = "-?\\d+[.]\\d+";
    private Pattern pattern = Pattern.compile(regex);
    public static String[] temp = new String[14];
    public static String fileName;
    public static FileInputStream fis = null;
    public static  InputStreamReader isr = null;
    public static String line;
    public DataController(){
        System.out.println("id было сгенерировано автоматически. ");
        generateId();
        setName();
        setCoordinateX();
        setCoordinateY();
        this.coordinates = new Coordinates(x,y);
        generateCreationDate();
        System.out.println("Дата создания была сгенерирована автоматически. ");
        setAge();
        setWingspan();
        setWeight();
        setColor();
        setHead();
        System.out.println("Действие завершено.");
    }
    public DataController(String s){
        setName();
        setCoordinateX();
        setCoordinateY();
        this.coordinates = new Coordinates(x,y);
        setAge();
        generateCreationDate();
        setWingspan();
        setWeight();
        setColor();
        setHead();
        System.out.println("Действие завершено.");
    }
    public void generateId(){
        id = (long) (Math.random() * 1000);
        Set<Long> keys = CollectionController.getCollection().keySet();
        if (keys.contains(id)) generateId();
    }
    public void setName(){
        try {
            System.out.print("Введите имя: ");
            if (line != null) name = line;
            else name = scanner.nextLine();
            if ((name.trim().length() == 0) || name == null) throw new IncorrectNameException();
        }catch (IncorrectNameException e){
            System.out.println(e.toString());
            setName();
        }
    }
    public void setColor(){
        System.out.print("Выберите цвет из доступных: "+ Arrays.toString(Color.values()) + ": ");
        try {
            if (line != null)  this.color = Color.valueOf(line);
            else this.color = Color.valueOf(scanner.nextLine());
        }catch (IllegalArgumentException e){
            System.out.println("Ошибка. Введите константу из списка");
            setColor();
        }
    }
    public void setCoordinateX(){
        try {
            System.out.print("Введите координату по Х: ");
            try{
                String temp = "";
                if (line == null) temp = scanner.nextLine().replace(",", ".");
                else {
                    temp = line.replace(",", ".");
                }
                Double x = Double.valueOf(temp);
                if (x == null) throw new IncorrectCoordinateException();
                this.x = x;
            }catch (NumberFormatException e){
                System.out.println("Ошибка. Введите число. ");
                setCoordinateX();
            }
        }catch (IncorrectCoordinateException e){
            System.out.println(e.toString());
            setCoordinateX();
        }
    }
    public void setCoordinateY(){
            System.out.print("Введите координату по Y(>-666): ");
            try {
                String temp = "";
                if (line == null) temp = scanner.nextLine().replace(",",".");
                else temp = line.replace(",",".");
                Matcher matcher = pattern.matcher(temp);
                if (Double.parseDouble(temp) <= -666) throw new IncorrectCoordinateException();
                if (matcher.matches()) throw new NotIntegerException();
                this.y = Long.parseLong(temp);
            }catch (NumberFormatException r){
                System.out.println("Ошибка. Введите число. ");
                setCoordinateY();
            }catch (IncorrectCoordinateException e){
                System.out.println(e.toString());
                setCoordinateY();}
            catch (NotIntegerException ex){
                System.out.println(ex.toString());
                setCoordinateY();
            }
    }
    public void generateCreationDate(){
        this.creationDate = new Date();
    }
    public void setAge(){
            System.out.print("Введите возраст(>0): ");
            try {
                String temp = "";
                if (line == null) temp = scanner.nextLine();
                else temp = line;
                Matcher matcher = pattern.matcher(temp);
                temp = temp.replace(",",".");
                if (Double.parseDouble(temp) <= 0) throw new IncorrectAgeException();
                if (matcher.matches()) throw new NotIntegerException();
                age = Integer.parseInt(temp);
            }catch (NumberFormatException e){
                System.out.println("Ошибка. Введите число");
                setAge();
            }catch (NotIntegerException r){
                System.out.println(r.toString());
                setAge();
            }catch (IncorrectAgeException e) {
                System.out.println(e.toString());
                setAge();
            }
    }
    public void setWingspan(){
        try {
            System.out.print("Введите кол-во крыльев: ");
            try {
                if (line == null) wingspan = Float.valueOf(scanner.nextLine());
                else wingspan = Float.valueOf(line);
            }catch (NumberFormatException e){
                System.out.println("Ошибка. Введите число");
                setWingspan();
            }
            if (wingspan <= 0 || wingspan == null) throw new IncorrectWingspanException();
        }catch (IncorrectWingspanException e){
            System.out.println(e.toString());
            setWingspan();
        }
    }
    public void setWeight(){
            System.out.print("Введите вес: ");
            try {
                String temp = "";
                if (line == null) temp = scanner.nextLine().replace(",",".");
                else temp = line.replace(",",".");
                if (Double.parseDouble(temp) <= 0) throw new IncorrectWeightException();
                Matcher matcher = pattern.matcher(temp);
                if (matcher.matches()) throw new NotIntegerException();
                weight = Long.parseLong(temp);
            }catch (NumberFormatException e){
                System.out.println("Ошибка. Введите число");
                setWeight();
            }catch (IncorrectWeightException r){
                System.out.println(r.toString());
                setWeight();
            }catch (NotIntegerException ex){
                System.out.println(ex.toString());
                setWeight();
            }
    }
    public void setHead(){
        System.out.print("Введите размер головы: ");
        try {
            String temp = "";
            if (line == null) temp = scanner.nextLine().replace(",",".");
            else temp = line.replace(",",".");
            Matcher matcher = pattern.matcher(temp);
            if (matcher.matches()) throw new NotIntegerException();
            head = new DragonHead(Long.parseLong(temp));
        }catch (NumberFormatException e){
            System.out.println("Ошибка. Введите число");
            setHead();
        }catch (NotIntegerException ex){
            System.out.println(ex.toString());
            setHead();
        }
    }
    public long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public Date getCreationDate(){
        return creationDate;
    }
    public Coordinates getCoordinates(){
        return coordinates;
    }
    public int getAge(){
        return age;
    }
    public Color getColor(){
        return color;
    }
    public Float getWingspan(){
        return wingspan;
    }
    public long getWeight(){
        return weight;
    }
    public DragonHead getHead(){
        return head;
    }
    public long getHeadSize(){
        return head.getSize();
    }
    public Double getCoordinateX(){
        return coordinates.getX();
    }
    public long getCoordinateY(){
        return coordinates.getY();
    }
    @Override
    public boolean equals(Object o){
        return this == o;
    }
}
