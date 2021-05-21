package game.action;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.enumeration.DinosaurSpecies;
import game.ground.Corpse;
import game.item.Egg;

public class DeadAction extends Action {

    public DeadAction() {}

    @Override
    public String execute(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);
        if (actor.hasCapability(DinosaurSpecies.STEGOSAUR)) {
            here.setGround(new Corpse(DinosaurSpecies.STEGOSAUR));
        }
        else if (actor.hasCapability(DinosaurSpecies.BRACHIOSAUR)) {
            here.setGround(new Corpse(DinosaurSpecies.BRACHIOSAUR));
        }
        else if (actor.hasCapability(DinosaurSpecies.ALLOSAUR)) {
            here.setGround(new Corpse(DinosaurSpecies.ALLOSAUR));
        }
        map.removeActor(actor);

        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " dies";
    }
}
