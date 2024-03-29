package game.item;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;
import game.enumeration.DinosaurDiet;
import game.enumeration.ItemType;

public class Fruit extends PortableItem {

    private int rotTurns = 0;
    private int MAX_ROT_TURNS = 15;

    public Fruit() {
        super("fruit", 'f');
        addCapability(ItemType.FRUIT);
        addCapability(DinosaurDiet.HERBIVORE);
    }

    /**
     * Increase the rot turns of this Fruit if the situation is valid. If it is fully rotted, remove from the game.
     *
     * @param currentLocation The location of the actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation) {
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
