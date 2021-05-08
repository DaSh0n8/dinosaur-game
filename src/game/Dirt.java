package game;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

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
		if (rand == 1)
			System.out.println(location.x() + " " + location.y());
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
		GameMap map = location.map();
		int x = location.x();
		int y = location.y();
		int maxX = map.getXRange().max();
		int maxY = map.getYRange().max();
		int minX = map.getXRange().min();
		int minY = map.getYRange().min();
		int count = 0;

		// check ground on above
		if (y-1 >= minY) {
			if (map.at(x, y-1).getGround().hasCapability(targetGroundType))
				count++;
		}
		// check ground on upper right
		if (x+1 <= maxX && y-1 >= minY) {
			if (map.at(x+1, y-1).getGround().hasCapability(targetGroundType))
				count++;
		}
		// check ground on right
		if (x+1 <= maxX) {
			if (map.at(x+1, y).getGround().hasCapability(targetGroundType))
				count++;
		}
		// check ground on lower right
		if (x+1 <= maxX && y+1 <= maxY) {
			if (map.at(x+1, y+1).getGround().hasCapability(targetGroundType))
				count++;
		}
		// check ground on below
		if (y+1 <= maxY) {
			if (map.at(x, y+1).getGround().hasCapability(targetGroundType))
				count++;
		}
		// check ground on lower left
		if (x-1 >= minX && y+1 <= maxY) {
			if (map.at(x-1, y+1).getGround().hasCapability(targetGroundType))
				count++;
		}
		// check ground on left
		if (x-1 >= minX) {
			if (map.at(x-1, y).getGround().hasCapability(targetGroundType))
				count++;
		}
		// check ground on upper left
		if (x-1 >= minX && y-1 >= minY) {
			if (map.at(x-1, y-1).getGround().hasCapability(targetGroundType))
				count++;
		}

		return count;
	}

}
