package com.company;

public class Main {

    public static void main(String[] args) {
        Bringing privedenie = new Bringing("4 лаба");
        FrekenBok freken = new FrekenBok("Злюка");
        freken.Angry();
        freken.Toss();
        freken.Disassemble();
        freken.Run();
        freken.EndStory();
        privedenie.Following(freken);
        Kid malish = new Kid("эмокид");
        malish.Run(privedenie);
        malish.EndStory();
        Beembo bimbo = new Beembo("коронавирус");
        bimbo.Run();
        bimbo.Barked();
        bimbo.Knowledge(privedenie);
        bimbo.Thought();
        bimbo.EndStory();
        privedenie.Thought(bimbo);
        privedenie.EndStory();
    }
}
