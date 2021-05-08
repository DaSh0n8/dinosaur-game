package game;

import edu.monash.fit2099.engine.Item;

public class Fruit extends Item{

    private int rotTurns;
    public Fruit() {
        super("fruit", 'f', true);
    }

    public void increaseRotTurn(){
        rotTurns++;
    }

    public int checkRotTurns(){
        return rotTurns;
    }

    public void setRotTurns(int rotTurns) {
        this.rotTurns = rotTurns;
    }
}
