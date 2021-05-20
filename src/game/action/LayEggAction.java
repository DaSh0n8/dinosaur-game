package game.action;

import edu.monash.fit2099.engine.*;
import game.enumeration.DinosaurSpecies;
import game.item.Egg;

/**
 * A class that makes actor to produce its Egg at its current Location.
 */
public class LayEggAction extends Action {

    @Override
    public String execute(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);
        if (actor.hasCapability(DinosaurSpecies.STEGOSAUR)) {
            here.addItem(new Egg(DinosaurSpecies.STEGOSAUR));
        }
        else if (actor.hasCapability(DinosaurSpecies.BRACHIOSAUR)) {
            here.addItem(new Egg(DinosaurSpecies.BRACHIOSAUR));
        }
        else if (actor.hasCapability(DinosaurSpecies.ALLOSAUR)) {
            here.addItem(new Egg(DinosaurSpecies.ALLOSAUR));
        }

        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " lays an egg";
    }

}
