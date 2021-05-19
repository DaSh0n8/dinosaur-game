package game.actor;

import edu.monash.fit2099.engine.*;
import game.behaviour.Behaviour;
import game.behaviour.HungryBehaviour;
import game.behaviour.WanderBehaviour;
import game.enumeration.DinosaurGender;
import game.enumeration.DinosaurSpecies;
import game.enumeration.GroundType;
import game.enumeration.Status;
import game.ground.Corpse;
import game.ground.Dirt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Brachiosaur extends Dinosaur {
    Random random = new Random();
    private final static int MAX_HIT_POINTS = 160;
    private final static int SATISFIED_HIT_POINTS = 140;
    private final static int HUNGRY_HIT_POINTS = 70;
    private final static int MAX_UNCONSCIOUS_TURNS = 15;
    private final static int MAX_WATER_LEVEL = 200;
    private final static int THIRSTY_WATER_LEVEL = 40;
    private final static GroundType TARGET_FOOD_SOURCE_TYPE = GroundType.TREE;
    private List<Behaviour> actionFactories = new ArrayList<>();

    /**
     * Constructor.
     * All Brachiosaur are represented by a 'B' and have 160 max hit points but start with 100 hit points.
     *
     * @param name the name of Brachiosaur
     */
    public Brachiosaur(String name) {
        super(name, 'B', MAX_HIT_POINTS);
        this.hurt(60);
        addCapability(DinosaurSpecies.BRACHIOSAUR);
        this.addCapability(Status.HUNGRY);
        this.actionFactories.add(new HungryBehaviour(TARGET_FOOD_SOURCE_TYPE));
        this.actionFactories.add(new WanderBehaviour());
    }

    /**
     * Brachiosaur will move towards a tree when it's hungry. Otherwise, it will wander around.
     *
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        // if Brachiosaur is hungry, print message
        if (this.hitPoints < SATISFIED_HIT_POINTS) {
            Location location = map.locationOf(this);
            int x = location.x();
            int y = location.y();
            System.out.println("Brachiosaur at (" + x + ", " + y + ") is getting hungry!");
        }
        // if Stegasaur is thirsty, print message
        if (this.getThirstLevel() < THIRSTY_WATER_LEVEL) {
            Location location = map.locationOf(this);
            int x = location.x();
            int y = location.y();
            System.out.println("Stegosaur at (" + x + ", " + y + ") is getting thirsty!");
        }

        // if unconscious, count the unconscious length and do nothing
        if (!this.isConscious() || this.isThirsty()) {
            this.incrementUnconsciousTurns();
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
            this.decreaseThirst();
        }

        // in its current Location, if it is standing on a bush, it have 50% to kill the bush
        Location thisLocation = map.locationOf(this);
        Ground thisGround = thisLocation.getGround();
        if (thisGround.hasCapability(GroundType.BUSH)) {
            int rand = random.nextInt(2);
            if (rand == 1)
                thisLocation.setGround(new Dirt());
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
                    if (thisLocation.getActor().hasCapability(DinosaurSpecies.BRACHIOSAUR) || thisLocation.getActor().hasCapability((DinosaurGender.FEMALE))){
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
            if(map.at(x,y-2).getActor().hasCapability(DinosaurSpecies.BRACHIOSAUR) || map.at(x,y-2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        if (y-2 >= minY) {
            if(map.at(x,y-2).getActor().hasCapability(DinosaurSpecies.BRACHIOSAUR) || map.at(x,y-2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on upper right
        if (x+2 <= maxX && y-2 >= minY) {
            if(map.at(x+2,y-2).getActor().hasCapability(DinosaurSpecies.BRACHIOSAUR) || map.at(x+2,y-2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on right
        if (x+1 <= maxX) {
            if(map.at(x+2,y).getActor().hasCapability(DinosaurSpecies.BRACHIOSAUR) || map.at(x+2,y).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on lower right
        if (x+2 <= maxX && y+2 <= maxY) {
            if(map.at(x+2,y+2).getActor().hasCapability(DinosaurSpecies.BRACHIOSAUR) || map.at(x+2,y+2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on below
        if (y+2 <= maxY) {
            if(map.at(x,y+2).getActor().hasCapability(DinosaurSpecies.BRACHIOSAUR) || map.at(x,y+2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on lower left
        if (x-2 >= minX && y+2 <= maxY) {
            if(map.at(x-2,y+2).getActor().hasCapability(DinosaurSpecies.BRACHIOSAUR) || map.at(x-2,y+2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on left
        if (x-2 >= minX) {
            if(map.at(x-2,y).getActor().hasCapability(DinosaurSpecies.BRACHIOSAUR) || map.at(x-2,y).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on upper left
        if (x-2 >= minX && y-2 >= minY) {
            if(map.at(x-2,y-2).getActor().hasCapability(DinosaurSpecies.BRACHIOSAUR) || map.at(x-2,y-2).getActor().hasCapability(DinosaurGender.FEMALE) )
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

}
