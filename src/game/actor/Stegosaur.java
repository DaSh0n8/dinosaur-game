package game.actor;


import edu.monash.fit2099.engine.*;
import game.action.AttackAction;
import game.behaviour.Behaviour;
import game.behaviour.HungryBehaviour;
import game.behaviour.MateBehaviour;
import game.behaviour.WanderBehaviour;
import game.enumeration.DinosaurGender;
import game.enumeration.DinosaurSpecies;
import game.enumeration.GroundType;
import game.enumeration.Status;
import game.ground.Corpse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A herbivorous dinosaur.
 */
public class Stegosaur extends Dinosaur {
    private final static int MAX_HIT_POINTS = 100;
    private final static int SATISFIED_HIT_POINTS = 90;
    private final static int HUNGRY_HIT_POINTS = 50;
    private final static int MAX_UNCONSCIOUS_TURNS = 20;
    private final static int MAX_PREGNANT_TURNS = 10;
    private final static GroundType TARGET_FOOD_SOURCE_TYPE = GroundType.FRUITPLANT;
    private static int totalMale = 0;
    private static int totalFemale = 0;
    private DinosaurGender oppositeGender;
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
        this.decideGender();
        this.addCapability(Status.HUNGRY);
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
     * If Stegosaur is conscious and not laying egg, it will either try mating, try eating, or wandering around.
     * The priority is:
     * If it has Status SATISFY, it will first try to breed with another valid Dinosaur next to it. If not possible,
     * then it will try to search for a nearest valid Dinosaur and follows it. If didn't found one, then it will wanders
     * around.
     * If it has Status HUNGRY, it will first try to breed with another valid Dinosaur next to it. If not possible, it
     * will try to eat foods at its current Location. If eating is not valid, it will move towards a valid food source.
     * If it has Status STARVE, it will first try to eat at its current Location. Otherwise, it will move towards a
     * valid food source.
     *
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
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
