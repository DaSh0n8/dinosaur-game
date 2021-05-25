package game.action;

import edu.monash.fit2099.engine.*;
import game.EcoPoints;
import game.enumeration.GroundType;
import game.ground.FruitPlant;

import java.util.Random;

/**
 * Class that allows Player to pick fruit from fruitplant
 */
public class PluckAction extends Action {

    private Random random = new Random();
    private final static int PLUCK_SUCCESS_RATE = 40;

    public PluckAction() { }

    /**
     * When player try to pick fruit from empty fruitplant, he will not found any ripe fruits. If there is a ripe fruit
     * on the fruitplant, player has 40% chance to pluck the fruit.
     * Picking fruit always increase 10 ecoPoints.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened after Player tried to pick fruit
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Ground ground = map.locationOf(actor).getGround();
        if(ground.hasCapability(GroundType.TREE) || ground.hasCapability(GroundType.BUSH)){
            try {
                FruitPlant plant = (FruitPlant) ground;
                if (plant.getTotalFruits() > 0) {
                    // 40% chance to successfully pick the fruits
                    int rand = random.nextInt(100) + 1;
                    if (rand <= PLUCK_SUCCESS_RATE) {
                        actor.addItemToInventory(plant.getFruit());
                        EcoPoints.increase_points(10);
                        return actor + " picks a fruit";
                    }
                    else {
                        return actor + " fails to pick a fruit";
                    }
                }
                else {
                    return actor + " can't find any ripe fruit";
                }
            }
            catch (ClassCastException e) {
                System.out.println("Invalid ground");
            }
        }
        return actor + " tries to pick fruit on an invalid ground";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " picks fruit";
    }

    @Override
    public String hotkey() {
        return "p";
    }
}
