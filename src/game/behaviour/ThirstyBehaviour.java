package game.behaviour;

import edu.monash.fit2099.engine.*;
import game.action.DrinkAction;
import game.actor.Dinosaur;
import game.enumeration.GroundType;
import game.enumeration.Status;
import game.ground.Lake;

/**
 * A class that will makes the actor drinks the adjacent water or moves the actor one step closer to its water source.
 */
public class ThirstyBehaviour implements Behaviour {

    private final static String name = "TRAVEL";
    private Location waterSource = null;
    private GroundType waterSourceType;

    public ThirstyBehaviour(GroundType waterSourceType) {
        this.waterSourceType = waterSourceType;
    }

    /**
     * If this Dinosaur is next to a valid water source, drink the water. Otherwise, this thirsty Dinosaur will moves
     * one step closer to its target water source.
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return DrinkAction if drinking is valid
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (!map.contains(actor) || !actor.hasCapability(Status.THIRSTY)) {
            return null;
        }

        Location here = map.locationOf(actor);

        Location target = this.adjacentLake(here,GroundType.LAKE);


        // first, if the adjacent place is a valid water source, continue drinking
        if(target!=null){
            // if this lake has water, return drink action
            try{
                Lake lake = (Lake) target.getGround();
                if (lake.getSips() > 0){
                    return new DrinkAction(target);
                }
                else{
                    this.waterSource = null;
                }
            }catch (ClassCastException e){
                System.out.println("Invalid ground");
            }
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

    /**
     * Checks adjacent ground and return the location of the adjacent ground that allows drinking
     *
     * @param location location of this Dinosaur
     * @param targetGroundType GroundType for Dinosaur to drink
     * @return location of the valid ground for drinking
     */
    private Location adjacentLake (Location location, GroundType targetGroundType) {
        // checks adjacent ground
        for (Exit thisExit : location.getExits()) {
            if (thisExit.getDestination().getGround().hasCapability(targetGroundType)) {
                return thisExit.getDestination();
            }
        }

        return null;
    }
}
