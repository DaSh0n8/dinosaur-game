package game.action;

import edu.monash.fit2099.engine.*;
import game.actor.Dinosaur;
import game.actor.Pterodactyl;
import game.enumeration.DinosaurSpecies;
import game.enumeration.GroundType;
import game.enumeration.ItemType;
import game.ground.Bush;
import game.ground.Corpse;
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
        Item item;
        // Stegosaur eating
        if (actor.hasCapability(DinosaurSpecies.STEGOSAUR)){
            // if Stegosaur on Bush
            if (ground.hasCapability(GroundType.BUSH)) {
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
            // if Stegosaur under Tree
            else if (ground.hasCapability(GroundType.TREE)) {
                // Stegasaur can eat dropped Fruit
                for (Item thisItem : location.getItems()) {
                    if (thisItem.hasCapability(ItemType.FRUIT)) {
                        location.removeItem(thisItem);
                        actor.heal(10);
                        break;
                    }
                }
                return actor + " eats fruit under tree";
            }
        }
        // Brachiosaur eating
        else if (actor.hasCapability(DinosaurSpecies.BRACHIOSAUR)) {
            // if Brachiosaur under Tree
            if (ground.hasCapability(GroundType.TREE)) {
                try {
                    FruitPlant plant = (FruitPlant) ground;
                    // Brachiosaur can eat many fruits in a turn
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
        // Pterodactyl eating
        else if (actor.hasCapability(DinosaurSpecies.PTERODACTYL)) {
            // if Pterodactyl on location have corpse
            for (Item thisItem : location.getItems()) {
                if (thisItem.hasCapability(ItemType.CORPSE)) {
                    // Pterodactyl eats only part of the corpse
                    try {
                        Corpse corpse = (Corpse) thisItem;
                        int foodPoints = corpse.eatenPart(10);
                        actor.heal(foodPoints);
                    }
                    catch (ClassCastException e) {
                        System.out.println("Invalid item");
                    }
                    return actor + " eat parts of the corpse";
                }
            }
            // if Pterodactyl on lake
            if (ground.hasCapability(GroundType.LAKE)) {
                try {
                    Lake lake = (Lake) ground;
                    Dinosaur dinosaur = (Dinosaur) actor;
                    int rand = random.nextInt(100) + 1;
                    // Pterodactyl can try to eat fish twice
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
                } catch (ClassCastException e) {
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
