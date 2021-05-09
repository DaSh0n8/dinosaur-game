package game.item;

import game.enumeration.ItemType;
import game.item.MealKit;

public class VegetarianMealKit extends MealKit {
    public VegetarianMealKit() {
        super("Vegetarian Meal Kit", 'V', true);
        addCapability(ItemType.VEGETARIAN_MEALKIT);
    }
}
