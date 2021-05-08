package game;


import edu.monash.fit2099.engine.*;

/**
 * A herbivorous dinosaur.
 */
public class Stegosaur extends Dinosaur {
    // Will need to change this to a collection if Stegosaur gets additional Behaviours.
    private final static int MAX_HIT_POINTS = 100;
    private final static int SATISFIED_HIT_POINTS = 90;
    private final static int HUNGRY_HIT_POINTS = 50;
    private final static int MAX_UNCONSCIOUS_TURNS = 20;
    private Behaviour behaviour;

    /**
     * Constructor.
     * All Stegosaurs are represented by a 'd' and have 100 max hit points but start with 50 hit points.
     *
     * @param name the name of this Stegosaur
     */
    public Stegosaur(String name) {
        super(name, 'd', MAX_HIT_POINTS);
        this.hurt(50);
        this.behaviour = new WanderBehaviour();
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return new Actions(new AttackAction(this));
    }

    /**
     * Figure out what to do next.
     * <p>
     * FIXME: Stegosaur wanders around at random, or if no suitable MoveActions are available, it
     * just stands there.  That's boring.
     *
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        // if Stegasaur is hungry, print message
        if (this.hitPoints < SATISFIED_HIT_POINTS) {
            Location location = map.locationOf(this);
            int x = location.x();
            int y = location.y();
            System.out.println("Stegosaur at (" + x + ", " + y + ") is getting hungry!");
        }

        // if unconscious, count the unconscious length and do nothing
        if (!this.isConscious()) {
            this.incrementUnconsciousTurns();
            if (getUnconsciousTurns() == MAX_UNCONSCIOUS_TURNS) {
                // Dinosaur disappear
            }
            else {
                return new DoNothingAction();
            }
        }
        else {
            this.hurt(1);
        }

        Action thisAction = null;

        String thisBehaviourName = this.behaviour.getName();
        if (this.hitPoints >= SATISFIED_HIT_POINTS) {
            if (!thisBehaviourName.equals("WANDER")) {
                this.behaviour = new WanderBehaviour();
            }
            thisAction = this.behaviour.getAction(this, map);
        }
        else if (this.hitPoints >= HUNGRY_HIT_POINTS) {
            if (!thisBehaviourName.equals("TRAVEL")) {
                Location destination = findFoodSource(map);
                this.behaviour = new TravelBehaviour(destination);
            }
            else if (this.behaviour.getAction(this, map) == null){
                Location destination = findFoodSource(map);
                this.behaviour = new TravelBehaviour(destination);
            }
            thisAction = this.behaviour.getAction(this, map);
        }
        else {
            if (!thisBehaviourName.equals("TRAVEL")) {
                Location destination = findFoodSource(map);
                this.behaviour = new TravelBehaviour(destination);
            }
            else if (this.behaviour.getAction(this, map) == null){
                Location destination = findFoodSource(map);
                this.behaviour = new TravelBehaviour(destination);
            }
            thisAction = this.behaviour.getAction(this, map);
        }

        // if behaviour doesn't return action, try wander behaviour
        if (thisAction != null)
            return thisAction;

        this.behaviour = new WanderBehaviour();
        thisAction = this.behaviour.getAction(this, map);

        // if wander behaviour also doesn't return action, return DoNothingAction()
        if (thisAction != null)
            return thisAction;

        return new DoNothingAction();
    }

    /**
     * Find a location of a nearest Bush or Tree for Stegasaur.
     * Stegasaur current Location is not considered.
     *
     * @return location of a FruitPlant
     */
    @Override
    public Location findFoodSource(GameMap map) {
        if (!map.contains(this)) {
            return null;
        }

        Location here = map.locationOf(this);
        // initialize target place to be the top left corner of the map
        int topLeftX = map.getXRange().min();
        int topLeftY = map.getYRange().min();
        Location there = map.at(topLeftX, topLeftY);
        int minDistance = distance(here, there);
        // find a nearest FruitPlant
        NumberRange heights = map.getYRange();
        NumberRange widths = map.getXRange();
        for (int y : heights) {
            for (int x : widths) {
                Location thisLocation = map.at(x, y);
                Ground thisGround = thisLocation.getGround();
                if (thisGround.hasCapability(GroundType.FRUITPLANT)) {
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

    /**
     * Compute the Manhattan distance between two locations.
     *
     * @param a the first location
     * @param b the second location
     * @return the number of steps between a and b if you only move in the four cardinal directions.
     */
    private static int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    public void Death(){
        int uncturns = getUnconsciousTurns();
        if (MAX_UNCONSCIOUS_TURNS == uncturns){

        }
    }

}
