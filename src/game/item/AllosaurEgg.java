package game.item;

import game.enumeration.ItemType;

public class AllosaurEgg extends Egg {
    public AllosaurEgg() {
        super("Allosaur Egg", 'a', true);
        addCapability(ItemType.ALLOSAUR_EGG);
    }
}
