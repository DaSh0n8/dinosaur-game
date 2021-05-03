package game;

import edu.monash.fit2099.engine.Actor;

/**
 * Class representing dinosaur type
 */
public abstract class Dinosaur extends Actor {

    private Behaviour behaviour;
    private int MAX_FOOD_LEVEL;
    private int current_food_level;
    private int current_unconscious_length = 0;

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
    public Dinosaur(String name, char displayChar, int hitPoints, int food_level) {
        super(name, displayChar, hitPoints);
        behaviour = new WanderBehaviour();
        current_food_level = food_level;
    }

    /**
     * Decrease the food level by 1.
     */
    public void decrement_food_level() {
        current_food_level -= 1;
    }

}
