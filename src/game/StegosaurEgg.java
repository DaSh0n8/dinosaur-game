package game;

public class StegosaurEgg extends Egg{
    public StegosaurEgg() {
        super("Stegosaur Egg", 's', true);
        addCapability(ItemType.STEGOSAUR_EGG);
    }
}
