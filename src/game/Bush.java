package game;

import edu.monash.fit2099.engine.Location;
/**
 * A class that represents bush.
 */
public class Bush extends FruitPlant {

    private int fruit;

    public Bush() {
        super('=');
    }

    @Override
    public void tick(Location location) {
        super.tick(location);

        if (spawnFruit(10)){
            fruit++;
            EcoPoints.increase_points(1);
        }
    }

}
