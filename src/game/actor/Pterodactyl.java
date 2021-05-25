package game.actor;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import game.behaviour.*;
import game.enumeration.*;

public class Pterodactyl extends Dinosaur{

    private final static int MAX_HIT_POINTS = 100;
    private final static int SATISFY_HIT_POINTS = 90;
    private final static int HUNGRY_HIT_POINTS = 50;
    private final static int THIRSTY_WATER_LEVEL = 40;
    private final static int MAX_UNCONSCIOUS_TURNS = 20;
    private final static int MAX_PREGNANT_TURNS = 10;
    private final static int MAX_WATER_LEVEL = 100;
    private final static int MAX_BABY_TURNS = 30;
    private final static int MAX_FLYING_TURNS = 30;
    private final static GroundType TARGET_FOOD_SOURCE_TYPE = GroundType.LAKE;
    private final static GroundType TARGET_WATER_SOURCE_TYPE = GroundType.LAKE;
    private final static GroundType TARGET_HOME_TYPE = GroundType.TREE;
    private static int totalMale = 0;
    private static int totalFemale = 0;
    private DinosaurGender oppositeGender;
    private int flyingTurns = 0;
    private boolean onLand = false;

    public Pterodactyl(String name) {
        super(name, 'p', MAX_HIT_POINTS);
        initialization();
    }

    public Pterodactyl(String name, boolean isAdult) {
        super(name, 'p', MAX_HIT_POINTS);
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
        addCapability(DinosaurSpecies.PTERODACTYL);
        addCapability(DinosaurDiet.CARNIVORE);
        this.decideGender();
        this.addCapability(Status.HUNGRY);
        this.addBehaviour(new ThirstyBehaviour(TARGET_WATER_SOURCE_TYPE));
        this.addBehaviour(new MateBehaviour(DinosaurSpecies.PTERODACTYL, this.oppositeGender, TARGET_HOME_TYPE));
        this.addBehaviour(new ReturnBehaviour(TARGET_HOME_TYPE));
        this.addBehaviour(new HungryBehaviour(TARGET_FOOD_SOURCE_TYPE));
        this.addBehaviour(new WanderBehaviour());
    }

    /**
     * Try to maintain a same number of male and female Pterodactyl. If the total are the same, produce a female
     * Pterodactyl.
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
        this.displayChar = 'P';
    }

    /**
     * The priority of Pterodactyl's behaviours:
     * If pterodactyl is thirsty, use ThirstyBehaviour to go to a nearest lake to drink.
     * Otherwise if Pterodactyl is not hungry, it will use try to breed.
     * (In addition, if it has a valid partner next to it but not on a tree, MateBehaviour cannot be used)
     * Otherwise if satisfy (not hungry), Pterodactyl will use ReturnBehaviour to move towards a tree, or does nothing
     * on a tree.
     * Otherwise if Pterodactyl is not satisfy, use HungryBehaviour to go to a nearest lake to eat fish. If there is a
     * corpse in the same location, it will stop and eat the corpse.
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Action action = super.playTurn(actions, lastAction, map, display);

        // Pterodactyls can only fly 30 turns straight after leaving a tree
        if (map.locationOf(this).getGround().hasCapability(TARGET_HOME_TYPE)) {
            this.onLand = false;
            this.flyingTurns = 0;
        }
        else {
            if (this.flyingTurns < MAX_FLYING_TURNS) {
                this.flyingTurns++;
            }
            else {
                this.onLand = true;
            }
        }
        System.out.println(this.onLand);

        return action;
    }

}
