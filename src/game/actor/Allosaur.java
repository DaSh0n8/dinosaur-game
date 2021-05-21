package game.actor;

import edu.monash.fit2099.engine.*;
import game.behaviour.Behaviour;
import game.behaviour.WanderBehaviour;
import game.enumeration.DinosaurGender;
import game.enumeration.DinosaurSpecies;
import game.enumeration.GroundType;

public class Allosaur extends Dinosaur {
    private final static int MAX_HIT_POINTS = 100;
    private final static int SATISFIED_HIT_POINTS = 90;
    private final static int HUNGRY_HIT_POINTS = 50;
    private final static int MAX_UNCONSCIOUS_TURNS = 20;
    private static int totalMale = 0;
    private static int totalFemale = 0;
    private DinosaurGender oppositeGender;
    private int unconsciousTurns = 0;
    private int pregnantTurns = 0;
    private Behaviour behaviour;

    public Allosaur(String name) {
        super(name, 'a', MAX_HIT_POINTS);
        this.hurt(50);
        this.behaviour = new WanderBehaviour();
    }

    /**
     * Try to maintain a same number of male and female Stegosaur. If the total are the same, produce a female Stegosaur.
     *
     */
    @Override
    public void decideGender() {
        if (totalMale < totalFemale) {
            totalMale++;
            this.addCapability(DinosaurGender.MALE);
            this.oppositeGender = DinosaurGender.FEMALE;
        }
        else {
            totalFemale++;
            this.addCapability(DinosaurGender.FEMALE);
            this.oppositeGender = DinosaurGender.MALE;
        }
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return null;
    }

//    @Override
//    public Location findFoodSource(GameMap map) {
//        if (!map.contains(this)){
//            return null;
//        }
//
//        Location here = map.locationOf(this);
//        int topLeftX = map.getXRange().min();
//        int topLeftY = map.getYRange().min();
//        Location there = map.at(topLeftX,topLeftY);
//        int minDistance = distance(here,there);
//        // find a nearest corpse
//        NumberRange heights = map.getYRange();
//        NumberRange widths = map.getXRange();
//        for(int y : heights){
//            for(int x : widths){
//                Location location = new Location(map,x,y);
//                Location thisLocation = map.at(x,y);
//                Ground thisGround = thisLocation.getGround();
//                if(thisGround.hasCapability(GroundType.CORPSE) ){
//                    int thisDistance = distance(here,thisLocation);
//                    if (thisDistance<minDistance && thisDistance != 0){
//                        minDistance = thisDistance;
//                        there = thisLocation;
//                    }
//                }else if (location.containsAnActor()){
//                    Actor thisActor = location.getActor();
//                    if(thisActor.hasCapability(DinosaurSpecies.STEGOSAUR)){
//                        int thisDistance = distance(here,thisLocation);
//                        if(thisDistance<minDistance && thisDistance != 0){
//                            minDistance = thisDistance;
//                            there = thisLocation;
//                        }
//                    }
//                }
//            }
//        }
//        return there;
//    }

    private static int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

}
