package game.behaviour;

import edu.monash.fit2099.engine.*;
import game.action.DrinkAction;
import game.actor.Dinosaur;
import game.enumeration.GroundType;
import game.enumeration.Status;
import game.ground.Lake;

public class ThirstyBehaviour implements Behaviour {

    private final static String name = "TRAVEL";
    private Location waterSource = null;
    private GroundType waterSourceType;

    public ThirstyBehaviour(GroundType waterSourceType) {
        this.waterSourceType = waterSourceType;
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (!map.contains(actor) || !actor.hasCapability(Status.THIRSTY)) {
            System.out.println("drinking behaviour ignored");
            return null;
        }

        Location here = map.locationOf(actor);

        // first, if the current place is a valid water source, continue drinking
        if(here.getGround().hasCapability(GroundType.LAKE)){
            // if this lake has water, return drink action
            try{
                Lake lake = (Lake) here.getGround();
                if (lake.getSips() > 0){
                    System.out.println("Dinosaur is drinking");
                    return new DrinkAction();
                }
                else{
                    this.waterSource = null;
                }
            }catch (ClassCastException e){
                System.out.println("Invalid ground");
            }

        }
        else{
            findWaterSource(actor,map);
        }

        // find a water source if the current Lake doesn't have one
        if (this.waterSource == null){
            this.waterSource = findWaterSource(actor,map);
        }

        // travel to the water source
        int currentDistance = distance(here, this.waterSource);
        for (Exit exit : here.getExits()) {
            Location newDestination = exit.getDestination();
            if (newDestination.canActorEnter(actor)) {
                int newDistance = distance(newDestination, this.waterSource);
                if (newDistance < currentDistance) {
                    return new MoveActorAction(newDestination, exit.getName());
                }
            }
        }

        return null;
    }


    public Location findWaterSource(Actor actor, GameMap map){
        if (!map.contains(actor)){
            return null;
        }
        Location here = map.locationOf(actor);

        // initialize target place to be the top left corner of the map
        int topLeftX = map.getXRange().min();
        int topLeftY = map.getYRange().min();
        Location there = map.at(topLeftX, topLeftY);
        int minDistance = distance(here, there);
        // find a nearest Lake
        NumberRange heights = map.getYRange();
        NumberRange widths = map.getXRange();
        for (int y : heights) {
            for (int x : widths) {
                Location thisLocation = map.at(x, y);
                Ground thisGround = thisLocation.getGround();
                if (thisGround.hasCapability(GroundType.LAKE)) {
                    int thisDistance = distance(here, thisLocation);
                    if (thisDistance < minDistance && thisDistance != 0) {
                        minDistance = thisDistance;
                        there = thisLocation;
                    }
                }
            }
        }
        return there;
    }

    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    public String getName(){
        return name;
    }
}
