package game.action;

import edu.monash.fit2099.engine.*;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.actor.Dinosaur;
import game.enumeration.DinosaurSpecies;
import game.enumeration.GroundType;
import game.ground.Lake;

public class DrinkAction extends Action {

    private Location target;

    public DrinkAction(Location target) {
        this.target= target;
    }

    @Override
    public String execute(Actor actor, GameMap map) {

        Ground ground = target.getGround();
        if(ground.hasCapability(GroundType.LAKE)){
            Lake lake = (Lake) ground;
            Dinosaur dinosaur = (Dinosaur) actor;
            if(lake.getSips()>0){
                try {
                    if (actor.hasCapability(DinosaurSpecies.STEGOSAUR) || actor.hasCapability(DinosaurSpecies.ALLOSAUR)) {
                        dinosaur.increaseThirst(30);
                    } else if (actor.hasCapability(DinosaurSpecies.BRACHIOSAUR)) {
                        dinosaur.increaseThirst(80);
                    }
                }
                catch (ClassCastException e){
                    System.out.println("Invalid ground");
                }
            }
        }
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " drinks " + "water";
    }
}
