package game.actor;

import edu.monash.fit2099.engine.*;
import game.behaviour.WanderBehaviour;
import game.behaviour.Behaviour;
import game.enumeration.DinosaurGender;

import java.util.Random;

/**
 * Class representing dinosaur type
 */
public abstract class Dinosaur extends Actor {

    private int unconsciousTurns;
    private int thirstLevel;


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
     * Increment the dinosaur's number of unconscious turns.
     */
    public void incrementUnconsciousTurns() {
        this.unconsciousTurns++;
    }

    /**
     * Reset the dinosaur's unconscious turns to 0 because it have regained consciousness.
     */
    public void awake() {
        this.unconsciousTurns = 0;
    }

    public void increaseThirst (int points){
        thirstLevel += points;
    }

    public void decreaseThirst(){
        thirstLevel-=1;
    }

    public int getThirstLevel(){
        return thirstLevel;
    }

    public boolean isThirsty(){
        return thirstLevel>0;
    }

    public int getUnconsciousTurns() {
        return this.unconsciousTurns;
    }

}
