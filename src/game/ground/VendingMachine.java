package game.ground;

import edu.monash.fit2099.engine.*;

import java.util.Scanner;

import game.EcoPoints;
import game.action.BuyAction;
import game.enumeration.DinosaurSpecies;
import game.enumeration.GroundType;
import game.item.*;

public class VendingMachine extends Ground {

    private static int itemPrice;

    public VendingMachine() {
        super('V');
        addCapability(GroundType.VENDINGMACHINE);
    }

    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions actions = super.allowableActions(actor, location, direction);
        actions.add(new BuyAction());
        return actions;

    }

    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

}
