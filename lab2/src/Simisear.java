import moves.FlameBurst;
import moves.FurySwipes;
import ru.ifmo.se.pokemon.Type;

public class Simisear extends Pansear {
    public Simisear(String name,int level) {
        super (name,level);
        setStats(75,98,63,98,63,101);
        setType(Type.FIRE);
        setMove(new FlameBurst(),new FurySwipes());
    }
}
