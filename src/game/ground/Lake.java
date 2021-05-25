package game.ground;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.AdvancedGameMap;
import game.enumeration.DinosaurSpecies;
import game.enumeration.GroundType;

import java.util.Random;

/**
 * Class represents the water square.
 */
public class Lake extends Ground {

    private Random random = new Random();
    private final static int MAX_SIPS = 25;
    private final static int MAX_FISH_AMOUNT = 25;
    private final static int BORN_FISH_CHANCE = 60;
    private int sips = 5;
    private int fishAmount = 5;


    public Lake() {
        super('~');
        addCapability(GroundType.LAKE);
    }

    /**
     * Lake has 60% chance to born a fish in every turn, and will increase the water amount when it's raining.
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        try {
            AdvancedGameMap map = (AdvancedGameMap) location.map();
            if (map.getIsRaining()) {
                increaseSips(map.getWaterAmount());
            }
            int rand = random.nextInt();
            if (rand <= BORN_FISH_CHANCE) {
                increaseFishAmount();
            }
        }
        catch (ClassCastException e) {
            System.out.println("Error when using AdvancedGameMap class");
        }
    }

    public int getSips(){
        return this.sips;
    }

    public void increaseSips (int amount){
        this.sips += amount;
        this.sips = Math.min(this.sips, MAX_SIPS);
    }

    public void decreaseSips (){
        this.sips--;
    }

    public int getFishAmount(){
        return this.fishAmount;
    }

    private void increaseFishAmount(){
        this.fishAmount++;
        this.fishAmount = Math.min(this.fishAmount, MAX_FISH_AMOUNT);
    }

    public void decreaseFishAmount(){
        this.fishAmount--;
    }

    /**
     * Only Pterodactyl can enter lake.
     *
     * @param actor the Actor to check
     * @return false except for Pterodactyl
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        if (actor.hasCapability(DinosaurSpecies.PTERODACTYL)) {
            return true;
        }
        return false;
    }
}
