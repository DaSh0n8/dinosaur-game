package game.ground;

import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.enumeration.GroundType;

import java.util.Random;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {

	private Random random = new Random();

	public Dirt() {
		super('.');
		addCapability(GroundType.DIRT);
	}

	//determine x and y axis

	@Override
	public void tick(Location location) {
		// Dirt try to become Bush
		int totalAdjoiningBush = countAdjoiningGround(location, GroundType.BUSH);
		int totalAdjoiningTree = countAdjoiningGround(location, GroundType.TREE);
		int becomeBushChance = 0;
		if (totalAdjoiningTree == 0) {
			if (totalAdjoiningBush >= 2) {
				becomeBushChance = 10;
			}
			else
				becomeBushChance = 1;
		}
		int rand = random.nextInt(100) + 1;
		if (rand <= becomeBushChance) {
			location.setGround(new Bush());
		}

	}

	/**
	 * Count how many grounds next to this Dirt is of a certain ground type.
	 *
	 * @param location the Location of this Dirt
	 * @param targetGroundType the specific ground type this Dirt want to count
	 * @return the total of the adjoining Ground of a GroundType
	 */
	private int countAdjoiningGround(Location location, GroundType targetGroundType) {
		int count = 0;

		// checks all adjacent locations
		for (Exit thisExit : location.getExits()) {
			if (thisExit.getDestination().getGround().hasCapability(targetGroundType)) {
				count++;
			}
		}

		return count;
	}

}
