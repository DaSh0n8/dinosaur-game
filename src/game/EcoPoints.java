package game;

public class EcoPoints {

    private static int ecoPoints;


    public static int getEcoPoints() {
        return ecoPoints;
    }

    public static int increase_points(int amount){
        ecoPoints += amount;
        return ecoPoints;
    }

    public static int decrease_points(int amount){
        ecoPoints -= amount;
        return ecoPoints;
    }


}
