package game.ground;

import edu.monash.fit2099.engine.Ground;

public class Lake extends Ground {

    private int sips = 25;

    public Lake() {
        super('~');
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
}
