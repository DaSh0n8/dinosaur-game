package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing dinosaur type
 */
public abstract class Dinosaur extends Actor {

    private Behaviour behaviour;
    private int unconsciousTurns;

    /**
     * Enum represents gender of the dinosaur.
     */
    private enum Gender {
        MALE, FEMALE
    }

    /**
     * Constructor.
     *
     * @param name        the name of this Dinosaur
     * @param displayChar the character that will represent this type of Dinosaur
     * @param hitPoints   the Actor's starting hit points
     */
    public Dinosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.behaviour = new WanderBehaviour();
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        // if unconscious, count the unconscious length and do nothing
        if (!this.isConscious()) {
            this.incrementUnconsciousTurns();
            return new DoNothingAction();
        }
        else {
            this.hurt(1);
        }

        return new DoNothingAction();
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

    /**
     * Dinosaur will search for a specific location that might have their foods.
     *
     * @return location of nearest food source
     */
    public abstract Location findFoodSource(GameMap map);

}
