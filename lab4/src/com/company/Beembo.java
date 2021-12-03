package com.company;

public class Beembo extends Entity implements Runnable,Barkable{
    private final String name;
    private final RunTypes type = RunTypes.RACED;
    public Beembo(){
        super();
        name = "Бимбо";
        story();
    }
    public Beembo(String name){
        super(name);
        this.name = name;
    }
    public void story() {
        System.out.println("Бимбо '"+name+"' вбежал в историю с четырех лап");
    }
    public void run(){
        if (type == RunTypes.RACED){
            System.out.println("Бимбо по имени '"+name+"' мчался");
        }
    }
    public void barked(){
        System.out.println("Бимбо '"+name+"' заливисто лаял");
    }
    public class Knowledge {
        public void knowledge(Bringing prizrak) {
            System.out.println("Бимбо '" + name + "' узнал приведение '" + prizrak.getName() + "' по запаху");
        }
    }
    public class Thought {
        public void thought() {
            System.out.println("Бимбо '" + name + "' думает, что началась весёлая игра");
        }
    }
    public void endStory(){
        System.out.println("Бимбо '"+name+"' вышел из рассказа");
    }
    public String getName() {
        return name;
    }
    public String toString(){
        return "Beembo name - "+ name;
    }
}
