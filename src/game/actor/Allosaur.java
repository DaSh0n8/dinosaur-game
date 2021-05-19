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
    private Behaviour behaviour;

    public Allosaur(String name) {
        super(name, 'a', MAX_HIT_POINTS);
        this.hurt(50);
        this.behaviour = new WanderBehaviour();
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        if(!this.isConscious()){
            this.incrementUnconsciousTurns();
        }
        else{
            this.hurt(1);
        }

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

    @Override
    public Location findMatingPartner(GameMap map) {
        if (!map.contains(this)) {
            return null;
        }

        Location here = map.locationOf(this);
        int topLeftX = map.getXRange().min();
        int topLeftY = map.getYRange().min();
        Location there = map.at(topLeftX, topLeftY);
        int minDistance = distance(here, there);

        NumberRange heights = map.getYRange();
        NumberRange widths = map.getXRange();
        for (int y : heights) {
            for (int x : widths) {
                Location thisLocation = map.at(x, y);
                if (thisLocation.containsAnActor()){
                    if (thisLocation.getActor().hasCapability(DinosaurSpecies.ALLOSAUR)){
                        int thisDistance = distance(here,thisLocation);
                        if (thisDistance < minDistance && thisDistance != 0){
                            minDistance = thisDistance;
                            there = thisLocation;
                        }
                    }
                }
            }
        }
        return there;
    }

    @Override
    public boolean surroundingMatingPartner(Location location, GameMap map) {
        int x = location.x();
        int y = location.y();
        int maxX = map.getXRange().max();
        int maxY = map.getYRange().max();
        int minX = map.getXRange().min();
        int minY = map.getYRange().min();

        if(y-2 >= minY){
            if(map.at(x,y-2).getActor().hasCapability(DinosaurSpecies.ALLOSAUR) || map.at(x,y-2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        if (y-2 >= minY) {
            if(map.at(x,y-2).getActor().hasCapability(DinosaurSpecies.ALLOSAUR) || map.at(x,y-2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on upper right
        if (x+2 <= maxX && y-2 >= minY) {
            if(map.at(x+2,y-2).getActor().hasCapability(DinosaurSpecies.ALLOSAUR) || map.at(x+2,y-2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on right
        if (x+1 <= maxX) {
            if(map.at(x+2,y).getActor().hasCapability(DinosaurSpecies.ALLOSAUR) || map.at(x+2,y).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on lower right
        if (x+2 <= maxX && y+2 <= maxY) {
            if(map.at(x+2,y+2).getActor().hasCapability(DinosaurSpecies.ALLOSAUR) || map.at(x+2,y+2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on below
        if (y+2 <= maxY) {
            if(map.at(x,y+2).getActor().hasCapability(DinosaurSpecies.ALLOSAUR) || map.at(x,y+2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on lower left
        if (x-2 >= minX && y+2 <= maxY) {
            if(map.at(x-2,y+2).getActor().hasCapability(DinosaurSpecies.ALLOSAUR) || map.at(x-2,y+2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on left
        if (x-2 >= minX) {
            if(map.at(x-2,y).getActor().hasCapability(DinosaurSpecies.ALLOSAUR) || map.at(x-2,y).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }
        // check ground on upper left
        if (x-2 >= minX && y-2 >= minY) {
            if(map.at(x-2,y-2).getActor().hasCapability(DinosaurSpecies.ALLOSAUR) || map.at(x-2,y-2).getActor().hasCapability(DinosaurGender.FEMALE) )
                return true;
        }

        return false;
    }


    private static int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}
