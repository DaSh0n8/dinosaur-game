package game.ground;

import edu.monash.fit2099.engine.Location;
import game.EcoPoints;
import game.enumeration.GroundType;
import game.ground.FruitPlant;
import game.item.Fruit;

import java.util.Random;

public class Tree extends FruitPlant {

	private int age = 0;
	private final static int SPAWN_FRUIT_CHANCE = 50;	// 50%
	private final static int FRUIT_FALLING_CHANCE = 5;	// 5%
	private Random random = new Random();

	public Tree() {
		super('+', SPAWN_FRUIT_CHANCE);
		addCapability(GroundType.FRUITPLANT);
		addCapability(GroundType.TREE);
	}

	@Override
	public void tick(Location location) {
		super.tick(location);

		age++;
		if (age == 10)
			displayChar = 't';
		if (age == 20)
			displayChar = 'T';

		// try to produce one ripe fruit in each turn
		if (spawnFruit()) {
			this.incrementFruits();
			EcoPoints.increase_points(1);
		}

		// every ripe Fruit may fall from Tree
		fruitFalling(location);

	}

	/**
	 * If Fruit from tree, remove the fruit from tree.
	 *
	 * @param location Location of the tree
	 */
	private void fruitFalling(Location location) {
		int rand;
		int totalRemovedFruits = 0;
		for (int i = 0; i < this.getTotalFruits(); i++) {
			rand = random.nextInt(100) + 1;
			if (rand <= FRUIT_FALLING_CHANCE) {
				location.addItem(new Fruit());    // add fruit on the ground
				totalRemovedFruits++;
			}
		}
		reduceFruits(totalRemovedFruits);
	}

}
