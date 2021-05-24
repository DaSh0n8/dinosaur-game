package game.ground;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.AdvancedGameMap;
import game.enumeration.DinosaurSpecies;
import game.enumeration.GroundType;

public class Lake extends Ground {

    private final static int maxSips = 25;
    private final static int maxFishAmount = 25;
    private int sips = 25;
    private int fishAmount = 5;


    public Lake() {
        super('~');
        addCapability(GroundType.LAKE);
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
        if (AdvancedGameMap.rain){
            this.sips += AdvancedGameMap.rainfall;
        }
        if (Math.random()*100 < 60){
            increaseFishAmount();
        }
    }

    public int getSips(){
        return this.sips;
    }

    public void increaseSips (int amount){
        this.sips += amount;
        this.sips = Math.min(this.sips, maxSips);
    }

    public void decreaseSips (){
        sips--;
    }

    public int getFishAmount(){
        return this.fishAmount;
    }

    public void increaseFishAmount(){
        this.fishAmount++;
        this.fishAmount = Math.min(this.fishAmount, maxFishAmount);
    }

    public void decreaseFishAmount(){
        this.fishAmount--;
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        if (actor.hasCapability(DinosaurSpecies.PTERODACTYL)) {
            return true;
        }
        return false;
    }
}
