package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import edu.monash.fit2099.engine.*;
import game.actor.Brachiosaur;
import game.actor.Player;
import game.actor.Stegosaur;
import game.ground.*;

/**
 * The main class for the Jurassic World game.
 *
 */
public class Application {

	public static void main(String[] args) {
		System.out.println("Welcome to FIT2099 Jurassic Park!");

		int choice;
		do {
			choice = gameMenu();
			// initialize world if user have chosen a game mode
			if (choice != 0) {
				World world = new World(new Display());

				FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(),
						new Tree(), new Bush(), new Lake(), new VendingMachine());

				List<String> map = Arrays.asList(
						".....................~~~~~~~~~~~~~.....~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.........",
						"................................................................................",
						".....#######....................................................................",
						".....#_____#....................................................................",
						".....#_____#....................................................................",
						".....###.###....................................................................",
						"................................................................................",
						"......................................+++.......................................",
						".......................................++++.....................................",
						".............~~~~~~................+++++........................................",
						"...............~~~~~~~...............++++++.....................................",
						"......................................+++.......................................",
						".....................................+++........................................",
						"................................................................................",
						"............+++.................................................................",
						".............+++++..............................................................",
						"...............++........................................+++++..................",
						".............+++....................................++++++++....................",
						"............+++.......................................+++.......................",
						"................................................................................",
						".........................................................................++.....",
						"........................................................................++.++...",
						".........................................................................++++...",
						"..........................................................................++....",
						"................................................................................");

				AdvancedGameMap gameMap = new AdvancedGameMap(groundFactory, map);
				world.addGameMap(gameMap);

				// Place 2 stegosaurs in the middle of the first map, and another 4 brachiosaurs
				gameMap.at(30, 12).addActor(new Stegosaur("Stegosaur", true));
				gameMap.at(32, 12).addActor(new Stegosaur("Stegosaur", true));
				gameMap.at(62, 2).addActor(new Brachiosaur("Brachiosaur", true));
				gameMap.at(70, 8).addActor(new Brachiosaur("Brachiosaur", true));
				gameMap.at(10, 20).addActor(new Brachiosaur("Brachiosaur", true));
				gameMap.at(20, 23).addActor(new Brachiosaur("Brachiosaur", true));
				gameMap.at(35, 10).setGround(new VendingMachine());

				List<String> map2 = Arrays.asList(
						"................................................................................",
						"................................................................................",
						".....#######....................................................................",
						".....#_____#....................................................................",
						".....#_____#....................................................................",
						".....###.###....................................................................",
						"................................................................................",
						"......................................+++.......................................",
						".......................................++++.....................................",
						"...........~~~.....................+++++........................................",
						"...........~~~.......................++++++..............~~~~~..................",
						"......................................+++................~~~~~..................",
						".....................................+++.................~~~~~..................",
						"................................................................................",
						"............+++.................................................................",
						".............+++++..............................................................",
						"...............++........................................+++++..................",
						".............+++....................................++++++++....................",
						"............+++.......................................+++.......................",
						"................................................................................",
						".........................................................................++.....",
						"........................................................................++.++...",
						".........................................................................++++...",
						"..........................................................................++....",
						"................................................................................");

				AdvancedGameMap gameMap2 = new AdvancedGameMap(groundFactory, map2);
				world.addGameMap(gameMap2);

				// start game with the game mode user chose
				if (choice == 1) {
					startChallenge(world, gameMap);
				}
				else if (choice == 2) {
					startSandbox(world, gameMap);
				}

				// reset EcoPoints after a game is finished
				EcoPoints.resetEcoPoints();
			}

		} while (choice != 0);

		System.out.println("Thanks for play FIT2099 Jurassic Park!");
	}

	/**
	 * This method shows menu and ask user to select a game mode top play. When 0 is entered, the program ends.
	 *
	 * @return the choice user selected
	 */
	private static int gameMenu() {
		int choice;
		Scanner scanner = new Scanner(System.in);
		System.out.println("--------------------------------------------------");
		System.out.println("Select a game mode below:");
		System.out.println("1 Challenge");
		System.out.println("2 Sandbox");
		System.out.println("0 EXIT");
		System.out.println("--------------------------------------------------");
		// read the option from user and make sure it's a valid option
		do {
			System.out.print("Option: ");
			choice = readInt();
		} while (choice < 0 || choice > 2);

		return choice;
	}

	/**
	 * This method reads string integer and change it to int type
	 *
	 * @return An integer
	 * @throws NumberFormatException When characters is given by user instead of integers, the program will tells.
	 */
	private static int readInt() {
		int input = -1;
		Scanner scanner = new Scanner(System.in);
		try {
			input = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("Invalid input!");
		}
		return input;
	}

	/**
	 * Prepare the Challenge mode and run the world.
	 *
	 * @param world the world to be run
	 * @param map map to place the Player
	 */
	private static void startChallenge(World world, GameMap map) {
		// ask user to specify a number of moves and eco points
		int moves;
		int points;
		do {
			System.out.print("Enter a specified number of moves (> 0): ");
			moves = readInt();
		} while (moves <= 0);
		do {
			System.out.print("Enter a specified number of eco points (> 0): ");
			points = readInt();
		} while(points <= 0);
		Actor player = new Player("Player", '@', 100, moves);
		EcoPoints.setSpecifiedEcoPoints(points);

		world.addPlayer(player, map.at(9, 4));

		System.out.println("Game starts");
		world.run();
	}

	/**
	 * Prepare the Sandbox mode and run the world.
	 *
	 * @param world the world to be run
	 * @param map map to place the Player
	 */
	private static void startSandbox(World world, GameMap map) {
		Actor player = new Player("Player", '@', 100);
		world.addPlayer(player, map.at(9, 4));

		System.out.println("Game starts");
		world.run();
	}

}
