package game.actor;


import edu.monash.fit2099.engine.*;
import game.action.DeadAction;
import game.action.LayEggAction;
import game.behaviour.*;
import game.enumeration.*;

/**
 * A herbivorous dinosaur. 's' represents baby Stegosaur, 'S' represents adult Stegosaur.
 */
public class Stegosaur extends Dinosaur {

    private final static int MAX_HIT_POINTS = 100;
    private final static int SATISFY_HIT_POINTS = 90;
    private final static int HUNGRY_HIT_POINTS = 50;
    private final static int THIRSTY_WATER_LEVEL = 40;
    private final static int MAX_UNCONSCIOUS_TURNS = 20;
    private final static int MAX_PREGNANT_TURNS = 10;
    private final static int MAX_WATER_LEVEL = 100;
    private final static int MAX_BABY_TURNS = 30;
    private final static int MAX_WOUNDED_TURNS = 20;
    private final static GroundType TARGET_FOOD_SOURCE_TYPE = GroundType.FRUITPLANT;
    private final static GroundType TARGET_WATER_SOURCE_TYPE = GroundType.LAKE;
    private static int totalMale = 0;
    private static int totalFemale = 0;
    private DinosaurGender oppositeGender;
    private int woundedTurns = 0;

    /**
     * Constructor.
     * All Stegosaurs are represented by a 's' or 'S' and have 100 max hit points but start with 50 hit points.
     *
     * @param name the name of this Stegosaur
     */
    public Stegosaur(String name) {
        super(name, 's', MAX_HIT_POINTS);
        initialization();
    }

    /**
     * Constructor, mainly for creating adult Stegosaur at start of the game.
     * All Stegosaurs are represented by a 's' or 'S' and have 100 max hit points but start with 50 hit points.
     *
     * @param name the name of this Stegosaur
     * @param isAdult boolean to set this Stegosaur adult or not
     */
    public Stegosaur(String name, boolean isAdult) {
        super(name, 's', MAX_HIT_POINTS);
        initialization();
        if (isAdult) {
            this.grownUp();
        }
    }

    @Override
    public void initialization() {
        this.hitPoints = 50;
        this.setWaterLevel(60);
        this.addCapability(Status.BABY);
        this.setSatisfyHitPoints(SATISFY_HIT_POINTS);
        this.setHungryHitPoints(HUNGRY_HIT_POINTS);
        this.setThirstyWaterLevel(THIRSTY_WATER_LEVEL);
        this.setMaxUnconsciousTurns(MAX_UNCONSCIOUS_TURNS);
        this.setMaxPregnantTurns(MAX_PREGNANT_TURNS);
        this.setMaxWaterLevel(MAX_WATER_LEVEL);
        this.setMaxBabyTurns(MAX_BABY_TURNS);
        addCapability(DinosaurSpecies.STEGOSAUR);
        addCapability(DinosaurDiet.HERBIVORE);
        this.decideGender();
        this.addCapability(Status.HUNGRY);
        this.addBehaviour(new ThirstyBehaviour(TARGET_WATER_SOURCE_TYPE));
        this.addBehaviour(new MateBehaviour(DinosaurSpecies.STEGOSAUR, this.oppositeGender));
        this.addBehaviour(new HungryBehaviour(TARGET_FOOD_SOURCE_TYPE));
        this.addBehaviour(new WanderBehaviour());
    }

    /**
     * Try to maintain a same number of male and female Stegosaur. If the total are the same, produce a female
     * Stegosaur.
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
        this.displayChar = 'S';
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
        Action action = super.playTurn(actions, lastAction, map, display);

        // if recovered from attack set isWounded back to false and reset woundedTurns
        if (this.hasCapability(Status.WOUNDED)) {
            if (this.woundedTurns == MAX_WOUNDED_TURNS) {
                this.removeCapability(Status.WOUNDED);
                this.woundedTurns = 0;
            }
            this.woundedTurns++;
        }

        return action;
    }

}
