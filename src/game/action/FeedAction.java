package game.action;

import edu.monash.fit2099.engine.*;
import game.EcoPoints;
import game.actor.Dinosaur;
import game.enumeration.DinosaurDiet;
import game.enumeration.DinosaurSpecies;
import game.enumeration.ItemType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Action for player to feed a dinosaur.
 */
public class FeedAction extends Action {

    private final static int FEED_FRUIT = 20;

    private Scanner scanner = new Scanner(System.in);
    private Dinosaur target;
    private DinosaurDiet targetDinosaurDiet;

    public FeedAction() {}

    /**
     * Choose a target adjacent dinosaur, and feed with the first valid food in inventory.
     * Feeding always increase 10 ecoPoints.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return the description of what happened after actor is trying to feed
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // set target dinosaur to feed and its dinosaur diet
        dinosaursMenu(actor, map);

        // find the item to feed the target dinosaur
        Item item = null;
        if (this.target != null && this.targetDinosaurDiet != null) {
            for (Item thisItem : actor.getInventory()) {
                if (thisItem.hasCapability(this.targetDinosaurDiet)) {
                    item = thisItem;
                    break;
                }
            }
        }

        // heals feeded dinosaur
        String description = null;
        if (item != null) {
            // different Dinosaur heals different amount of hitPoints when fed with Fruit
            if (item.hasCapability(ItemType.FRUIT)) {
                if (this.target.hasCapability(DinosaurSpecies.STEGOSAUR)) {
                    this.target.heal(10);
                }
                else if (this.target.hasCapability(DinosaurSpecies.BRACHIOSAUR)) {
                    this.target.heal(20);
                }
                description = actor + " feeds " + this.target + " fruit";
            }
            // different types of Corpse heals Dinosaur with different amount.
            else if (item.hasCapability(ItemType.CORPSE)) {
                if (item.hasCapability(DinosaurSpecies.STEGOSAUR) && item.hasCapability(DinosaurSpecies.ALLOSAUR)) {
                    this.target.heal(50);
                }
                else if (item.hasCapability(DinosaurSpecies.BRACHIOSAUR)) {
                    this.target.heal(100);
                }
                else if (item.hasCapability(DinosaurSpecies.PTERODACTYL)) {
                    this.target.heal(30);
                }
                description = actor + " feeds " + this.target + " corpse";
            }
            else if (item.hasCapability(ItemType.MEALKIT)) {
                this.target.fullRestoration();
                description = actor + " feeds " + this.target + " meal kit";
            }
            else if (item.hasCapability(ItemType.EGG)) {
                this.target.heal(10);
                description = actor + " feeds " + this.target + " egg";
            }
            actor.removeItemFromInventory(item);
            EcoPoints.increase_points(10);
            return description;
        }

        return actor + " fails to feed dinosaur";
    }

    /**
     * Shows user all valid feeding dinosaur into a hashmap. After user have chose an adjacent dinosaur, save the
     * dinosaur and its dinosaur diet.
     * Valid feeding dinosaur is dinosaur next to the player, and its food is inside player's inventory.
     *
     * @param actor player who wants to feed
     * @param map map where player is located
     */
    public void dinosaursMenu(Actor actor, GameMap map) {
        HashMap<String, Actor> dinosaurs = new HashMap<>();
        Location here = map.locationOf(actor);

        System.out.println("---------------------------------- Feeding ----------------------------------");
        DinosaurDiet dinosaurDiet = null;
        for (Exit thisExit : here.getExits()) {
            Location destination = thisExit.getDestination();
            if (destination.containsAnActor()) {
                if (destination.getActor().hasCapability(DinosaurDiet.HERBIVORE)) {
                    dinosaurDiet = DinosaurDiet.HERBIVORE;
                }
                else if (destination.getActor().hasCapability(DinosaurDiet.CARNIVORE)) {
                    dinosaurDiet = DinosaurDiet.CARNIVORE;
                }
                for (Item thisItem : actor.getInventory()) {
                    if (thisItem.hasCapability(dinosaurDiet)) {
                        System.out.println(thisExit.getHotKey() + ") feeds dinosaur at " + thisExit.getName());
                        dinosaurs.put(thisExit.getHotKey(), thisExit.getDestination().getActor());
                        break;
                    }
                }
            }
            dinosaurDiet = null;
        }
        if (dinosaurs.size() == 0) {
            System.out.println("No available dinosaurs");
        }
        System.out.println("-----------------------------------------------------------------------------");

        String option;
        do {
            System.out.print("Enter dinosaur to be fed: ");
            option = scanner.next();
        } while (!dinosaurs.containsKey(option));

        // saves target and its DinosaurDiet
        try {
            this.target = (Dinosaur) dinosaurs.get(option);
        }
        catch (ClassCastException e) {
            System.out.println("Invalid actor");
        }
        if (this.target.hasCapability(DinosaurDiet.HERBIVORE)) {
            this.targetDinosaurDiet = DinosaurDiet.HERBIVORE;
        }
        else if (this.target.hasCapability(DinosaurDiet.CARNIVORE)) {
            this.targetDinosaurDiet = DinosaurDiet.CARNIVORE;
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " feeds dinosaur";
    }

    @Override
    public String hotkey() {
        return "f";
    }
}
