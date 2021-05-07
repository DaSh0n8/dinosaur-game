package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

public class Tree extends Ground {
    private int age = 0;

    /**
     * Constructor. Initialize its display character and add its types into capabilities (A Tree is also a FruitPlant).
     */
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
    }
}
