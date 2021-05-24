package game.action;

import edu.monash.fit2099.engine.*;
import game.actor.Dinosaur;
import game.actor.Pterodactyl;
import game.enumeration.DinosaurSpecies;
import game.enumeration.GroundType;
import game.enumeration.ItemType;
import game.ground.Bush;
import game.ground.FruitPlant;
import game.ground.Lake;

import java.util.Random;

/**
 * A class that will makes a Dinosaur to eat in its own way at current Location.
 */
public class EatAction extends Action {

    private Random random = new Random();

    public EatAction() {

    }

    @Override
    public String execute (Actor actor, GameMap map) {
        Location location = map.locationOf(actor);
        Ground ground = location.getGround();
        Item item = null;
        // if the ground is Bush
        if (ground.hasCapability(GroundType.BUSH)) {
            // Stegosaur can eat Fruit in Bush
            if (actor.hasCapability(DinosaurSpecies.STEGOSAUR)) {
                try {
                    FruitPlant plant = (FruitPlant) ground;
                    item = plant.getFruit();
                    if (item != null) {
                        actor.heal(10);
                    }
                }
                catch (ClassCastException e) {
                    System.out.println("Invalid ground");
                }
                return actor + " eats fruit on bush";
            }
        }
        // if the ground is Tree
        else if (ground.hasCapability(GroundType.TREE)) {
            // Stegasaur can eat dropped Fruit
            if (actor.hasCapability(DinosaurSpecies.STEGOSAUR)) {
                for (Item thisItem : location.getItems()) {
                    if (thisItem.hasCapability(ItemType.FRUIT)) {
                        location.removeItem(thisItem);
                        actor.heal(10);
                        break;
                    }
                }
                return actor + " eats fruit under tree";
            }
            // Brachiosaur can eat many Fruit on Tree in a turn
            else if (actor.hasCapability(DinosaurSpecies.BRACHIOSAUR)) {
                try {
                    FruitPlant plant = (FruitPlant) ground;
                    do {
                        item = plant.getFruit();
                        if (item != null) {
                            actor.heal(5);
                        }
                    } while (item != null);
                }
                catch (ClassCastException e) {
                    System.out.println("Invalid ground");
                }
                return actor + " eat fruits on tree";
            }
        }
        // if the ground is lake
        else if (ground.hasCapability(GroundType.LAKE)) {
            // Pterodactyl can eat try to eat fish twice
            if (actor.hasCapability(DinosaurSpecies.PTERODACTYL)) {
                try {
                    Lake lake = (Lake) ground;
                    Dinosaur dinosaur = (Dinosaur) actor;
                    int rand = random.nextInt(100) + 1;
                    for (int i = 0; i < 2; i++) {
                        // 50% chance to catch fish, 100% chance to increase water level
                        if (lake.getFishAmount() > 0) {
                            lake.decreaseFishAmount();
                            if (rand <= 50) {
                                dinosaur.heal(5);
                            }
                        }
                        dinosaur.increaseWaterLevel(30);
                    }
                }
                catch (ClassCastException e) {
                    System.out.println("Invalid ground/actor");
                }
                return actor + " eats fish in lake";
            }
        }

        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " eats food";
    }
}
