package game;


import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

/**
 * A herbivorous dinosaur.
 */
public class Stegosaur extends Dinosaur {
    // Will need to change this to a collection if Stegosaur gets additional Behaviours.
    private final static int MAX_UNCONSCIOUS_TURNS = 20;
    private Behaviour behaviour;

    /**
     * Constructor.
     * All Stegosaurs are represented by a 'd' and have 100 max hit points but start with 50 hit points.
     *
     * @param name the name of this Stegosaur
     */
    public Stegosaur(String name) {
        super(name, 'd', 100);
        this.hurt(50);
        behaviour = new WanderBehaviour();
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return new Actions(new AttackAction(this));
    }

    /**
     * Figure out what to do next.
     * <p>
     * FIXME: Stegosaur wanders around at random, or if no suitable MoveActions are available, it
     * just stands there.  That's boring.
     *
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        // if unconscious, count the unconscious length and do nothing
        if (!this.isConscious()) {
            this.incrementUnconsciousTurns();
            return new DoNothingAction();
        }
        // decrease food level by 1
        this.hurt(1);

        Action wander = behaviour.getAction(this, map);
        if (wander != null)
            return wander;

        return new DoNothingAction();
    }

}
