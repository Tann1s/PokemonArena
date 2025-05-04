package cs6310;

public class SeedManager {

    private static int currentSeed = 20;
    public static void setSeed(int seed) {
        currentSeed = seed;
    }
    public static void removeSeed(){
        currentSeed = 1;
    }
    public static int getCurrentSeed(){
        return currentSeed;
    }


}
