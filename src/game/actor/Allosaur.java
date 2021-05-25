package game.actor;

import edu.monash.fit2099.engine.*;
import game.behaviour.*;
import game.enumeration.*;

public class Allosaur extends Dinosaur {

    private final static int MAX_HIT_POINTS = 100;
    private final static int SATISFY_HIT_POINTS = 90;
    private final static int HUNGRY_HIT_POINTS = 50;
    private final static int THIRSTY_WATER_LEVEL = 40;
    private final static int MAX_UNCONSCIOUS_TURNS = 20;
    private final static int MAX_PREGNANT_TURNS = 10;
    private final static int MAX_WATER_LEVEL = 100;
    private final static int MAX_BABY_TURNS = 50;
    private final static GroundType TARGET_FOOD_SOURCE_TYPE = null; // Allosaur don't have a target food source
    private final static GroundType TARGET_WATER_SOURCE_TYPE = GroundType.LAKE;
    private static int totalMale = 0;
    private static int totalFemale = 0;
    private DinosaurGender oppositeGender;

    public Allosaur(String name) {
        super(name, 'a', MAX_HIT_POINTS);
        initialization();
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
        addCapability(DinosaurSpecies.ALLOSAUR);
        addCapability(DinosaurDiet.CARNIVORE);
        this.decideGender();
        this.addCapability(Status.HUNGRY);
        this.addBehaviour(new ThirstyBehaviour(TARGET_WATER_SOURCE_TYPE));
        this.addBehaviour(new MateBehaviour(DinosaurSpecies.ALLOSAUR, this.oppositeGender));
        this.addBehaviour(new HungryBehaviour(TARGET_FOOD_SOURCE_TYPE));
        this.addBehaviour(new WanderBehaviour());
    }

    /**
     * Try to maintain a same number of male and female Stegosaur. If the total are the same, produce a female Stegosaur.
     *
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
        this.displayChar = 'A';
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return super.playTurn(actions, lastAction, map, display);
    }

}
