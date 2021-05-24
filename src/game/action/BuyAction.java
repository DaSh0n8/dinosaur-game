package game.action;

import edu.monash.fit2099.engine.*;
import game.EcoPoints;
import game.enumeration.DinosaurDiet;
import game.enumeration.DinosaurSpecies;
import game.ground.VendingMachine;
import game.item.*;

import java.util.Scanner;

public class BuyAction extends Action {
    private final static int FRUIT_PRICE = 30;
    private final static int VEGETARIAN_KIT_PRICE = 100;
    private final static int CARNIVORE_KIT_PRICE = 500;
    private final static int STEGOSAUR_EGG_PRICE = 200;
    private final static int BRACHIOSAUR_EGG_PRICE = 500;
    private final static int ALLOSAUR_EGG_PRICE = 1000;
    private final static int PTERODACTYL_EGG_PRICE = 700;
    private final static int LASER_GUN = 500;
    private final static String INSUFFICIENT_POINTS_MESSAGE = "No sufficient eco points for this purchase";

    public BuyAction() {}

    @Override
    public String execute(Actor actor, GameMap map) {

        Item item = itemMenu();
        if(item!=null){
            actor.addItemToInventory(item);
            return actor + " buys " + item;
        }

        return menuDescription(actor);
    }

    /**
     * List out all available items and return the one player chose if ecoPoints is sufficient.
     *
     * @return the Item Player chose
     */
    private Item itemMenu(){
        System.out.println("------------------------------ Vending machine ------------------------------");
        System.out.println("1) Fruit                                                     30 eco points");
        System.out.println("2) Vegetarian Meal Kit                                      100 eco points");
        System.out.println("3) Carnivore Meal Kit                                       500 eco points");
        System.out.println("4) Stegosaur egg                                            200 eco points");
        System.out.println("5) Brachiosaur egg                                          500 eco points");
        System.out.println("6) Allosaur egg                                            1000 eco points");
        System.out.println("7) Pterodactyl egg                                          700 eco points");
        System.out.println("8) Laser gun                                                500 eco points");
        System.out.println("9) Cancel");
        System.out.println("-----------------------------------------------------------------------------");

        int option;
        do {
            System.out.print("Enter item number: ");
            option = readInt();
        } while (option < 1 || option > 9);

        if (option == 1){
            return this.buyFruit();
        }else if (option == 2){
            return buyVegetarianMealKit();
        }else if (option == 3){
            return buyCarnivoreMealKit();
        }else if (option == 4){
            return buyStegosaurEgg();
        }else if (option == 5){
            return buyBrachiosaurEgg();
        }else if (option == 6){
            return buyAllosaurEgg();
        }else if (option ==7){
            return buyPterodactylEgg();
        }else if (option == 8){
            return buyLaserGun();
        }

        return null;
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

    private Item buyFruit(){
        if (EcoPoints.getEcoPoints() >= FRUIT_PRICE){
            EcoPoints.decrease_points(FRUIT_PRICE);
            return new Fruit();
        }
        else{
            System.out.println(INSUFFICIENT_POINTS_MESSAGE);
            return null;
        }
    }

    private Item buyVegetarianMealKit(){
        if (EcoPoints.getEcoPoints() >= VEGETARIAN_KIT_PRICE){
            EcoPoints.decrease_points(VEGETARIAN_KIT_PRICE);
            return new MealKit(DinosaurDiet.HERBIVORE);
        }
        else{
            System.out.println(INSUFFICIENT_POINTS_MESSAGE);
            return null;
        }
    }

    private Item buyCarnivoreMealKit(){
        if (EcoPoints.getEcoPoints() >= CARNIVORE_KIT_PRICE){
            EcoPoints.decrease_points(CARNIVORE_KIT_PRICE);
            return new MealKit(DinosaurDiet.CARNIVORE);
        }
        else{
            System.out.println(INSUFFICIENT_POINTS_MESSAGE);
            return null;
        }
    }

    private Item buyStegosaurEgg(){
        if (EcoPoints.getEcoPoints() >= STEGOSAUR_EGG_PRICE){
            EcoPoints.decrease_points(STEGOSAUR_EGG_PRICE);
            return new Egg(DinosaurSpecies.STEGOSAUR);
        }
        else{
            System.out.println(INSUFFICIENT_POINTS_MESSAGE);
            return null;
        }
    }

    private Item buyBrachiosaurEgg(){
        if (EcoPoints.getEcoPoints() >= BRACHIOSAUR_EGG_PRICE){
            EcoPoints.decrease_points(BRACHIOSAUR_EGG_PRICE);
            return new Egg(DinosaurSpecies.BRACHIOSAUR);
        }
        else{
            System.out.println(INSUFFICIENT_POINTS_MESSAGE);
            return null;
        }
    }

    private Item buyAllosaurEgg(){
        if (EcoPoints.getEcoPoints() >= ALLOSAUR_EGG_PRICE){
            EcoPoints.decrease_points(ALLOSAUR_EGG_PRICE);
            return new Egg(DinosaurSpecies.ALLOSAUR);
        }
        else{
            System.out.println(INSUFFICIENT_POINTS_MESSAGE);
            return null;
        }
    }

    private Item buyPterodactylEgg(){
        if (EcoPoints.getEcoPoints() >= PTERODACTYL_EGG_PRICE){
            EcoPoints.decrease_points(PTERODACTYL_EGG_PRICE);
            return new Egg(DinosaurSpecies.PTERODACTYL);
        }
        else{
            System.out.println(INSUFFICIENT_POINTS_MESSAGE);
            return null;
        }
    }

    private Item buyLaserGun(){
        if (EcoPoints.getEcoPoints() >= LASER_GUN){
            EcoPoints.decrease_points(LASER_GUN);
            return new LaserGun();
        }
        else{
            System.out.println(INSUFFICIENT_POINTS_MESSAGE);
            return null;
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " accesses vending machine";
    }
}
