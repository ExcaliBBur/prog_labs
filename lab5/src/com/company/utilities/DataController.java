package com.company.utilities;

import com.company.exceptions.*;
import com.company.sourse.Color;
import com.company.sourse.Coordinates;
import com.company.sourse.DragonHead;


import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Data controller class
 */
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
    public static LinkedList<String> line = new LinkedList<>();

    /**
     * Set new dragon with generated id
     */
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

    /**
     * Set new dragon without generated id
     * @param s dragon`s id
     */
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

    /**
     * Method to generate id
     */
    public void generateId(){
        id = (long) (Math.random() * 1000);
        Set<Long> keys = CollectionController.getCollection().keySet();
        if (keys.contains(id)) generateId();
    }

    /**
     * Method to set name
     */
    public void setName(){
        try {
            if (DataController.line.size() != 0) TimeUnit.MILLISECONDS.sleep(1000);
            System.out.print("Введите имя: ");
            if (DataController.line.size() == 0) name = scanner.nextLine();
            else{
                name = DataController.line.getFirst();
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println(name);
                DataController.line.removeFirst();
            }
            if ((name.trim().length() == 0) || name == null) throw new IncorrectNameException();
        }catch (IncorrectNameException e){
            System.out.println(e.toString());
            if (DataController.line.size() != 0){
            }
            setName();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to set color
     */
    public void setColor(){
        if (DataController.line.size() != 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print("Выберите цвет из доступных: "+ Arrays.toString(Color.values()) + ": ");
        try {
            if (DataController.line.size() == 0) this.color = Color.valueOf(scanner.nextLine());
            else{
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println(DataController.line.getFirst());
                this.color = Color.valueOf(DataController.line.getFirst());
                DataController.line.removeFirst();
            }
        }catch (IllegalArgumentException e){
            System.out.println("Ошибка. Введите константу из списка");
            if (DataController.line.size() != 0){
                DataController.line.removeFirst();
            }
            setColor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to set X coordinate
     */
    public void setCoordinateX(){
        try {
            if (DataController.line.size() != 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print("Введите координату по Х: ");
            try{
                String temp = "";
                if (DataController.line.size() == 0) temp = scanner.nextLine().replace(",", ".");
                else{
                    TimeUnit.MILLISECONDS.sleep(1000);
                    temp = DataController.line.getFirst().replace(",",".");
                    DataController.line.removeFirst();
                    System.out.println(temp);
                }
                Double x = Double.valueOf(temp);
                if (x == null) throw new IncorrectCoordinateException();
                this.x = x;
            }catch (NumberFormatException e){
                System.out.println("Ошибка. Введите число. ");
                if (DataController.line.size() != 0){
                }
                setCoordinateX();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }catch (IncorrectCoordinateException e){
            System.out.println(e.toString());
            if (DataController.line.size() != 0){
            }
            setCoordinateX();
        }
    }

    /**
     * Method to set Y coordinate
     */
    public void setCoordinateY(){
        if (DataController.line.size() != 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            System.out.print("Введите координату по Y(>-666): ");
            try {
                String temp = "";
                if (DataController.line.size() == 0) temp = scanner.nextLine().replace(",",".");
                else{
                    TimeUnit.MILLISECONDS.sleep(1000);
                    temp = DataController.line.getFirst().replace(",",".");
                    DataController.line.removeFirst();
                    System.out.println(temp);
                }
                Matcher matcher = pattern.matcher(temp);
                if (Double.parseDouble(temp) <= -666) throw new IncorrectCoordinateException();
                if (matcher.matches()) throw new NotIntegerException();
                this.y = Long.parseLong(temp);
            }catch (NumberFormatException r){
                System.out.println("Ошибка. Введите число. ");
                if (DataController.line.size() != 0){
                }
                setCoordinateY();
            }catch (IncorrectCoordinateException e){
                System.out.println(e.toString());
                if (DataController.line.size() != 0){
                }
                setCoordinateY();}
            catch (NotIntegerException ex){
                System.out.println(ex.toString());
                if (DataController.line.size() != 0){
                }
                setCoordinateY();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    /**
     * Method to generate creation date
     */
    public void generateCreationDate(){
        this.creationDate = new Date();
    }

    /**
     * Method to set age
     */
    public void setAge(){
        if (DataController.line.size() != 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            System.out.print("Введите возраст(>0): ");
            try {
                String temp = "";
                if (DataController.line.size() == 0) temp = scanner.nextLine();
                else{
                    TimeUnit.MILLISECONDS.sleep(1000);
                    temp = DataController.line.getFirst();
                    DataController.line.removeFirst();
                    System.out.println(temp);
                }
                Matcher matcher = pattern.matcher(temp);
                temp = temp.replace(",",".");
                if (Double.parseDouble(temp) <= 0) throw new IncorrectAgeException();
                if (matcher.matches()) throw new NotIntegerException();
                age = Integer.parseInt(temp);
            }catch (NumberFormatException e){
                System.out.println("Ошибка. Введите число");
                if (DataController.line.size() != 0){
                }
                setAge();
            }catch (NotIntegerException r){
                System.out.println(r.toString());
                if (DataController.line.size() != 0){
                }
                setAge();
            }catch (IncorrectAgeException e) {
                System.out.println(e.toString());
                if (DataController.line.size() != 0){
                }
                setAge();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    /**
     * Method to set wingspan
     */
    public void setWingspan(){
        try {
            if (DataController.line.size() != 0) TimeUnit.MILLISECONDS.sleep(1000);
            System.out.print("Введите кол-во крыльев: ");
            try {
                if (DataController.line.size() == 0) wingspan = Float.valueOf(scanner.nextLine());
                else{
                    TimeUnit.MILLISECONDS.sleep(1000);
                    System.out.println(DataController.line.getFirst());
                    wingspan = Float.valueOf(DataController.line.getFirst());
                    DataController.line.removeFirst();
                }
            }catch (NumberFormatException e){
                System.out.println("Ошибка. Введите число");
                if (DataController.line.size() != 0){
                    DataController.line.removeFirst();
                }
                setWingspan();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (wingspan <= 0 || wingspan == null) throw new IncorrectWingspanException();
        }catch (IncorrectWingspanException e){
            System.out.println(e.toString());
            if (DataController.line.size() != 0){
            }
            setWingspan();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to set weight
     */
    public void setWeight(){
        if (DataController.line.size() != 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            System.out.print("Введите вес: ");
            try {
                String temp = "";
                if (DataController.line.size() == 0) temp = scanner.nextLine().replace(",",".");
                else{
                    TimeUnit.MILLISECONDS.sleep(1000);
                    temp = DataController.line.getFirst();
                    DataController.line.removeFirst();
                    System.out.println(temp);

                }
                if (Double.parseDouble(temp) <= 0) throw new IncorrectWeightException();
                Matcher matcher = pattern.matcher(temp);
                if (matcher.matches()) throw new NotIntegerException();
                weight = Long.parseLong(temp);
            }catch (NumberFormatException e){
                System.out.println("Ошибка. Введите число");
                if (DataController.line.size() != 0){
                }
                setWeight();
            }catch (IncorrectWeightException r){
                System.out.println(r.toString());
                if (DataController.line.size() != 0){
                }
                setWeight();
            }catch (NotIntegerException ex){
                System.out.println(ex.toString());
                if (DataController.line.size() != 0){
                }
                setWeight();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    /**
     * Method to set head
     */
    public void setHead(){
        if (DataController.line.size() != 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print("Введите размер головы: ");
        try {
            String temp = "";
            if (DataController.line.size() == 0) temp = scanner.nextLine().replace(",",".");
            else{
                TimeUnit.MILLISECONDS.sleep(1000);
                temp = DataController.line.getFirst();
                DataController.line.removeFirst();
                System.out.println(temp);
            }
            Matcher matcher = pattern.matcher(temp);
            if (matcher.matches()) throw new NotIntegerException();
            head = new DragonHead(Long.parseLong(temp));
        }catch (NumberFormatException e){
            System.out.println("Ошибка. Введите число");
            if (DataController.line.size() != 0){
            }
            setHead();
        }catch (NotIntegerException ex){
            System.out.println(ex.toString());
            if (DataController.line.size() != 0){
            }
            setHead();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to get id
     * @return id
     */
    public long getId(){
        return id;
    }

    /**
     * Method to get name
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * Method to get creation date
     * @return creation date
     */
    public Date getCreationDate(){
        return creationDate;
    }

    /**
     * Method to get coordinates
     * @return coordinates
     */
    public Coordinates getCoordinates(){
        return coordinates;
    }

    /**
     * Method to get age
     * @return age
     */
    public int getAge(){
        return age;
    }

    /**
     * Method to get color
     * @return color
     */
    public Color getColor(){
        return color;
    }

    /**
     * Method to get wingspan
     * @return wingspan
     */
    public Float getWingspan(){
        return wingspan;
    }

    /**
     * Method to get weight
     * @return weigth
     */
    public long getWeight(){
        return weight;
    }

    /**
     * Method to get head
     * @return head
     */
    public DragonHead getHead(){
        return head;
    }

    /**
     * Method to get head size
     * @return head size
     */
    public long getHeadSize(){
        return head.getSize();
    }

    /**
     * Method to get X coordinate
     * @return X coordinate
     */
    public Double getCoordinateX(){
        return coordinates.getX();
    }

    /**
     * Method to get Y coordinate
     * @return Y coordinate
     */
    public long getCoordinateY(){
        return coordinates.getY();
    }
    @Override
    public boolean equals(Object o){
        return this == o;
    }
}
