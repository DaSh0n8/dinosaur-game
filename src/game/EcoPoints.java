package game;

public class EcoPoints {

    private static int ecoPoints = 0;
    private static int specifiedEcoPoints = 0;

    public static int getEcoPoints() {
        return ecoPoints;
    }

    public static void increase_points(int amount){
        ecoPoints += amount;
    }

    public static void decrease_points(int amount){
        ecoPoints -= amount;
    }

    /**
     * Initialize the number of ecoPoints the Player have to get in order to win the game
     *
     * @param points the specified ecoPoints that the Player nid to get
     */
    public static void setSpecifiedEcoPoints(int points) {
        specifiedEcoPoints = points;
    }

    /**
     * Check if there is a specified amount of ecoPoints and is the current ecoPoints have reached the specified amount.
     *
     * @return true if ecoPoints is enough
     */
    public static boolean isEnough() {
        if (specifiedEcoPoints > 0) {
            return ecoPoints >= specifiedEcoPoints;
        }
        else {
            return false;
        }
    }

    /**
     * Reset ecoPoints by making them 0
     */
    public static void resetEcoPoints() {
        ecoPoints = 0;
        specifiedEcoPoints = 0;
    }

}
