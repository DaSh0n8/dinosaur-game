package game.action;

import edu.monash.fit2099.engine.*;
import game.enumeration.GroundType;
import game.ground.FruitPlant;

public class PluckAction extends Action {

    private Location target;

    public PluckAction(Location target) {
        this.target = target;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        Ground ground = target.getGround();
        if(ground.hasCapability(GroundType.TREE) || ground.hasCapability(GroundType.BUSH)){
            FruitPlant plant = (FruitPlant) ground;
            Item item = plant.searchPlant();
            if (item!=null){
                actor.addItemToInventory(item);
            }
        }
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " attempts to picks up fruit";
    }
}
