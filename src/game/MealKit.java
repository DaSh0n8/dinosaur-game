package game;

import edu.monash.fit2099.engine.Item;

public abstract class MealKit extends Item {
    public MealKit(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);
        addCapability(ItemType.MEALKIT);
    }
}



















































