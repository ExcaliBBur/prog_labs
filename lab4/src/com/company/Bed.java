package com.company;

public class Bed extends Entity{
    private static String name;
    public Bed(String name){
        super(name);
        Bed.name = name;
        story();
    }
    public String getName() {
        return name;
    }

    public void story() {
        System.out.println("Кровать "+name+" появилась в истории");
    }
    public static class Voice{
        public void voiceOut(){
            System.out.println("Из-под кровати "+name+" раздался глухой голос");
            System.out.println("В нём звучало ещё больше удовлетворения");
        }
    }
    public void endStory() {
        System.out.println("Кровать "+name+" ушла из истории");
    }
    public String toString(){
        return "Кровать " + name;
    }
}
