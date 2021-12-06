package com.company;

public class FrekenBok extends Entity implements Runnable{
    private final String name;
    private RunTypes type = RunTypes.RUSHED;
    public FrekenBok(){
        super();
        name = "фрекен Бок";
        story();
    }
    public FrekenBok(String name){
        super(name);
        this.name = name;
        story();
    }
    private void method(){}
    public void story(){
        System.out.println("фрекен Бок '"+name+"' ворвалась в рассказ");
    }
    public void notFunny(){
        System.out.println("фрекен Бок '"+name+"' смеялась");
    }
    public void rushed(){
        if (type == RunTypes.RUN){
            System.out.println("фрекен Бок '"+name+"' побежала к двери");
        }
        else if (type == RunTypes.RUSHED){
            System.out.println("фрекен Бок '"+name+"' кинулась к двери");
        }
    }
    public void toss(){
        System.out.println("фрекен Бок '"+name+"' стала расшвыривать мебель");
    }
    public void run(){
        type = RunTypes.RUN_OUT;
        System.out.println("фрекен Бок '"+name+"' с громким криком выбежала в переднюю");
    }
    public void disassemble(){
        System.out.println("фрекен Бок '"+name+"' разобрала баррикаду в мнгновение ока");
    }
    public void galloped(){
        System.out.println("Впереди скакала Фрекен бок '"+name+"'");
    }
    public void scream(Bringing privedenie){
        System.out.println("Фрекен бок '"+name+"' вопила так, что в конце концов приведение '"+privedenie.getName()+"' попыталось её успокоить");
    }
    public String toString(){
        return "FrekenBok name - "+ name;
    }
    public void endStory(){
        System.out.println("фрекен Бок '"+name+"' вырвалась из рассказа");
    }
    public String getName(){
        return name;
    }
}
