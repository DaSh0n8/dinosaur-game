package game;

import edu.monash.fit2099.engine.Actor;

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

    private enum Status{
        ALIVE,DEAD
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
        behaviour = new WanderBehaviour();
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

    public int getUnconsciousTurns() {
        return unconsciousTurns;
    }
}
