package game.ground;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.Sky;
import game.enumeration.GroundType;

public class Lake extends Ground {

    private int sips = 25;
    private int fish = 5;


    public Lake() {
        super('~');
        addCapability(GroundType.LAKE);
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
        if (Sky.rain){
            sips += Sky.rainfall;
        }
        if (Math.random()*100 < 60){
            increaseFish();
        }
    }

    public int getSips(){
        return sips;
    }

    public void increaseSips (int amount){
        sips+=amount;
    }

    public void decreaseSips (int amount){
        sips-=amount;
    }

    public int getFishAmount(){
        return fish;
    }

    public void increaseFish(){
        fish++;
    }

    public void decreaseFish(){
        fish--;
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }
}
