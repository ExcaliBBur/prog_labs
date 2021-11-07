package com.company;


public class Bringing extends Entity{
    private final String name;
    private final RunTypes type = RunTypes.FLEW;
    public Bringing(){
        super();
        name = "Приведение";
        Story();
        Shattered();
    }
    public Bringing(String name){
        super(name);
        this.name = name;
        Story();
        Shattered();
    }
    public void Story(){
        System.out.println("Приведение '"+name+"' ворвалось в рассказ");
    }
    public void Shattered(){
        System.out.println("Приведение '"+name+"' разразилось долгим глухим смехом");
    }
    public void Following(FrekenBok frek){
        if (type == RunTypes.FLEW) {
            System.out.println("Приведение '" + name + "' полетело следом за фрекен Бок '"+frek.getName()+"'");
        }
    }
    public void Thought(Beembo bim){
        System.out.println("Приведение '" + name + "' думало также, как и '"+bim.getName()+"'");
    }
    public void EndStory(){
        System.out.println("Приведение '" + name + "' вылетело из истории");
    }
    public String toString(){
        return "Bringing name - "+ name;
    }
    public String getName(){
        return name;
    }
}
