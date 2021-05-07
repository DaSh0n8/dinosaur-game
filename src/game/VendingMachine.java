package game;

import edu.monash.fit2099.engine.Item;

import java.util.Scanner;
import java.util.ArrayList;

public class VendingMachine extends Item {

    public VendingMachine(String name, char displayChar, boolean portable) {
        super("Vending Machine", 'V', false);
    }

    public Item displayMenu(){
        Scanner scanner = new Scanner(System.in);
        int option;
        do{
            System.out.println("------------------ Welcome to the vending machine ------------------------");
            System.out.println("1) Fruit                                                     30 eco points");
            System.out.println("2) Vegetarian Meal Kit                                      100 eco points");
            System.out.println("3) Carnivore Meal Kit                                       500 eco points");
            System.out.println("4) Stegosaur egg                                            200 eco points");
            System.out.println("5) Brachiosaur egg                                          500 eco points");
            System.out.println("6) Allosaur egg                                            1000 eco points");
            System.out.println("7) Laser gun                                                500 eco points");
            System.out.println("8) Exit");
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("Enter item number: ");
            option = scanner.nextInt();
            while (option < 1 || option > 8){
                System.out.println("Invalid option");
            }
            if (option == 1){
                EcoPoints.decrease_points(30);
                return new Fruit();
            }else if (option == 2){
                EcoPoints.decrease_points(100);
                return new VegetarianMealKit();
            }else if (option == 3){
                EcoPoints.decrease_points(500);
                return new CarnivoreMealKit();
            }else if (option == 4){
                EcoPoints.decrease_points(200);
                return new StegosaurEgg();
            }else if (option == 5){
                EcoPoints.decrease_points(500);
                return new BrachiosaurEgg();
            }else if (option == 6){
                EcoPoints.decrease_points(1000);
                return new AllosaurEgg();
            }else if (option == 7){
                EcoPoints.decrease_points(500);
                return new LaserGun();
            }
        } while (option!=8);
        return null;
    }
}
