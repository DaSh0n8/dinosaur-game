package game.actor;

import edu.monash.fit2099.engine.*;
import game.action.ComaAction;
import game.action.DeadAction;
import game.action.LayEggAction;
import game.behaviour.Behaviour;
import game.enumeration.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing Dinosaur type
 */
public abstract class Dinosaur extends Actor {

    private int waterLevel;
    private int satisfyHitPoints;
    private int hungryHitPoints;
    private int thirstyWaterLevel;
    private int maxUnconsciousTurns;
    private int maxPregnantTurns;
    private int maxWaterLevel;
    private int maxBabyTurns;
    private int unconsciousTurns = 0;
    private int pregnantTurns = 0;
    private int babyTurns = 0;
    private List<Behaviour> actionFactories = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param name        the name of this Dinosaur
     * @param displayChar the character that will represent this type of Dinosaur
     * @param hitPoints   the Actor's starting hit points
     */
    public Dinosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
    }

    /**
     * First, checks the consciousness of this Dinosaur. If unconscious, return DeadAction. Second, checks the pregnancy
     * of the Dinosaur. If pregnancy is mature, return LayEggAction. Third, adjust the thirsty level status and hungry
     * level status, print if the Dinosaur is thirsty and/or hungry. Lastly, iterate through the Behaviour this Dinosaur
     * has, and return the not null Action.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return Action the Dinosaur is taking in this turns.
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        // if unconscious, count the unconscious length and do nothing
        if (!this.isConscious() || this.isDehydrated()) {
            this.unconsciousTurns++;
            // if reached max unconscious turns, dinosaur dies
            if (this.unconsciousTurns == maxUnconsciousTurns) {
                return new DeadAction();
            } else {
                return new ComaAction();
            }
        } else {
            // reset unconscious turns
            if (this.unconsciousTurns > 0) {
                this.unconsciousTurns = 0;
            }
            this.hurt(1);
            this.waterLevel--;
        }

        // if baby Dinosaur became adult, make it grown up
        if (this.hasCapability(Status.BABY)) {
            if (this.babyTurns == maxBabyTurns) {
                this.grownUp();
            }
            else {
                this.babyTurns++;
            }
        }

        // if pregnancy is mature, lay an egg
        if (this.hasCapability(Status.PREGNANT)) {
            if (this.pregnantTurns == this.maxPregnantTurns) {
                this.pregnantTurns = 0;
                this.removeCapability(Status.PREGNANT);
                return new LayEggAction();
            }
            else {
                this.pregnantTurns++;
            }
        }

        // if Dinosaur is thirsty, print message and adjust thirsty level status
        if (this.waterLevel < this.thirstyWaterLevel) {
            Location location = map.locationOf(this);
            int x = location.x();
            int y = location.y();
            System.out.println(this.name + " at (" + x + ", " + y + ") is getting thirsty!");
            this.addCapability(Status.THIRSTY);
        }else if (this.waterLevel > this.thirstyWaterLevel){
            this.removeCapability(Status.THIRSTY);
        }
        // if Dinosaur is hungry, print message
        if (this.hitPoints < this.satisfyHitPoints) {
            Location location = map.locationOf(this);
            int x = location.x();
            int y = location.y();
            System.out.println(this.name + " at (" + x + ", " + y + ") is getting hungry!");
        }

        // adjust Dinosaur hungry level status
        if (this.hitPoints >= this.satisfyHitPoints) {
            if (!this.hasCapability(Status.SATISFY)) {
                this.addCapability(Status.SATISFY);
                this.removeCapability(Status.HUNGRY);
                this.removeCapability(Status.STARVE);
            }
        }
        else if (this.hitPoints >= this.hungryHitPoints) {
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
     * Set the initial states of the Dinosaur.
     */
    public abstract void initialization();

    /**
     * Add the behaviour this Dinosaur has. The order of the Behaviour added is important as the Dinosaur will consider
     * the first added Behaviour first.
     *
     * @param newBehaviour Behaviour this Dinosaur has
     */
    public void addBehaviour(Behaviour newBehaviour) {
        this.actionFactories.add(newBehaviour);
    }

    /**
     * Decide the gender of Dinosaur.
     */
    public abstract void decideGender();

    /**
     * Modify baby Dinosaur to become and adult by changing its status and displayChar
     */
    public abstract void grownUp();

    public void setSatisfyHitPoints(int points) {
        this.satisfyHitPoints = points;
    }

    public void setHungryHitPoints(int points) {
        this.hungryHitPoints = points;
    }

    public void setThirstyWaterLevel(int points) {
        this.thirstyWaterLevel = points;
    }

    public void setMaxUnconsciousTurns(int turns) {
        this.maxUnconsciousTurns = turns;
    }

    public void setMaxPregnantTurns(int turns) {
        this.maxPregnantTurns = turns;
    }

    public void setMaxWaterLevel(int waterLevel) {
        this.maxWaterLevel = waterLevel;
    }

    public void setMaxBabyTurns(int turns) {
        this.maxBabyTurns = turns;
    }

    /**
     * Set the Dinosaur starting waterLevel
     *
     * @param points Starting waterLevel
     */
    public void setWaterLevel(int points){
        this.waterLevel = points;
    }

    public void increaseWaterLevel(int points){
        this.waterLevel += points;
        this.waterLevel = Math.min(this.waterLevel, this.maxWaterLevel);
    }

    public void decreaseWaterLevel(){
        this.waterLevel -=1;
    }

    public boolean isDehydrated(){
        return this.waterLevel == 0;
    }

    /**
     * Restores dinosaur hitpoints and water level to maximum.
     */
    public void fullRestoration() {
        this.hitPoints = maxHitPoints;
        this.waterLevel = maxWaterLevel;
    }

}
