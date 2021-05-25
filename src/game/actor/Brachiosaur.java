package game.actor;

import edu.monash.fit2099.engine.*;
import game.action.DeadAction;
import game.action.LayEggAction;
import game.behaviour.*;
import game.enumeration.*;
import game.ground.Dirt;
import java.util.Random;

/**
 * A long neck herbivorous dinosaur. 'b' represents baby Brachiosaur, 'B' represents adult Brachiosaur.
 */
public class Brachiosaur extends Dinosaur {

    Random random = new Random();
    private final static int MAX_HIT_POINTS = 160;
    private final static int SATISFY_HIT_POINTS = 140;
    private final static int HUNGRY_HIT_POINTS = 70;
    private final static int THIRSTY_WATER_LEVEL = 40;
    private final static int MAX_UNCONSCIOUS_TURNS = 15;
    private final static int MAX_PREGNANT_TURNS = 30;
    private final static int MAX_WATER_LEVEL = 200;
    private final static int MAX_BABY_TURNS = 50;
    private final static GroundType TARGET_FOOD_SOURCE_TYPE = GroundType.TREE;
    private final static GroundType TARGET_WATER_SOURCE_TYPE = GroundType.LAKE;
    private static int totalMale = 0;
    private static int totalFemale = 0;
    private DinosaurGender oppositeGender;

    /**
     * Constructor.
     * All Brachiosaur are represented by a 'b' or 'B' and have 160 max hit points but start with 100 hit points.
     *
     * @param name the name of Brachiosaur
     */
    public Brachiosaur(String name) {
        super(name, 'b', MAX_HIT_POINTS);
        initialization();
    }

    /**
     * Constructor, mainly for creating adult Brachiosaur at start of the game.
     * All Brachiosaur are represented by a 'b' or 'B' and have 160 max hit points but start with 100 hit points.
     *
     * @param name the name of Brachiosaur
     * @param isAdult boolean to set this Brachiosaur adult or not
     */
    public Brachiosaur(String name, boolean isAdult) {
        super(name, 'b', MAX_HIT_POINTS);
        initialization();
        if (isAdult) {
            this.grownUp();
        }
    }

    @Override
    public void initialization() {
        this.hitPoints = 100;
        this.setWaterLevel(60);
        this.addCapability(Status.BABY);
        this.setSatisfyHitPoints(SATISFY_HIT_POINTS);
        this.setHungryHitPoints(HUNGRY_HIT_POINTS);
        this.setThirstyWaterLevel(THIRSTY_WATER_LEVEL);
        this.setMaxUnconsciousTurns(MAX_UNCONSCIOUS_TURNS);
        this.setMaxPregnantTurns(MAX_PREGNANT_TURNS);
        this.setMaxWaterLevel(MAX_WATER_LEVEL);
        this.setMaxBabyTurns(MAX_BABY_TURNS);
        addCapability(DinosaurSpecies.BRACHIOSAUR);
        addCapability(DinosaurDiet.HERBIVORE);
        this.decideGender();
        this.addCapability(Status.HUNGRY);
        this.addBehaviour(new ThirstyBehaviour(TARGET_WATER_SOURCE_TYPE));
        this.addBehaviour(new MateBehaviour(DinosaurSpecies.BRACHIOSAUR, this.oppositeGender));
        this.addBehaviour(new HungryBehaviour(TARGET_FOOD_SOURCE_TYPE));
        this.addBehaviour(new WanderBehaviour());
    }

    /**
     * Try to maintain a same number of male and female Brachiosaur. If the total are the same, produce a female
     * Brachiosaur.
     */
    @Override
    public void decideGender() {
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
    public void grownUp() {
        this.removeCapability(Status.BABY);
        this.addCapability(Status.ADULT);
        this.displayChar = 'B';
    }

    /**
     * If Brachiosaur is conscious and not laying egg, it will either try mating, try eating, or wandering around.
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
        // in its current Location, if it is standing on a bush, it have 50% to kill the bush
        Location thisLocation = map.locationOf(this);
        Ground thisGround = thisLocation.getGround();
        if (thisGround.hasCapability(GroundType.BUSH)) {
            int rand = random.nextInt(2);
            if (rand == 1)
                thisLocation.setGround(new Dirt());
        }

        return super.playTurn(actions, lastAction, map, display);
    }

}
