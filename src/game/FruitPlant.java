package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

public class FruitPlant extends Ground {
    private float spawnFruitChance;
    private double d = Math.random();

    /**
     * Constructor. Initialize its display char and add its GroundType into capabilities.
     *
     * @param displayChar character to display for this type of terrain
     */
    public FruitPlant(char displayChar) {
        super(displayChar);
        addCapability(GroundType.FRUITPLANT);
    }

    public void setSpawnFruitChance(int spawnFruitChance) {
        this.spawnFruitChance = spawnFruitChance;
    }

    public boolean spawnFruit(double spawnFruitChance) {
        return !(d * 100 > spawnFruitChance);
    }
}
