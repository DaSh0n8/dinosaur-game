package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

public class Tree extends FruitPlant {

	private int age = 0;
	private int fruit;

	public Tree() {
		super('+');
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

		if (spawnFruit(10)){
			fruit++;
			EcoPoints.increase_points(1);
		}
	}
}
