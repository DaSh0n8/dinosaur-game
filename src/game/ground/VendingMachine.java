package game.ground;

import edu.monash.fit2099.engine.Item;

import java.util.Scanner;
import edu.monash.fit2099.engine.Ground;
import game.EcoPoints;
import game.enumeration.GroundType;
import game.item.*;

public class VendingMachine extends Ground {

    private int itemPrice;

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
        itemPrice = 30;
        if (EcoPoints.getEcoPoints() <= itemPrice){
            EcoPoints.decrease_points(itemPrice);
            return new Fruit();
        }
        else{
            System.out.println("No sufficient eco points for this purchase");
            return null;
        }
    }

    public Item buyVegetarianMealKit(){
        itemPrice = 100;
        if (EcoPoints.getEcoPoints() <= itemPrice){
            EcoPoints.decrease_points(itemPrice);
            return new VegetarianMealKit();
        }
        else{
            System.out.println("No sufficient eco points for this purchase");
            return null;
        }
    }

    public Item buyCarnivoreMealKit(){
        itemPrice = 500;
        if (EcoPoints.getEcoPoints() <= itemPrice){
            EcoPoints.decrease_points(itemPrice);
            return new CarnivoreMealKit();
        }
        else{
            System.out.println("No sufficient eco points for this purchase");
            return null;
        }
    }

    public Item buyStegosaurEgg(){
        itemPrice = 200;
        if (EcoPoints.getEcoPoints() <= itemPrice){
            EcoPoints.decrease_points(itemPrice);
            return new StegosaurEgg();
        }
        else{
            System.out.println("No sufficient eco points for this purchase");
            return null;
        }
    }

    public Item buyBrachiosaurEgg(){
        itemPrice = 500;
        if (EcoPoints.getEcoPoints() <= itemPrice){
            EcoPoints.decrease_points(itemPrice);
            return new BrachiosaurEgg();
        }
        else{
            System.out.println("No sufficient eco points for this purchase");
            return null;
        }
    }

    public Item buyAllosaurEgg(){
        itemPrice = 1000;
        if (EcoPoints.getEcoPoints() <= itemPrice){
            EcoPoints.decrease_points(itemPrice);
            return new AllosaurEgg();
        }
        else{
            System.out.println("No sufficient eco points for this purchase");
            return null;
        }
    }

    public Item buyLaserGun(){
        itemPrice = 500;
        if (EcoPoints.getEcoPoints() <= itemPrice){
            EcoPoints.decrease_points(itemPrice);
            return new LaserGun();
        }
        else{
            System.out.println("No sufficient eco points for this purchase");
            return null;
        }
    }
}
