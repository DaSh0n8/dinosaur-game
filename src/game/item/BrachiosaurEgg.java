package game.item;

import game.enumeration.ItemType;

public class BrachiosaurEgg extends Egg {
    public BrachiosaurEgg() {
        super("Brachiosaur Egg", 'b', true);
        addCapability(ItemType.BRACHIOSAUR_EGG);
    }
}
