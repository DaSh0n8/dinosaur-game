package game;

import edu.monash.fit2099.engine.Ground;

public class Corpse extends Ground {
    public Corpse(DinosaurSpecies species) {
        super('c');
        addCapability(GroundType.CORPSE);
    }
}
