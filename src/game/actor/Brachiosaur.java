package game.actor;

import edu.monash.fit2099.engine.*;
import game.behaviour.Behaviour;
import game.behaviour.HungryBehaviour;
import game.behaviour.MateBehaviour;
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
    private final static GroundType TARGET_FOOD_SOURCE_TYPE = GroundType.TREE;
    private static int totalMale = 0;
    private static int totalFemale = 0;
    private DinosaurGender oppositeGender;
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
        this.decideGender();
        this.addCapability(Status.HUNGRY);
        this.actionFactories.add(new MateBehaviour(DinosaurSpecies.BRACHIOSAUR, this.oppositeGender));
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

        // if unconscious, count the unconscious length and do nothing
        if (!this.isConscious()) {
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
