package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

public class FruitPlant extends Ground{
    private float spawnFruitChance;
    private double d = Math.random();

    public FruitPlant(char displayChar) {
        super(displayChar);
    }


    public void setSpawnFruitChance(int spawnFruitChance) {
        this.spawnFruitChance = spawnFruitChance;
    }

    public boolean spawnFruit(double spawnFruitChance){
        return !(d * 100 > spawnFruitChance);
    }
}
