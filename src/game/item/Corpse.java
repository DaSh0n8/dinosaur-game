package game.item;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import game.enumeration.DinosaurDiet;
import game.enumeration.DinosaurSpecies;
import game.enumeration.GroundType;
import game.enumeration.ItemType;
import game.item.PortableItem;

/**
 * Class represent the corpse of Dinosaur.
 */
public class Corpse extends PortableItem {

    private int hitPoints;
    private int maxTurns;
    private int turns = 0;

    public Corpse(DinosaurSpecies species) {
        super("Corpse", 'c');
        addCapability(ItemType.CORPSE);
        addCapability(DinosaurDiet.CARNIVORE);
        this.addCapability(species);
        // set exist turns
        if (this.hasCapability(DinosaurSpecies.STEGOSAUR)) {
            this.hitPoints = 50;
            this.maxTurns = 20;
        }
        else if (this.hasCapability(DinosaurSpecies.BRACHIOSAUR)) {
            this.maxTurns = 40;
            this.hitPoints = 100;
        }
        else if (this.hasCapability(DinosaurSpecies.ALLOSAUR)) {
            this.hitPoints = 50;
            this.maxTurns = 20;
        }
        else if (this.hasCapability(DinosaurSpecies.PTERODACTYL)) {
            this.maxTurns = 20;
            this.hitPoints = 30;
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
        if (this.turns == this.maxTurns || this.hitPoints == 0) {
            currentLocation.removeItem(this);
        }
        else {
            this.turns++;
        }
    }

    /**
     * Dinosaur eats part of this Corpse
     *
     * @param points The amount of this Corpse the Dinosaur wants to eat
     * @return The amount of this Corpse the Dinosaur get
     */
    public int eatenPart(int points) {
        if (this.hitPoints > points) {
            this.hitPoints -= points;
            return points;
        }
        else {
            points = this.hitPoints;
            this.hitPoints = 0;
            return points;
        }
    }

    /**
     * Dinosaur eats all the remaining amount of this Corpse
     *
     * @return The remaining amount of this Corpse
     */
    public int eatenAll() {
        int points = this.hitPoints;
        this.hitPoints = 0;
        return points;
    }

}
