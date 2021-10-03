import moves.Astonish;
import moves.Bite;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Trapinch extends Pokemon {
    public Trapinch(String name,int level) {
        super(name,level);
        setStats(45,100,45,45,45,10);
        setType(Type.GROUND);
        setMove(new Astonish(),new Bite());
    }
}
