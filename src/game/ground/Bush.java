package game.ground;

import edu.monash.fit2099.engine.Location;
import game.enumeration.GroundType;

/**
 * A class that represents bush.
 */
public class Bush extends FruitPlant {

    private final static int SPAWN_FRUIT_CHANCE = 10;	// 10%

    public Bush() {
        super('=', SPAWN_FRUIT_CHANCE);
        addCapability(GroundType.FRUITPLANT);
        addCapability(GroundType.BUSH);
    }

    @Override
    public void tick(Location location) {
        super.tick(location);

        // try to produce one ripe fruit in each turn
        if (spawnFruit()) {
            this.incrementFruits();
        }

    }

}
