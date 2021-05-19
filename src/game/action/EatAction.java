package game.action;

import edu.monash.fit2099.engine.*;
import game.enumeration.DinosaurSpecies;
import game.enumeration.GroundType;
import game.enumeration.ItemType;
import game.ground.Bush;
import game.ground.FruitPlant;

public class EatAction extends Action {

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
            }
            // Brachiosaur can eat many Fruit on Tree in a turn
            else if (actor.hasCapability(DinosaurSpecies.BRACHIOSAUR)) {
                try {
                    FruitPlant plant = (FruitPlant) ground;
                    do {
                        item = plant.getFruit();
                        actor.heal(5);
                    } while (item != null);
                }
                catch (ClassCastException e) {
                    System.out.println("Invalid ground");
                }
            }
        }


        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " eats " + "food";
    }

}
