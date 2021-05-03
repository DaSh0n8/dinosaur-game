package game;

import edu.monash.fit2099.engine.Actor;

/**
 * Class representing dinosaur type
 */
public abstract class Dinosaur extends Actor {

    private Behaviour behaviour;

    /**
     * Constructor.
     *
     * @param name the name of this Dinosaur
     * @param displayChar the character that will represent this type of Dinosaur
     * @param hitPoints the Actor's starting hit points
     */
    public Dinosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        behaviour = new WanderBehaviour();
    }

}
