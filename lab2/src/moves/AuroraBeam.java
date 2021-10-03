package moves;
import ru.ifmo.se.pokemon.*;

public class AuroraBeam extends SpecialMove {
    public AuroraBeam(){
        super(Type.ICE,65,100);
    }
    @Override
    public String describe(){
        return "использует Aurora Beam";
    }
}
