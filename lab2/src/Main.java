import ru.ifmo.se.pokemon.*;

public class Main {

    public static void main(String[] args) {
        Battle b = new Battle();
        Pansear p1 = new Pansear("Чужой", 1);
        Magearna p2 = new Magearna("Хищник", 1);
        Simisear p3 = new Simisear("Кто",1);
        Trapinch p4 = new Trapinch("Потом",1);
        Vibrava p5 = new Vibrava("Лень",1);
        Flygon p6 = new Flygon("Что",1);
        b.addAlly(p1);
        b.addAlly(p3);
        b.addAlly(p5);
        b.addFoe(p2);
        b.addFoe(p4);
        b.addFoe(p6);
        b.go();
    }
}
