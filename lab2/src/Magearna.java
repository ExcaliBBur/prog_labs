import moves.AuroraBeam;
import moves.Rollout;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Magearna extends Pokemon {

    public Magearna(String name, int level) {
            super(name,level);
            setStats(80,95,115,130,115,65);
            setMove(new Rollout(),new AuroraBeam());
            setType(Type.STEEL,Type.FAIRY);
        }
}
