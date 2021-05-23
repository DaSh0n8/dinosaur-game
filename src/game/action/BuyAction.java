package game.action;

import edu.monash.fit2099.engine.*;
import game.ground.VendingMachine;

public class BuyAction extends Action {
    public  BuyAction() {

    }

    @Override
    public String execute(Actor actor, GameMap map) {

        Item item = VendingMachine.itemMenu();
        if(item!=null){
            actor.addItemToInventory(item);
        }
        else{
            return null;
        }

        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " accesses vending machine";
    }
}
