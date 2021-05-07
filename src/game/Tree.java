package game;

import edu.monash.fit2099.engine.Location;

public class Tree extends FruitPlant {
	private int age = 0;
	private int fruit = 0;

	public Tree() {
		super('+');
	}

	@Override
	public void tick(Location location) {
		super.tick(location);

		age++;
		if (age == 10)
			displayChar = 't';
		if (age == 20)
			displayChar = 'T';

		if (spawnFruit(50)){
			fruit++;
			EcoPoints.increase_points(1);
		}
	}

}
