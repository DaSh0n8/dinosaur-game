package game;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.GroundFactory;

public class Sky extends GameMap {
    public static boolean rain;
    private int i=0;
    public static double rainfall;
    private double min = 0.1;
    private double max = 0.6;


    public Sky(GroundFactory groundFactory, char groundChar, int width, int height) {
        super(groundFactory, groundChar, width, height);
    }

    @Override
    public void tick() {
        super.tick();
        while (i%10==0){
            if(Math.random()*100 < 20){
                rain = true;
                rainfall = (calcRainfall() *20);
            }else{
                rain = false;
            }
        }
        i++;
    }

    public double calcRainfall(){
        return (float) ((Math.random()*(max-min))+min);
    }
}
