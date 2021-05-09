package game.item;

import game.enumeration.ItemType;

public class CarnivoreMealKit extends MealKit {
    public CarnivoreMealKit() {
        super("Carnivore Meal Kit", 'C', true);
        addCapability(ItemType.CARNIVORE_MEALKIT);
    }
}
