package game.actor;


import edu.monash.fit2099.engine.*;
import game.action.AttackAction;
import game.behaviour.Behaviour;
import game.behaviour.HungryBehaviour;
import game.behaviour.WanderBehaviour;
import game.enumeration.DinosaurGender;
import game.enumeration.DinosaurSpecies;
import game.enumeration.GroundType;
import game.enumeration.Status;
import game.ground.Corpse;

import java.util.ArrayList;
import java.util.List;

/**
 * A herbivorous dinosaur.
 */
public class Stegosaur extends Dinosaur {
    // Will need to change this to a collection if Stegosaur gets additional Behaviours.
    private final static int MAX_HIT_POINTS = 100;
    private final static int SATISFIED_HIT_POINTS = 90;
    private final static int HUNGRY_HIT_POINTS = 50;
    private final static int MAX_UNCONSCIOUS_TURNS = 20;
    private final static GroundType TARGET_FOOD_SOURCE_TYPE = GroundType.FRUITPLANT;
    private List<Behaviour> actionFactories = new ArrayList<>();

    /**
     * Constructor.
     * All Stegosaurs are represented by a 'd' and have 100 max hit points but start with 50 hit points.
     *
     * @param name the name of this Stegosaur
     */
    public Stegosaur(String name) {
        super(name, 'd', MAX_HIT_POINTS);
        this.hurt(50);
        addCapability(DinosaurSpecies.STEGOSAUR);
        this.addCapability(Status.HUNGRY);
        this.actionFactories.add(new HungryBehaviour(TARGET_FOOD_SOURCE_TYPE));
        this.actionFactories.add(new WanderBehaviour());

    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return new Actions(new AttackAction(this));
    }

    /**
     * Figure out what to do next.
     * <p>
     * FIXME: Stegosaur wanders around at random, or if no suitable MoveActions are available, it
     * just stands there.  That's boring.
     *
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        // if Stegasaur is hungry, print message
        if (this.hitPoints < SATISFIED_HIT_POINTS) {
            Location location = map.locationOf(this);
            int x = location.x();
            int y = location.y();
            System.out.println("Stegosaur at (" + x + ", " + y + ") is getting hungry!");
        }

        // if unconscious, count the unconscious length and do nothing
        if (!this.isConscious()) {
            this.incrementUnconsciousTurns();
            // if reached max unconscious turns, dinosaur dies
            if (getUnconsciousTurns() == MAX_UNCONSCIOUS_TURNS) {
                Location location = map.locationOf(this);
                location.setGround(new Corpse(DinosaurSpecies.STEGOSAUR));
                map.removeActor(this);
                return new DoNothingAction();
            }
            else {
                return new DoNothingAction();
            }
        }
        else {
            this.hurt(1);
        }

        // adjust Dinosaur Status
        if (this.hitPoints >= SATISFIED_HIT_POINTS) {
            if (!this.hasCapability(Status.SATISFY)) {
                this.addCapability(Status.SATISFY);
                this.removeCapability(Status.HUNGRY);
                this.removeCapability(Status.STARVE);
            }
        }
        else if (this.hitPoints >= HUNGRY_HIT_POINTS) {
            if (!this.hasCapability(Status.HUNGRY)) {
                this.addCapability(Status.HUNGRY);
                this.removeCapability(Status.SATISFY);
                this.removeCapability(Status.STARVE);
            }
        }
        else {
            if (!this.hasCapability(Status.STARVE)) {
                this.addCapability(Status.STARVE);
                this.removeCapability(Status.SATISFY);
                this.removeCapability(Status.HUNGRY);
            }
        }

        // get one valid Behaviour among all
        for (Behaviour thisFactory : this.actionFactories) {
            Action thisAction = thisFactory.getAction(this, map);
            if (thisAction != null) {
                return thisAction;
            }
        }

        return new DoNothingAction();
    }

    @Override
    public Location findMatingPartner(GameMap map) {
        if (!map.contains(this)) {
            return null;
        }

        Location here = map.locationOf(this);
        int topLeftX = map.getXRange().min();
        int topLeftY = map.getYRange().min();
        Location there = map.at(topLeftX, topLeftY);
        int minDistance = distance(here, there);

        NumberRange heights = map.getYRange();
        NumberRange widths = map.getXRange();
        for (int y : heights) {
            for (int x : widths) {
                Location thisLocation = map.at(x, y);
                if (thisLocation.containsAnActor()){
                    if (thisLocation.getActor().hasCapability(DinosaurSpecies.STEGOSAUR)){
                        int thisDistance = distance(here,thisLocation);
                        if (thisDistance < minDistance && thisDistance != 0){
                            minDistance = thisDistance;
                            there = thisLocation;
                        }
                    }
                }
            }
        }
        return there;
    }

    @Override
    public boolean surroundingMatingPartner(Location location, GameMap map) {
        int x = location.x();
        int y = location.y();
        int maxX = map.getXRange().max();
        int maxY = map.getYRange().max();
        int minX = map.getXRange().min();
        int minY = map.getYRange().min();

        if(y-2 >= minY){
            if(map.at(x,y-2).getActor().hasCapability(DinosaurSpecies.STEGOSAUR) || map.at(x,y-2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        if (y-2 >= minY) {
            if(map.at(x,y-2).getActor().hasCapability(DinosaurSpecies.STEGOSAUR) || map.at(x,y-2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on upper right
        if (x+2 <= maxX && y-2 >= minY) {
            if(map.at(x+2,y-2).getActor().hasCapability(DinosaurSpecies.STEGOSAUR) || map.at(x+2,y-2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on right
        if (x+1 <= maxX) {
            if(map.at(x+2,y).getActor().hasCapability(DinosaurSpecies.STEGOSAUR) || map.at(x+2,y).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on lower right
        if (x+2 <= maxX && y+2 <= maxY) {
            if(map.at(x+2,y+2).getActor().hasCapability(DinosaurSpecies.STEGOSAUR) || map.at(x+2,y+2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on below
        if (y+2 <= maxY) {
            if(map.at(x,y+2).getActor().hasCapability(DinosaurSpecies.STEGOSAUR) || map.at(x,y+2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on lower left
        if (x-2 >= minX && y+2 <= maxY) {
            if(map.at(x-2,y+2).getActor().hasCapability(DinosaurSpecies.STEGOSAUR) || map.at(x-2,y+2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on left
        if (x-2 >= minX) {
            if(map.at(x-2,y).getActor().hasCapability(DinosaurSpecies.STEGOSAUR) || map.at(x-2,y).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on upper left
        if (x-2 >= minX && y-2 >= minY) {
            if(map.at(x-2,y-2).getActor().hasCapability(DinosaurSpecies.STEGOSAUR) || map.at(x-2,y-2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }

        return false;
    }

    /**
     * Compute the Manhattan distance between two locations.
     *
     * @param a the first location
     * @param b the second location
     * @return the number of steps between a and b if you only move in the four cardinal directions.
     */
    private static int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    public void Death(){
        int uncturns = getUnconsciousTurns();
        if (MAX_UNCONSCIOUS_TURNS == uncturns){

        }
    }

}
