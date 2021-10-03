package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class Bulldoze extends PhysicalMove {
    public Bulldoze(){
        super(Type.GROUND,60,100);
    }
    @Override
    public String describe(){
        return "использует Bulldoze";
    }
}
