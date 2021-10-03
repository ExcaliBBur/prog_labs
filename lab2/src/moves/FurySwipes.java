package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class FurySwipes extends PhysicalMove {
    public FurySwipes(){
        super(Type.NORMAL,18,80);
    }
    @Override
    public String describe(){
        return "использует Fury Swipes";
    }
}
