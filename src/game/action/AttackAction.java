package game.action;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;
import game.enumeration.DinosaurSpecies;
import game.enumeration.Status;
import game.item.PortableItem;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;
	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target) {
		this.target = target;
	}

	@Override
	public String execute(Actor actor, GameMap map) {

		// if Allosaur attacks Stegosaur
		String description = null;
		if (actor.hasCapability(DinosaurSpecies.ALLOSAUR) && this.target.hasCapability(DinosaurSpecies.STEGOSAUR)) {
			if (actor.hasCapability(Status.ADULT)) {
				this.target.hurt(20);
				actor.heal(20);
			}
			else if (actor.hasCapability(Status.BABY)) {
				this.target.hurt(10);
				actor.heal(10);
			}
			// return description
			if (this.target.isConscious()) {
				this.target.addCapability(Status.WOUNDED);
				description = actor + " attacks " + this.target;
			}
			else {
				description = actor + " kills " + this.target;
				map.removeActor(this.target);
			}
			return description;
		}

		Weapon weapon = actor.getWeapon();

		if (rand.nextBoolean()) {
			return actor + " misses " + target + ".";
		}

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";

		target.hurt(damage);
		if (!target.isConscious()) {
			Item corpse = new PortableItem("dead " + target, '%');
			map.locationOf(target).addItem(corpse);
			
			Actions dropActions = new Actions();
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction());
			for (Action drop : dropActions)		
				drop.execute(target, map);
			map.removeActor(target);	
			
			result += System.lineSeparator() + target + " is killed.";
		}

		return result;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target;
	}
}
