package moves;

import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class DragonBreath extends SpecialMove {
    public DragonBreath(){
        super(Type.DRAGON,60,100);
    }
    @Override
    public String describe(){
        return "использует Dragon Breath";
    }
}
