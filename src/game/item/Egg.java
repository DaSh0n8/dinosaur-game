package game.item;

import edu.monash.fit2099.engine.*;
import game.actor.Allosaur;
import game.actor.Brachiosaur;
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
            currentLocation.removeItem(this);
            if (this.hasCapability(DinosaurSpecies.STEGOSAUR)) {
                currentLocation.addActor(new Stegosaur("Stegosaur"));
            }
            else if (this.hasCapability(DinosaurSpecies.BRACHIOSAUR)) {
                currentLocation.addActor(new Brachiosaur("Brachiosaur"));
            }
            else if (this.hasCapability(DinosaurSpecies.ALLOSAUR)) {
                currentLocation.addActor(new Allosaur("Allosaur"));
            }
        }

        // if this Egg is on the ground, increment its developing turns
        if (this.developing_turns < HATCH_TURNS) {
            if (currentLocation.getItems().contains(this)) {
                this.developing_turns++;
            }
        }

    }
}
