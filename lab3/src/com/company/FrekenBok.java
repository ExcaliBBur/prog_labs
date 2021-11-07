package com.company;

public class FrekenBok extends Entity implements Runnable{
    private final String name;
    private RunTypes type = RunTypes.RUSHED;
    public FrekenBok(){
        super();
        name = "фрекен Бок";
        Story();
        NotFunny();
    }
    public FrekenBok(String name){
        super(name);
        this.name = name;
        Story();
        NotFunny();
    }
    public void Story(){
        System.out.println("фрекен Бок '"+name+"' ворвалась в рассказ");
    }
    public void NotFunny(){
        System.out.println("фрекен Бок '"+name+"' было не до смеха");
    }
    public void Angry(){
        if (type == RunTypes.RUN){
            System.out.println("фрекен Бок '"+name+"' побежала к двери");
        }
        else if (type == RunTypes.RUSHED){
            System.out.println("фрекен Бок '"+name+"' кинулась к двери");
        }
    }
    public void Toss(){
        System.out.println("фрекен Бок '"+name+"' стала расшвыривать мебель");
    }
    public void Run(){
        type = RunTypes.RUN_OUT;
        System.out.println("фрекен Бок '"+name+"' с громким криком выбежала в переднюю");
    }
    public void Disassemble(){
        System.out.println("фрекен Бок '"+name+"' разобрала баррикаду в мнгновение ока");
    }
    public String toString(){
        return "FrekenBok name - "+ name;
    }
    public void EndStory(){
        System.out.println("фрекен Бок '"+name+"' вырвалась из рассказа");
    }
    public String getName(){
        return name;
    }
}
