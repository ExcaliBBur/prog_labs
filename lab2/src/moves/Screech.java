package moves;

import ru.ifmo.se.pokemon.*;

public class Screech extends StatusMove {
    public Screech() {
        super(Type.NORMAL, 0, 85);
    }

    @Override
    protected void applyOppEffects(Pokemon p){
        Effect e = new Effect().turns(2).stat(Stat.DEFENSE,-1);
        p.addEffect(e);
    }

    @Override
    public String describe() {
        return "использует Screech";
    }
}
