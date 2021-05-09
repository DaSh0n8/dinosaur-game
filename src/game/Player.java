package game;

import edu.monash.fit2099.engine.*;

/**
 * Class representing the Player.
 */
public class Player extends Actor {

	private Menu menu = new Menu();
	private double rand = Math.random();

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

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		return menu.showMenu(this, actions, display);
	}



	private void checkVendingMachine(Location location){
		GameMap map = location.map();
		int x = location.x();
		int y = location.y();
		if (map.at(x,y).getGround().hasCapability(GroundType.VENDINGMACHINE)){
			VendingMachine v1 = new VendingMachine();
			addItemToInventory(v1.displayMenu());
		}
	}

//	public void pickFruit(Location location){
//		GameMap map = location.map();
//		int x = location.x();
//		int y = location.y();
//		if (map.at(x,y).getGround().hasCapability(GroundType.TREE)){
//			Tree tree = new Tree();
//			if(tree.getFruitOnTreeAmount()!= 0){
//				if (rand*100 < 60) {
//					addItemToInventory(new Fruit());
//				}
//				else{
//					System.out.println("You search the tree or bush for fruit, but you can’t find any ripe ones.");
//				}
//			}
//
//		}else if(map.at(x,y).getGround().hasCapability(GroundType.BUSH)){
//			Bush bush = new Bush();
//			if(bush.getFruitAmount()!= 0){
//				if (rand*100 < 60) {
//					addItemToInventory(new Fruit());
//				}
//				else{
//					System.out.println("You search the tree or bush for fruit, but you can’t find any ripe ones.");
//				}
//			}
//
//		}
//	}

	public void pickUpFruit(Location location){
		GameMap map = location.map();
		int x = location.x();
		int y = location.y();
		Ground thisGround = map.at(x, y).getGround();
		if (thisGround.hasCapability(GroundType.FRUITPLANT)) {
			for (Item thisItem: location.getItems()) {
				if (thisItem.hasCapability(ItemType.FRUIT))
					addItemToInventory(new Fruit());
					break;
			}
		}
	}

}
