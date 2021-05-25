package game.item;

import edu.monash.fit2099.engine.*;
import game.EcoPoints;
import game.actor.Allosaur;
import game.actor.Brachiosaur;
import game.actor.Pterodactyl;
import game.actor.Stegosaur;
import game.enumeration.DinosaurSpecies;
import game.enumeration.ItemType;

public class Egg extends PortableItem {

    private final static int HATCH_TURNS = 20;
    private int developing_turns = 0;

    public Egg(DinosaurSpecies dinosaurSpecies) {
        super("Egg", 'e');
        addCapability(ItemType.EGG);
        this.addCapability(dinosaurSpecies);
    }

    public void tick(Location currentLocation) {
        // egg hatches and produce a Dinosaur if situation allows
        if (this.developing_turns == HATCH_TURNS && !currentLocation.containsAnActor()) {
            if (this.hasCapability(DinosaurSpecies.STEGOSAUR)) {
                currentLocation.addActor(new Stegosaur("Stegosaur"));
                EcoPoints.increase_points(100);
            }
            else if (this.hasCapability(DinosaurSpecies.BRACHIOSAUR)) {
                currentLocation.addActor(new Brachiosaur("Brachiosaur"));
                EcoPoints.increase_points(1000);
            }
            else if (this.hasCapability(DinosaurSpecies.ALLOSAUR)) {
                EcoPoints.increase_points(1000);
                currentLocation.addActor(new Allosaur("Allosaur"));
            }
            else if (this.hasCapability(DinosaurSpecies.PTERODACTYL)) {
                EcoPoints.increase_points(1000);
                currentLocation.addActor(new Pterodactyl("Pterodactyl"));
            }
            currentLocation.removeItem(this);
        }

        // if this Egg is on the ground, increment its developing turns
        if (this.developing_turns < HATCH_TURNS) {
            if (currentLocation.getItems().contains(this)) {
                this.developing_turns++;
            }
        }

    }
}
