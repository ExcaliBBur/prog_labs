package com.company;

public class Main {
    public static void main(String[] args) throws SizeBringingException{
        Bed bed = new Bed("папы");
        //static вложенный класс
        Bed.Voice bed_voiceOut = new Bed.Voice();
        bed_voiceOut.voiceOut();
        Bringing privedenie = new Bringing("4 лаба");
        try{
            //size == 1 => маленькое приведение; if size != 1 => SizeBringingException
            privedenie.flew(bed,1);
        }
        catch(SizeBringingException e){
            System.out.println(e.toString());
        }
        bed.endStory();
        privedenie.shattered();
        FrekenBok freken = new FrekenBok("Злюка");
        //анонимный класс
        FrekenBok freken_angry = new FrekenBok("Злюка"){
            public void story(){};
            public void notFunny(){
                System.out.println("фрекен Бок '"+getName()+"' было не до смеха");
            }
        };
        freken_angry.notFunny();
        freken.rushed();
        freken.toss();
        freken.disassemble();
        freken.run();
        //following №1
        privedenie.following(freken,1);
        Kid malish = new Kid("эмокид");
        // Run - RunType; if (type != "Run") => RunException
        malish.run(privedenie,"Run");
        Beembo bimbo = new Beembo("коронавирус");
        //non-static вложенные классы
        Beembo.Knowledge bimbo_knowledge = bimbo.new Knowledge();
        Beembo.Thought bimbo_thought = bimbo.new Thought();
        bimbo.run();
        bimbo.barked();
        bimbo_knowledge.knowledge(privedenie);
        bimbo_thought.thought();
        bimbo.endStory();
        privedenie.thought(bimbo);
        privedenie.backwardly();
        freken.galloped();
        //following №2
        privedenie.following(freken,2);
        freken.scream(privedenie);
        malish.endStory();
        freken.endStory();
        privedenie.endStory();
    }
}
