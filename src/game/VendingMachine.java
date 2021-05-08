package game;

import edu.monash.fit2099.engine.Item;

import java.util.Scanner;
import edu.monash.fit2099.engine.Ground;
import java.util.ArrayList;

public class VendingMachine extends Ground {

    public VendingMachine() {
        super('V');
        addCapability(GroundType.VENDINGMACHINE);
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
                buyFruit();
            }else if (option == 2){
                buyVegetarianMealKit();
            }else if (option == 3){
                buyCarnivoreMealKit();
            }else if (option == 4){
                buyStegosaurEgg();
            }else if (option == 5){
                buyBrachiosaurEgg();
            }else if (option == 6){
                buyAllosaurEgg();
            }else if (option == 7){
                buyLaserGun();
            }
        } while (option!=8);
        return null;
    }

    public Item buyFruit(){
        EcoPoints.decrease_points(30);
        return new Fruit();
    }

    public Item buyVegetarianMealKit(){
        EcoPoints.decrease_points(100);
        return new VegetarianMealKit();
    }

    public Item buyCarnivoreMealKit(){
        EcoPoints.decrease_points(500);
        return new CarnivoreMealKit();
    }

    public Item buyStegosaurEgg(){
        EcoPoints.decrease_points(200);
        return new StegosaurEgg();
    }

    public Item buyBrachiosaurEgg(){
        EcoPoints.decrease_points(500);
        return new BrachiosaurEgg();
    }

    public Item buyAllosaurEgg(){
        EcoPoints.decrease_points(1000);
        return new AllosaurEgg();
    }

    public Item buyLaserGun(){
        EcoPoints.decrease_points(500);
        return new LaserGun();
    }
}
