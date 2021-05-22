package game;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.GroundFactory;

import java.util.List;

public class AdvancedGameMap extends GameMap {
    public static boolean rain;
    private int turns=0;
    public static double rainfall;
    private double min = 0.1;
    private double max = 0.6;

    public AdvancedGameMap(GroundFactory groundFactory, List<String> lines) {
        super(groundFactory, lines);
    }

    @Override
    public void tick() {
        setRainFalse();
        if (turns > 1 && turns%10 == 0){
            if ((Math.random()*100) < 20){
                System.out.println("It rains!");
                setRainTrue();
                rainfall = (calcRainfall() * 20);
            }else if((Math.random()*100)>20) {
                System.out.println("Did not rain");
                setRainFalse();
            }
        }
        turns++;
        super.tick();
    }

    public double calcRainfall(){
        return (float) ((Math.random()*(max-min))+min);
    }

    public void setRainTrue(){
        rain = true;
    }

    public void setRainFalse(){
        rain = false;
    }

}
