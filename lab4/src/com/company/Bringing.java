package com.company;

import com.sun.glass.ui.Size;

public class Bringing extends Entity{
    private final String name;
    private final RunTypes type = RunTypes.FLEW;
    private final String[] places = {"кухню","кухни","столовую","столовой","комнату Малыша","комнаты Малыша","большую комнату"};
    public Bringing(){
        super();
        name = "Приведение";
        story();
    }
    public Bringing(String name){
        super(name);
        this.name = name;
        story();
    }
    public void story(){
        System.out.println("Приведение '"+name+"' ворвалось в рассказ");
    }
    public void flew(Bed bed,int size) throws SizeBringingException {
        if (size == 1){
            System.out.println("Маленькое приведение '"+name+"' вылетело из-под кровати "+bed.getName());
        }
        else throw new SizeBringingException();
    }
    public void shattered(){
        System.out.println("Приведение '"+name+"' разразилось долгим глухим смехом");
    }
    public void following(FrekenBok frek,int count){
        if (type == RunTypes.FLEW && count == 1) {
            System.out.println("Приведение '" + name + "' полетело следом за фрекен Бок '"+frek.getName()+"'");
        }
        if (count == 2){
            System.out.print("Приведение '" + name + "' мчалось за фрекен Бок '"+frek.getName()+"': ");
            for (int i = 0;i<7;i+=2){
                if (i < 4){
                System.out.print("в "+places[i]+" и из "+places[i+1]+", ");}
                else if (i < 6){
                    System.out.print("в "+places[i]+" и из "+places[i+1]+" и снова в "+ places[0]+", ");
                }
                else{
                    System.out.println("большую комнату, "+places[4]+" и снова, и снова...");
                }
            }
        }
    }
    public void thought(Beembo bim){
        System.out.println("Приведение '" + name + "' думало также, как и '"+bim.getName()+"'");
    }
    public void backwardly(){
        System.out.println("Приведение '"+name+"' немного поотстало, чтобы получилась настоящая погоня");
    }
    public void endStory(){
        System.out.println("Приведение '" + name + "' вылетело из истории");
    }
    public String toString(){
        return "Bringing name - "+ name;
    }
    public String getName(){
        return name;
    }
}
