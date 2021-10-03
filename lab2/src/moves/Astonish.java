package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class Astonish extends PhysicalMove {
    public Astonish(){
        super(Type.GHOST,30,100);
    }
    @Override
    public String describe(){
        return "использует Astonish";
    }
}
