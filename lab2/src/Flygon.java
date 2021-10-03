import moves.Dig;
import moves.Screech;
import ru.ifmo.se.pokemon.Type;

public class Flygon extends Vibrava{
    public Flygon(String name,int level){
        super (name,level);
        setStats(80,100,80,80,80,100);
        setType(Type.GROUND,Type.DRAGON);
        setMove(new Screech(),new Dig());
    }
}
