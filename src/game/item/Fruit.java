package game.item;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;
import game.enumeration.ItemType;

public class Fruit extends PortableItem {

    private int rotTurns = 0;
    private int MAX_ROT_TURNS = 15;

    public Fruit() {
        super("fruit", 'f');
        addCapability(ItemType.FRUIT);
    }

    /**
     * Increase the rot turns of this Fruit if the situation is valid. If it is fully rotted, remove from the game.
     *
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor           The actor carrying this Item.
     */
    public void tick(Location currentLocation, Actor actor) {
        // increase rotTurns if it is on the ground
        if (currentLocation.getItems().contains(this)) {
            this.rotTurns++;
        }

        // remove this fruit if it have rotted for max rot turns
        if (this.rotTurns == MAX_ROT_TURNS) {
            currentLocation.removeItem(this);
        }

    }

}
