package game.behaviour;

import edu.monash.fit2099.engine.*;
import game.enumeration.GroundType;
import game.enumeration.Status;

/**
 * A class that moves Dinosaur to a ground with a particular ground type when not hungry.
 */
public class ReturnBehaviour implements Behaviour{

    private GroundType homeType;
    private Location home;

    public ReturnBehaviour(GroundType groundType) {
        this.homeType = groundType;
    }

    /**
     * If home doesn't contain actor, moves Dinosaur towards its home.
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return MoveActorAction
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (!actor.hasCapability(Status.SATISFY)) {
            return null;
        }

        Location here = map.locationOf(actor);

        // if Dinosaur is on a ground with the homeType, it will stay there and do nothing
        if (here.getGround().hasCapability(this.homeType)) {
            return new DoNothingAction();
        }

        // make sure the home has no actor
        if (this.home == null) {
            this.home = this.findHome(actor, map);
        }
        else if (this.home.containsAnActor()) {
            this.home = this.findHome(actor, map);
        }

        // return null if can't finds any home
        if (this.home == null) {
            return null;
        }

        // travel to its home
        int currentDistance = distance(here, this.home);
        for (Exit exit : here.getExits()) {
            Location newDestination = exit.getDestination();
            if (newDestination.canActorEnter(actor)) {
                int newDistance = distance(newDestination, this.home);
                if (newDistance < currentDistance) {
                    return new MoveActorAction(newDestination, exit.getName());
                }
            }
        }

        return null;
    }

    /**
     * Find a location of a nearest home.
     * D current Location and location that contains actor are not considered.
     *
     * @param actor Actor that is hungry
     * @param map   Map that contains the Actor
     * @return location of a FruitPlant
     */
    public Location findHome(Actor actor, GameMap map) {
        if (!map.contains(actor)) {
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
                if (thisGround.hasCapability(this.homeType)) {
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


