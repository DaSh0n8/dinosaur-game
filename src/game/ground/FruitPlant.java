package game.ground;

import edu.monash.fit2099.engine.Ground;
import game.enumeration.GroundType;
import game.item.Fruit;

import java.util.Random;

public abstract class FruitPlant extends Ground {
    private int fruits = 0; // fruits on FruitPlant
    private int spawnFruitChance;
    private Random random = new Random();

    /**
     * Constructor. Initialize its display char, add its GroundType into capabilities, and set its chances for spawning
     * Fruit.
     *
     * @param displayChar character to display for this type of terrain
     */
    public FruitPlant(char displayChar, int spawnFruitChance) {
        super(displayChar);
        addCapability(GroundType.FRUITPLANT);
        this.spawnFruitChance = spawnFruitChance;
    }


    /**
     * A FruitPlant has a certain chance to produce a ripe fruit.
     *
     * @return If successfully produced, return a Fruit object. Otherwise returns null.
     */
    public boolean spawnFruit() {
        int rand = random.nextInt(100) + 1;
        return rand <= this.spawnFruitChance;
    }

    /**
     * Increment number of fruits on FruitPlant.
     *
     */
    public void incrementFruits() {
        this.fruits++;
    }

    /**
     * Remove a number of fruits from FruitPlant.
     *
     * @param totalRemovedFruits Number of fruits removed
     */
    public void reduceFruits(int totalRemovedFruits) {
        this.fruits -= totalRemovedFruits;
    }

    /**
     * Get the total number of fruits on tree.
     *
     * @return an integer
     */
    public int getTotalFruits() {
        return this.fruits;
    }

    /**
     * Returns a Fruit object if there is any.
     *
     * @return a Fruit object
     */
    public Fruit getFruit() {
        if (this.fruits > 0) {
            this.fruits -= 1;
            return new Fruit();
        }
        return null;
    }

    public Fruit searchPlant(){
        if (this.getTotalFruits()!= 0){
            if(Math.random()*100 < 60){
                return new Fruit();
            }
            else{
                System.out.println("You search the tree or bush for fruit, but you can’t find any ripe ones");
                return null;
            }
        }
        else{
            System.out.println("There are no fruits here");
            return null;
        }

    }

}
