package moves;

import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class Incinerate extends SpecialMove {
    public Incinerate(){
        super(Type.FIRE,60,100);
    }
    @Override
    public String describe(){
        return "использует Incinerate";
    }
}
