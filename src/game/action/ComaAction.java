package game.action;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.DoNothingAction;

/**
 * An Action that doesn't do anything just like DoNothingAction, but tells that the actor is unconscious.
 */
public class ComaAction extends DoNothingAction {

    public ComaAction() {}

    @Override
    public String menuDescription(Actor actor) {
        return actor + " is unconscious";
    }
}
