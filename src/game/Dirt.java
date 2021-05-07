package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {

	public Dirt() {
		super('.');
	}

	//determine x and y axis

	@Override
	public void tick(Location location) {
		super.tick(location);
		System.out.println();
	}
}
