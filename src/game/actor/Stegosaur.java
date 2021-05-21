package game.actor;


import edu.monash.fit2099.engine.*;
import game.action.AttackAction;
import game.action.LayEggAction;
import game.behaviour.*;
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
    private final static int MAX_PREGNANT_TURNS = 10;
    private final static int MAX_WATER_LEVEL = 100;
    private final static int THIRSTY_WATER_LEVEL = 40;
    private final static GroundType TARGET_FOOD_SOURCE_TYPE = GroundType.FRUITPLANT;
    private final static GroundType TARGET_WATER_SOURCE_TYPE = GroundType.LAKE;
    private static int totalMale = 0;
    private static int totalFemale = 0;
    private DinosaurGender oppositeGender;
    private int unconsciousTurns = 0;
    private int pregnantTurns = 0;
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
        this.setThirstLevel(60);
        addCapability(DinosaurSpecies.STEGOSAUR);
        this.decideGender();
        this.addCapability(Status.HUNGRY);
        this.actionFactories.add(new ThirstyBehaviour(TARGET_WATER_SOURCE_TYPE));
        this.actionFactories.add(new MateBehaviour(DinosaurSpecies.STEGOSAUR, this.oppositeGender));
        this.actionFactories.add(new HungryBehaviour(TARGET_FOOD_SOURCE_TYPE));
        this.actionFactories.add(new WanderBehaviour());

    }

    /**
     * Try to maintain a same number of male and female Stegosaur. If the total are the same, produce a female Stegosaur.
     *
     */
    private void decideGender() {
        if (totalMale < totalFemale) {
            totalMale++;
            this.addCapability(DinosaurGender.MALE);
            this.oppositeGender = DinosaurGender.FEMALE;
        }
        else {
            totalFemale++;
            this.addCapability(DinosaurGender.FEMALE);
            this.oppositeGender = DinosaurGender.MALE;
        }
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
        // if unconscious, count the unconscious length and do nothing
        if (!this.isConscious() || this.isThirsty()) {
            this.unconsciousTurns++;
            // if reached max unconscious turns, dinosaur dies
            if (this.unconsciousTurns == MAX_UNCONSCIOUS_TURNS) {
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
            // reset unconscious turns
            if (this.unconsciousTurns > 0) {
                this.unconsciousTurns = 0;
            }
            this.hurt(1);
            this.decreaseThirst();
        }

        // if pregnancy is mature, lay an egg
        if (this.hasCapability(Status.PREGNANT)) {
            if (this.pregnantTurns == MAX_PREGNANT_TURNS) {
                this.pregnantTurns = 0;
                this.removeCapability(Status.PREGNANT);
                return new LayEggAction();
            }
            else {
                this.pregnantTurns++;
            }
        }

        // if Stegasaur is thirsty, print message
        if (this.getThirstLevel() < THIRSTY_WATER_LEVEL) {
            Location location = map.locationOf(this);
            int x = location.x();
            int y = location.y();
            System.out.println("Stegosaur at (" + x + ", " + y + ") is getting thirsty!");
            this.addCapability(Status.THIRSTY);
        }else if (this.getThirstLevel() > THIRSTY_WATER_LEVEL){
            this.removeCapability(Status.THIRSTY);
        }

        // if Stegosaur is hungry, print message
        if (this.hitPoints < SATISFIED_HIT_POINTS) {
            Location location = map.locationOf(this);
            int x = location.x();
            int y = location.y();
            System.out.println("Stegosaur at (" + x + ", " + y + ") is getting hungry!");
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
