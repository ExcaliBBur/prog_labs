package com.company;

import java.sql.SQLOutput;

public class Kid extends Entity implements Runnable{
    private final String name;
    public Kid(){
        super();
        name = "ребеночек";
        story();
    }
    public Kid(String name){
        super(name);
        this.name = name;
        story();
    }
    public void story(){
        System.out.println("Малыш '"+name+"' вполз в рассказ");
    }
    public void run(Bringing prizrak,String type)  {
        //unchecked exception
        try {
            if (!type.equals("Run"))
                throw new RunException();
            else System.out.println("Малыш '"+name+"' побежал за приведением '"+prizrak.getName()+"'");
        }
        catch(RunException r){
            System.out.println(r.toString());
        }
    }
    public void endStory(){
        System.out.println("Малыш '"+name+"' выполз из рассказа");
    }
    public String getName(){
        return name;
    }
    public String toString(){
        return "Kid name - "+ name;
    }
    public boolean equals(Object obj) {
        return obj == this;
    }
    public void run() {}
}
