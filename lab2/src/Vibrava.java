import moves.Bulldoze;
import moves.DragonBreath;
import ru.ifmo.se.pokemon.Type;

public class Vibrava extends Trapinch{
    public Vibrava(String name,int level){
        super(name,level);
        setStats(50,70,50,50,50,70);
        setType(Type.GROUND,Type.DRAGON);
        setMove(new Bulldoze(),new DragonBreath());
    }
}
