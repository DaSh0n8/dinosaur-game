package game.ground;

import edu.monash.fit2099.engine.Ground;
import game.enumeration.DinosaurSpecies;
import game.enumeration.GroundType;

public class Corpse extends Ground {
    public Corpse(DinosaurSpecies species) {
        super('c');
        addCapability(GroundType.CORPSE);
    }
}
