package game.behaviour;

import edu.monash.fit2099.engine.*;
import game.action.AttackAction;
import game.action.EatAction;
import game.actor.Stegosaur;
import game.enumeration.DinosaurSpecies;
import game.enumeration.GroundType;
import game.enumeration.ItemType;
import game.enumeration.Status;
import game.ground.FruitPlant;
import game.ground.Lake;

/**
 * A class that will makes the actor eats or moves the actor one step closer to its food source.
 */
public class HungryBehaviour implements Behaviour {

    private Location foodSource = null;

    private GroundType foodSourceType;

    public HungryBehaviour(GroundType foodSourceType) {
        this.foodSourceType = foodSourceType;
    }

    /**
     * If the current place of the Dinosaur is a valid food source and have foods, Dinosaur will eats at the Location.
     * Otherwise, it will searches for a new food source and move towards the Location.
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return EatAction to eat at its current Location, or MoveActorAction to move towards a food source
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (!map.contains(actor) || actor.hasCapability(Status.SATISFY)) {
            return null;
        }

        Location here = map.locationOf(actor);

        // returns EatAction if this Dinosaur can eat at its current location
        Action eatAction = this.checksCurrentLocation(actor, here);
        if (eatAction != null) {
            return eatAction;
        }

        // find a food source if the Dinosaur doesn't have one or is occupied by others
        if (this.foodSource == null) {
            this.foodSource = findFoodSource(actor, map);
        }
        else if (this.foodSource.containsAnActor()) {
            this.foodSource = findFoodSource(actor, map);
        }

        // return null if can't finds any food source
        if (this.foodSource == null) {
            return null;
        }

        // travel to the food source
        int currentDistance = distance(here, this.foodSource);
        for (Exit exit : here.getExits()) {
            Location newDestination = exit.getDestination();
            if (newDestination.canActorEnter(actor)) {
                int newDistance = distance(newDestination, this.foodSource);
                if (newDistance < currentDistance) {
                    return new MoveActorAction(newDestination, exit.getName());
                }
            }
        }

        return null;
    }

    /**
     * Checks Dinosaur current location, if the location contains food for this particular Dinosaur, returns EatAction.
     *
     * @param actor the Actor acting
     * @param here the Actor current location
     * @return EatAction if eating is possible at current location
     */
    public Action checksCurrentLocation(Actor actor, Location here) {
        // if Stegosaur/Brachiosaur on fruit plant, continue eating
        if (here.getGround().hasCapability(this.foodSourceType) && (actor.hasCapability(DinosaurSpecies.STEGOSAUR) ||
                actor.hasCapability(DinosaurSpecies.BRACHIOSAUR))) {
            // if Stegosaur on Bush or Brachiosaur under Tree
            if ((actor.hasCapability(DinosaurSpecies.STEGOSAUR) && here.getGround().hasCapability(GroundType.BUSH)) ||
                    (actor.hasCapability(DinosaurSpecies.BRACHIOSAUR) && here.getGround().hasCapability(GroundType.TREE))) {
                try {
                    FruitPlant plant = (FruitPlant) here.getGround();
                    if (plant.getTotalFruits() > 0) {
                        return new EatAction();
                    }
                } catch (ClassCastException e) {
                    System.out.println("Invalid ground");
                }
            }
            // if Stegosaur under Tree
            else if (actor.hasCapability(DinosaurSpecies.STEGOSAUR) && here.getGround().hasCapability(GroundType.TREE)) {
                for (Item thisItem : here.getItems()) {
                    if (thisItem.hasCapability(ItemType.FRUIT)) {
                        return new EatAction();
                    }
                }
            }
            // since nearest food source (here) has no food, make target source to null to search for another one later
            this.foodSource = null;
        }
        else if (actor.hasCapability(DinosaurSpecies.ALLOSAUR)) {
            // if Allosaur on location that has corpse, continue eating
            for (Item thisItem : here.getItems()) {
                if (thisItem.hasCapability(ItemType.CORPSE)) {
                    return new EatAction();
                }
            }
            // else if Allosaur is next to another Stegosaur that is not wounded, attack
            for (Exit thisExit : here.getExits()) {
                if (thisExit.getDestination().containsAnActor()) {
                    Actor target = thisExit.getDestination().getActor();
                    if (target.hasCapability(DinosaurSpecies.STEGOSAUR) && !target.hasCapability(Status.WOUNDED)) {
                        return new AttackAction(target);
                    }
                }
            }
        }
        else if (actor.hasCapability(DinosaurSpecies.PTERODACTYL)) {
            // if Pterodactyl on location that has corpse, continue eating
            for (Item thisItem : here.getItems()) {
                if (thisItem.hasCapability(ItemType.CORPSE)) {
                    return new EatAction();
                }
            }
            // else if Pterodactyl on lake, continue eating
            if (here.getGround().hasCapability(GroundType.LAKE)) {
                try {
                    Lake lake = (Lake) here.getGround();
                    if (lake.getFishAmount() > 0) {
                        return new EatAction();
                    }
                }
                catch (ClassCastException e) {
                    System.out.println("Invalid ground");
                }
                // since nearest food source (here) has no food, make target source to null to search another one later
                this.foodSource = null;
            }
        }

        return null;
    }

    /**
     * Find a location of a nearest food source for Dinosaur.
     * Dinosaur current Location and location that contains actor are not considered.
     *
     * @param actor Actor that is hungry
     * @param map   Map that contains the Actor
     * @return location of a FruitPlant
     */
    public Location findFoodSource(Actor actor, GameMap map) {
        if (!map.contains(actor) || this.foodSourceType == null) {
            return null;
        }

        Location here = map.locationOf(actor);

        // initialize target place to be the top left corner of the map
        int topLeftX = map.getXRange().min();
        int topLeftY = map.getYRange().min();
        Location there = map.at(topLeftX, topLeftY);
        int minDistance = distance(here, there);
        // find a nearest FruitPlant
        NumberRange heights = map.getYRange();
        NumberRange widths = map.getXRange();
        for (int y : heights) {
            for (int x : widths) {
                Location thisLocation = map.at(x, y);
                Ground thisGround = thisLocation.getGround();
                if (thisGround.hasCapability(this.foodSourceType)) {
                    int thisDistance = distance(here, thisLocation);
                    if (thisDistance < minDistance && !thisLocation.containsAnActor()) {
                        minDistance = thisDistance;
                        there = thisLocation;
                    }
                }
            }
        }

        return there;
    }


    /**
     * Compute the Manhattan distance between two locations.
     *
     * @param a the first location
     * @param b the second location
     * @return the number of steps between a and b if you only move in the four cardinal directions.
     */
    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

}
