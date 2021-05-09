package game.behaviour;

import edu.monash.fit2099.engine.*;
import game.behaviour.Behaviour;

/**
 * A class that will move the actor one step closer to its destination.
 */
public class TravelBehaviour implements Behaviour {

    private Location destination;
    private final static String name = "TRAVEL";

    public TravelBehaviour(Location location) {
        this.destination = location;
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (!map.contains(actor)) {
            return null;
        }

        Location here = map.locationOf(actor);

        int currentDistance = distance(here, this.destination);
        for (Exit exit : here.getExits()) {
            Location newDestination = exit.getDestination();
            if (newDestination.canActorEnter(actor)) {
                int newDistance = distance(newDestination, this.destination);
                if (newDistance < currentDistance) {
                    return new MoveActorAction(newDestination, exit.getName());
                }
            }
        }

        return null;
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

    /**
     * Returns Behaviour name for others to check what kind of Behaviour it is.
     *
     * @return Behaviour name
     */
    public String getName() {
        return name;
    }

}
