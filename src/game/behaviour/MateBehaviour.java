package game.behaviour;

import edu.monash.fit2099.engine.*;
import game.action.BreedAction;
import game.actor.Dinosaur;
import game.enumeration.DinosaurGender;
import game.enumeration.DinosaurSpecies;
import game.enumeration.GroundType;
import game.enumeration.Status;

/**
 * A class that moves the actor one step forward to its nearest opposite sex Dinosaur. It will breed when it is not
 * hungry and an opposite sex Dinosaur with same species is next to it.
 */
public class MateBehaviour implements Behaviour{

    private DinosaurSpecies targetSpecies;
    private DinosaurGender targetGender;
    private Actor targetMatingPartner = null;

    public MateBehaviour (DinosaurSpecies dinosaurSpecies, DinosaurGender dinosaurGender) {
        this.targetSpecies = dinosaurSpecies;
        this.targetGender = dinosaurGender;
    }

    /**
     * This Dinosaur will try to mate with a valid Dinosaur next to it. If there is none and this Dinosaur is not hungry
     * at all, it will try to find a nearest target and follows it.
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return BreedAction if there is a valid Dinosaur next to this Dinosaur, or MoveActorAction for moving this
     * Dinosaur to its target when this Dinosaur is not hungry at all
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (!map.contains(actor) || actor.hasCapability(Status.STARVE) || actor.hasCapability(Status.PREGNANT)) {
            return null;
        }

        Location here = map.locationOf(actor);

        // if there is a valid Dinosaur next to it, try continue mating
        Actor target = this.adjacentMatingPartner(here, map);
        if (target != null) {
            this.targetMatingPartner = null;
            return new BreedAction(target);
        }

        // if this Dinosaur is not hungry at all, makes sure it has a target partner
        if (!actor.hasCapability(Status.SATISFY)) {
            return null;
        }
        else {
            if (this.targetMatingPartner == null) {
                this.targetMatingPartner = findMatingPartner(actor, map);
                // if there is no valid target to follow
                if (this.targetMatingPartner == null) {
                    return null;
                }
            }
        }
        // moves one step towards its target partner
        Location there = map.locationOf(this.targetMatingPartner);
        int currentDistance = distance(here, there);
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                int newDistance = distance(destination, there);
                if (newDistance < currentDistance) {
                    return new MoveActorAction(destination, exit.getName());
                }
            }
        }

        return null;
    }

    /**
     * The Dinosaur will checks if there is a valid target Dinosaur next to it.
     * A valid target Dinosaur must have the same species, opposite sex, and not pregnant.
     *
     * @param location location of this Dinosaur
     * @param map map where this Dinosaur located
     * @return valid target Dinosaur next to it
     */
    public Actor adjacentMatingPartner(Location location, GameMap map) {
        int x = location.x();
        int y = location.y();
        int maxX = map.getXRange().max();
        int maxY = map.getYRange().max();
        int minX = map.getXRange().min();
        int minY = map.getYRange().min();

        Actor target;
        // check Dinosaur on above
        if (y-1 >= minY) {
            if (map.at(x, y-1).containsAnActor()) {
                target = map.at(x, y-1).getActor();
                if (target.hasCapability(this.targetSpecies) && target.hasCapability(this.targetGender)
                        && !target.hasCapability(Status.PREGNANT))
                    return target;
            }
        }
        // check Dinosaur on upper right
        if (x+1 <= maxX && y-1 >= minY) {
            if (map.at(x+1, y-1).containsAnActor()) {
                target = map.at(x+1, y-1).getActor();
                if (target.hasCapability(this.targetSpecies) && target.hasCapability(this.targetGender)
                        && !target.hasCapability(Status.PREGNANT))
                    return target;
            }
        }
        // check Dinosaur on right
        if (x+1 <= maxX) {
            if (map.at(x+1, y).containsAnActor()) {
                target = map.at(x+1, y).getActor();
                if (target.hasCapability(this.targetSpecies) && target.hasCapability(this.targetGender)
                        && !target.hasCapability(Status.PREGNANT))
                    return target;
            }
        }
        // check Dinosaur on lower right
        if (x+1 <= maxX && y+1 <= maxY) {
            if (map.at(x+1, y+1).containsAnActor()) {
                target = map.at(x+1, y+1).getActor();
                if (target.hasCapability(this.targetSpecies) && target.hasCapability(this.targetGender)
                        && !target.hasCapability(Status.PREGNANT))
                    return target;
            }
        }
        // check Dinosaur on below
        if (y+1 <= maxY) {
            if (map.at(x, y+1).containsAnActor()) {
                target = map.at(x, y+1).getActor();
                if (target.hasCapability(this.targetSpecies) && target.hasCapability(this.targetGender)
                        && !target.hasCapability(Status.PREGNANT))
                    return target;
            }
        }
        // check Dinosaur on lower left
        if (x-1 >= minX && y+1 <= maxY) {
            if (map.at(x-1, y+1).containsAnActor()) {
                target = map.at(x-1, y+1).getActor();
                if (target.hasCapability(this.targetSpecies) && target.hasCapability(this.targetGender)
                        && !target.hasCapability(Status.PREGNANT))
                    return target;
            }
        }
        // check Dinosaur on left
        if (x-1 >= minX) {
            if (map.at(x-1, y).containsAnActor()) {
                target = map.at(x-1, y).getActor();
                if (target.hasCapability(this.targetSpecies) && target.hasCapability(this.targetGender)
                        && !target.hasCapability(Status.PREGNANT))
                    return target;
            }
        }
        // check Dinosaur on upper left
        if (x-1 >= minX && y-1 >= minY) {
            if (map.at(x-1, y-1).containsAnActor()) {
                target = map.at(x-1, y-1).getActor();
                if (target.hasCapability(this.targetSpecies) && target.hasCapability(this.targetGender)
                        && !target.hasCapability(Status.PREGNANT))
                    return target;
            }
        }

        return null;
    }

    /**
     * Find the nearest valid Dinosaur which has the same species, opposite sex and not pregnant.
     *
     * @param actor Actor that want mating
     * @param map   Map that contains the Actor
     * @return nearest valid Dinosaur to follow
     */
    public Actor findMatingPartner(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);
        Actor target = null;

        // search target from top left corner of the map, and find the nearest one
        int topLeftX = map.getXRange().min();
        int topLeftY = map.getYRange().min();
        Location topLeftCorner = map.at(topLeftX, topLeftY);
        int minDistance = distance(here, topLeftCorner);
        // start searching for Dinosaur
        NumberRange heights = map.getYRange();
        NumberRange widths = map.getXRange();
        for (int y : heights) {
            for (int x : widths) {
                Location thisLocation = map.at(x, y);
                if (map.at(x, y).containsAnActor()) {
                    Actor thisActor = thisLocation.getActor();
                    if (thisActor.hasCapability(this.targetSpecies) && thisActor.hasCapability(this.targetGender)
                            && !thisActor.hasCapability(Status.PREGNANT)) {
                        int thisDistance = distance(here, thisLocation);
                        if (thisDistance < minDistance && thisDistance != 0) {
                            minDistance = thisDistance;
                            target = thisActor;
                        }
                    }
                }
            }
        }

        return target;
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
