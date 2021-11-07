package com.company;

public class Kid extends Entity implements Runnable{
    private final String name;
    private final RunTypes type = RunTypes.RUN;
    public Kid(){
        super();
        name = "ребеночек";
        Story();
    }
    public Kid(String name){
        super(name);
        this.name = name;
        Story();
    }
    public void Story(){
        System.out.println("Малыш '"+name+"' вполз в рассказ");
    }
    public void Run(Bringing prizrak){
        if (type == RunTypes.RUN){
            System.out.println("Малыш '"+name+"' побежал за приведением '"+prizrak.getName()+"'");
        }
    }
    public void EndStory(){
        System.out.println("Малыш '"+name+"' выполз из рассказа");
    }
    public String getName(){
        return name;
    }
    public String toString(){
        return "Kid name - "+ name;
    }
    public void Run() {}
}
