package game;

import edu.monash.fit2099.engine.Item;

public abstract class Egg extends Item {
    public Egg(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);
        addCapability(ItemType.EGG);
    }
}
