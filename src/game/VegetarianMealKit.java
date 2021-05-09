package game;

public class VegetarianMealKit extends MealKit{
    public VegetarianMealKit() {
        super("Vegetarian Meal Kit", 'V', true);
        addCapability(ItemType.VEGETARIAN_MEALKIT);
    }
}
