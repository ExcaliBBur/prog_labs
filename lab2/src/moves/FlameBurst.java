package moves;

import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class FlameBurst extends SpecialMove {
    public FlameBurst(){
        super(Type.FIRE,70,100);
    }
    @Override
    public String describe(){
        return "использует Flame Burst";
    }
}
