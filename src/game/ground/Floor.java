package game.ground;

import edu.monash.fit2099.engine.Ground;
import game.enumeration.GroundType;

/**
 * A class that represents the floor inside a building.
 */
public class Floor extends Ground {

	public Floor() {
		super('_');
		addCapability(GroundType.FLOOR);
	}

}
