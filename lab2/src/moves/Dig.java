package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class Dig extends PhysicalMove {
    public Dig(){
        super(Type.GROUND,80,100);
    }
    @Override
    public String describe(){
        return "использует Dig";
    }
}
