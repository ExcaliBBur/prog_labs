package com.company.sourse;

public class Dragon {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int age; //Значение поля должно быть больше 0
    private Float wingspan; //Значение поля должно быть больше 0, Поле не может быть null
    private long weight; //Значение поля должно быть больше 0
    private Color color; //Поле не может быть null
    private DragonHead head;

    public Dragon(long id, String name, Coordinates coordinates, java.util.Date creationDate, int age, Float wingspan,
                  long weight, Color color, DragonHead head){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.age = age;
        this.wingspan = wingspan;
        this.weight = weight;
        this.color = color;
        this.head = head;
    }
    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public Coordinates getCoordinates(){
        return coordinates;
    }
    public java.util.Date getCreationDate(){
        return creationDate;
    }
    public int getAge(){
        return age;
    }
    public Float getWingspan(){
        return wingspan;
    }
    public Long getWeight(){
        return weight;
    }
    public Color getColor(){
        return color;
    }
    public DragonHead getHead(){
        return head;
    }
    @Override
    public String toString(){
        String string = "id: "+ id +"\n name: " + name + "\n coordinates: "+coordinates+"\n creationDate: "+creationDate +
                "\n age: " + age + "\n wingspan: " + wingspan + "\n weight: " + weight + "\n color: " + color + "\n head: " + head;
        return string;
    }
}
