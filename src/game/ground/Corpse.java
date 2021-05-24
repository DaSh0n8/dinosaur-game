package game.ground;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import game.enumeration.DinosaurSpecies;
import game.enumeration.GroundType;
import game.enumeration.ItemType;
import game.item.PortableItem;

/**
 * Class represent the corpse of Dinosaur.
 */
public class Corpse extends PortableItem {

    private int maxTurns = 0;
    private int turns = 0;

    public Corpse(DinosaurSpecies species) {
        super("Corpse", 'c');
        addCapability(ItemType.CORPSE);
        this.addCapability(species);
        // set exist turns
        if (this.hasCapability(DinosaurSpecies.STEGOSAUR)) {
            this.maxTurns = 20;
        }
        else if (this.hasCapability(DinosaurSpecies.BRACHIOSAUR)) {
            this.maxTurns = 40;
        }
        else if (this.hasCapability(DinosaurSpecies.ALLOSAUR)) {
            this.maxTurns = 20;
        }
        else if (this.hasCapability(DinosaurSpecies.PTERODACTYL)) {
            this.maxTurns = 20;
        }
    }

    /**
     * Increase the exist turn of this corpse until its maximum turns, then remove it.
     *
     * @param currentLocation The location of the Corpse on which Dinosaur die.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        if (this.turns == this.maxTurns) {
            currentLocation.removeItem(this);
        }
        else {
            this.turns++;
        }
    }

}
