package com.company.sourse;


import java.io.Serializable;

public class Dragon implements Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int age; //Значение поля должно быть больше 0
    private Float wingspan; //Значение поля должно быть больше 0, Поле не может быть null
    private long weight; //Значение поля должно быть больше 0
    private Color color; //Поле не может быть null
    private DragonHead head;

    /**
     * New dragon
     * @param id id
     * @param name name
     * @param coordinates coordinates
     * @param creationDate creation date
     * @param age age
     * @param wingspan wingspan
     * @param weight weight
     * @param color color
     * @param head head
     */
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

    /**
     * Method to set age
     * @param age age
     */
    public void setAge(int age){
        this.age = age;
    }

    /**
     * Method to set wingspan
     * @param wingspan wingspan
     */
    public void setWingspan(Float wingspan){
        this.wingspan = wingspan;
    }

    /**
     * Method to set weight
     * @param weight weight
     */
    public void setWeight(long weight){
        this.weight = weight;
    }

    /**
     * Method to set head
     * @param head head
     */
    public void setHead(long head){
        this.head = new DragonHead(head);
    }

    /**
     * Method to get head size
     * @return head size
     */
    public long getHead(){
        return head.getSize();
    }

    /**
     * Method to set name
     * @param name name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Method to set coordinates
     * @param x x
     * @param y y
     */
    public void setCoordinates(Double x,long y){
        coordinates = new Coordinates(x,y);
    }

    /**
     * Method to set color
     * @param color color
     */
    public void setColor(Color color){
        this.color = color;
    }
    /**
     * Method to set id for server
     * @param id id
     */
    public void setId(Long id){this.id = id;}
    /**
     * Method to set id for server
     * @param creationDate creationDate
     */
    public void setCreationDate(java.util.Date creationDate){this.creationDate = creationDate;}
    /**
     * Method to get creation date
     * @return creation date
     */
    public java.util.Date getCreationDate(){
        return creationDate;
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
     * Method to get wingspan
     * @return wingspan
     */
    public Float getWingspan(){
        return wingspan;
    }

    /**
     * Method to get weight
     * @return weight
     */
    public long getWeight(){
        return weight;
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
     * Method to get X coordinate
     * @return x
     */
    public Double getCoordinateX(){
        return coordinates.getX();
    }

    /**
     * Method to get Y coordinate
     * @return y
     */
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
