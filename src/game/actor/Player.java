package game.actor;

import edu.monash.fit2099.engine.*;
import game.EcoPoints;
import game.action.PluckAction;
import game.action.QuitGameAction;
import game.item.Fruit;
import game.enumeration.GroundType;
import game.ground.VendingMachine;
import game.enumeration.ItemType;

/**
 * Class representing the Player.
 */
public class Player extends Actor {

	private Menu menu = new Menu();
	private double rand = Math.random();
	private int maxTurns = 0;
	private int turns = 0;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}

	/**
	 * Constructor, mainly for challenge mode to set the maximum moves Player has.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints, int maxTurns) {
		super(name, displayChar, hitPoints);
		this.maxTurns = maxTurns;
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		Location here = map.locationOf(this);

		// if player has maximum moves, checks if the ecoPoints is enough within the maximum moves
		if (!reachMaxTurns() && EcoPoints.isEnough()) {
			System.out.println(this.name + " won!");
			return new QuitGameAction();
		} else if (reachMaxTurns()) {
			System.out.println(this.name + " lose!");
			return new QuitGameAction();
		} else {
			this.turns++;
		}

		// player can pick fruit from bush and tree
		if(here.getGround().hasCapability(GroundType.FRUITPLANT)){
			actions.add(new PluckAction());
		}

		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		actions.add(new QuitGameAction());
		return menu.showMenu(this, actions, display);
	}

	/**
	 * If there is a specified maxTurns and the number of turns is >= maxTurns, return True.
	 * Always returns false if maximum turns is not specified
	 *
	 * @return true if specified maximum turns have reached
	 */
	private boolean reachMaxTurns() {
		if (maxTurns > 0) {
			return this.turns >= maxTurns;
		}
		return false;
	}

	/**
	 * Set the maximum number of moves.
	 *
	 * @param maxTurns specified number of moves
	 */
	public void setMaxTurns(int maxTurns) {
		this.maxTurns = maxTurns;
	}

}
