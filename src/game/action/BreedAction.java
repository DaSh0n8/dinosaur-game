package game.action;

import edu.monash.fit2099.engine.*;
import game.enumeration.DinosaurGender;
import game.enumeration.Status;

/**
 * A class that will makes male Dinosaur to impregnate another female Dinosaur, or makes female Dinosaur to wait for
 * another male Dinosaur
 */
public class BreedAction extends Action {

    private Actor target;

    public BreedAction(Actor target) {
        this.target = target;
    }

    /**
     * If this Female Dinosaur want to mate, it will wait for the valid Male Dinosaur next to it to impregnate. If this
     * is a Male Dinosaur, it will impregnate a valid Female Dinosaur next to it.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (actor.hasCapability(DinosaurGender.FEMALE) && this.target != null) {
            return actor + " waits for mating";
        }
        else if (actor.hasCapability(DinosaurGender.MALE) && this.target != null) {
            this.target.addCapability(Status.PREGNANT);
            return actor + " impregnates";
        }

        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " breeds";
    }


}
