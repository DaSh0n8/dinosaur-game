package game.ground;

import edu.monash.fit2099.engine.*;

import java.util.Scanner;

import game.EcoPoints;
import game.action.BuyAction;
import game.enumeration.DinosaurSpecies;
import game.enumeration.GroundType;
import game.item.*;

public class VendingMachine extends Ground {

    private static int itemPrice;

    public VendingMachine() {
        super('V');
        addCapability(GroundType.VENDINGMACHINE);
    }

    public static Item itemMenu(){
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
            System.out.println("8) Cancel");
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

    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions actions = super.allowableActions(actor, location, direction);
        actions.add(new BuyAction());
        return actions;

    }

    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    public static Item buyFruit(){
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

    public static Item buyVegetarianMealKit(){
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

    public static Item buyCarnivoreMealKit(){
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

    public static Item buyStegosaurEgg(){
        itemPrice = 200;
        if (EcoPoints.getEcoPoints() <= itemPrice){
            EcoPoints.decrease_points(itemPrice);
            return new Egg(DinosaurSpecies.STEGOSAUR);
        }
        else{
            System.out.println("No sufficient eco points for this purchase");
            return null;
        }
    }

    public static Item buyBrachiosaurEgg(){
        itemPrice = 500;
        if (EcoPoints.getEcoPoints() <= itemPrice){
            EcoPoints.decrease_points(itemPrice);
            return new Egg(DinosaurSpecies.BRACHIOSAUR);
        }
        else{
            System.out.println("No sufficient eco points for this purchase");
            return null;
        }
    }

    public static Item buyAllosaurEgg(){
        itemPrice = 1000;
        if (EcoPoints.getEcoPoints() <= itemPrice){
            EcoPoints.decrease_points(itemPrice);
            return new Egg(DinosaurSpecies.ALLOSAUR);
        }
        else{
            System.out.println("No sufficient eco points for this purchase");
            return null;
        }
    }

    public static Item buyLaserGun(){
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
