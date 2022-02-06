package com.company.sourse;

public class Dragon{
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
    public void setAge(int age){
        this.age = age;
    }
    public void setWingspan(Float wingspan){
        this.wingspan = wingspan;
    }
    public void setWeight(long weight){
        this.weight = weight;
    }
    public void setHead(long head){
        this.head = new DragonHead(head);
    }
    public long getHead(){
        return head.getSize();
    }
    public void setName(String name){
        this.name = name;
    }
    public void setCoordinates(Double x,long y){
        coordinates = new Coordinates(x,y);
    }
    public void setColor(Color color){
        this.color = color;
    }
    public java.util.Date getCreationDate(){
        return creationDate;
    }
    public long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public Float getWingspan(){
        return wingspan;
    }
    public long getWeight(){
        return weight;
    }
    public int getAge(){
        return age;
    }
    public Color getColor(){
        return color;
    }
    public Double getCoordinateX(){
        return coordinates.getX();
    }
    public long getCoordinateY(){
        return coordinates.getY();
    }
    @Override
    public String toString(){
        String string = "id: "+ id +"\n name: " + name + "\n coordinates: "+coordinates+"\n creationDate: "+creationDate +
                "\n age: " + age + "\n wingspan: " + wingspan + "\n weight: " + weight + "\n color: " + color + "\n head size: " + head +"\n\n";
        return string;
    }

}
