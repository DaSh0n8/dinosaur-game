package game;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.GroundFactory;

import java.util.List;
import java.util.Random;

public class AdvancedGameMap extends GameMap {

    private final static int RAINFALL_CHANCE = 20; // 20/100
    private final static int MIN_RAINFALL = 1; // 10/100
    private final static int MAX_RAINFALL = 6; // 60/100
    private static Random random = new Random();
    private boolean isRaining;
    private int waterAmount;
    private int turns=0;

    public AdvancedGameMap(GroundFactory groundFactory, List<String> lines) {
        super(groundFactory, lines);
    }

    /**
     * In addition to the gameMap function, calculate the water amount when it's raining in this gameMap in this turn.
     */
    @Override
    public void tick() {
        if (this.turns > 1 && this.turns%10 == 0){
            int rand = random.nextInt(100) + 1;
            if (rand <= RAINFALL_CHANCE){
                setIsRaining(true);
                calcWaterAmount();
            }else if((Math.random()*100)>20) {
                setIsRaining(false);
            }
        }
        this.turns++;
        super.tick();
    }

    /**
     * The water amount is 2 * rainFall. Water amount is the amount for each lake.
     */
    public void calcWaterAmount(){
        int rainFall = random.nextInt(MAX_RAINFALL) + 1;
        this.waterAmount = 2 * rainFall;
    }

    public int getWaterAmount() {
        return this.waterAmount;
    }

    public void setIsRaining(boolean isRaining){
        this.isRaining = isRaining;
    }

    public boolean getIsRaining() {
        return this.isRaining;
    }

}
