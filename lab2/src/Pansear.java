import moves.Incinerate;
import moves.Scratch;
import ru.ifmo.se.pokemon.*;

public class Pansear extends Pokemon {
    public Pansear(String name, int level){
        super(name,level);
        setStats(50,53,48,53,48,64);
        setMove(new Scratch(),new Incinerate());
        setType(Type.FIRE);
    }
}
