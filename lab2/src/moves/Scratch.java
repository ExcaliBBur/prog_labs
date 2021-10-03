package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class Scratch extends PhysicalMove {
    public Scratch(){
        super(Type.NORMAL,40,100);
    }
    @Override
    public String describe(){
        return "юзает Scratch";
    }
}
