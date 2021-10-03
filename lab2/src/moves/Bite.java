package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class Bite extends PhysicalMove {
    public Bite(){
        super(Type.DARK,60,100);
    }
    @Override
    public String describe(){
        return "использует Bite";
    }
}
