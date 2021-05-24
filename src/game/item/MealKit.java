package game.item;

import edu.monash.fit2099.engine.Item;
import game.enumeration.DinosaurDiet;
import game.enumeration.ItemType;

public class MealKit extends PortableItem {

    public MealKit(DinosaurDiet dinosaurDiet) {
        super("Meal Kit", 'm');
        addCapability(ItemType.MEALKIT);
        this.addCapability(dinosaurDiet);
    }

}



















































