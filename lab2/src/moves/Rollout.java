package moves;
import ru.ifmo.se.pokemon.*;

public class Rollout extends PhysicalMove {
    public Rollout() {
        super(Type.ROCK, 30, 90);
    }
    @Override
    public String describe(){
        return "использует супер пупер Rollout";
    }
}
