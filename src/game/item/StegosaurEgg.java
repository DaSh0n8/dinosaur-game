package game.item;

import game.enumeration.ItemType;
import game.item.Egg;

public class StegosaurEgg extends Egg {
    public StegosaurEgg() {
        super("Stegosaur Egg", 's', true);
        addCapability(ItemType.STEGOSAUR_EGG);
    }
}
