package game;

import edu.monash.fit2099.engine.Location;
/**
 * A class that represents bush.
 */
public class Bush extends FruitPlant {

    private int fruit;
    private int fruitOnGround;
    private double rand = Math.random();

    public Bush() {
        super('=');
        addCapability(GroundType.FRUITPLANT);
        addCapability(GroundType.BUSH);
    }

    @Override
    public void tick(Location location) {
        super.tick(location);


        if (spawnFruit(10)){
            fruit++;
            EcoPoints.increase_points(1);
        }

        if (fruit!=0){
            for (int i = 0; i < fruit; i++){
                if (rand*100 < 5){
                    fruitOnGround++;
                    Fruit fruitGround = new Fruit();
                    fruitGround.setRotTurns(0);
                }
            }
        }
    }

    public int getFruitAmount(){
        return fruit;
    }

    public int getFruitOnGround(){
        return fruitOnGround;
    }

}
