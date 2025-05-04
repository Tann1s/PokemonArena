package cs6310;

import cs6310.Exceptions.BattleLostException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static cs6310.PokemonLoader.loadPokemon;


public class BattleArena {

    public void battle(String pokemon1, String pokemon2) throws BattleLostException, ClassNotFoundException {
        int seed = SeedManager.getCurrentSeed();
        Object p1;
        Object p2;
        if (seed >= 0) {
            p1 = loadPokemon(pokemon1, SeedManager.getCurrentSeed());
            p2 = loadPokemon(pokemon2, SeedManager.getCurrentSeed() + 1);
        } else {
            p1 = loadPokemon(pokemon1);
            p2 = loadPokemon(pokemon2);
        }

        // Assuming pokemon1 and pokemon2 have a method battle(Object callingClass, Integer damage)
        // Here, we are calling pokemon1's battle method, passing pokemon2 as the opponent and a sample damage value (e.g., 10)
        try {
            p1.getClass().getMethod("Battle", Object.class, Integer.class).invoke(p1, p2, 0);
        } catch (InvocationTargetException e) {
            Throwable target = e.getTargetException();
            if (target instanceof BattleLostException) {
                BattleLostException ble = (BattleLostException) target;
                throw new BattleLostException(e.getMessage(), ble.getLosingPokemonName());
            } else {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayInfo(String pokemon) throws ClassNotFoundException {
        Object p1 = null;
        p1 = loadPokemon(pokemon);
        // Here, we are calling pokemon1's battle method, passing pokemon2 as the opponent and a sample damage value (e.g., 10)
        try {
            p1.getClass().getMethod("DisplayInfo").invoke(p1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tournament(List<String> pokemons) {
        if (!isPowerOfTwo(pokemons.size())) {
            System.out.println("Participant size invalid.");
            return;
        }

        List<String> competitors = new ArrayList<>(pokemons);
        int round = 1;
        while (competitors.size() > 1) {
            List<String> winners = new ArrayList<>();
            String winner = null;
            for (int i = 0; i < competitors.size(); i += 2) {
                String competitorOne = competitors.get(i);
                String competitorTwo = competitors.get(i+1);
                System.out.println("Starting tournament round " + round +  " with " + competitorOne + " and " + competitorTwo);
                try {
                    battle(competitors.get(i), competitors.get(i + 1));
                } catch (ClassNotFoundException e) {
                    winner = (competitorOne.equals(e.getMessage())) ? competitorTwo : competitorOne;
                    System.out.println("Name: " + e.getMessage() + " was invalid and has forfeited the battle");
                    System.out.println(e.getMessage() + " has lost");
                    System.out.println(winner + " has won the battle");
                } catch (BattleLostException e) {
                    // If a BattleLostException is caught, determine the winner based on the loser's name
                    winner = (competitors.get(i).equals(e.getLosingPokemonName())) ? competitors.get(i + 1) : competitors.get(i);
                    String loser = (competitors.get(i).equals(e.getLosingPokemonName())) ? competitors.get(i) : competitors.get(i + 1);
                    System.out.println(loser + " has lost");
                    System.out.println(winner + " has won the battle");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (winner != null) {
                    winners.add(winner);
                    System.out.println(winner + " has won round " + round);
                }
                round++;
            }
            competitors = winners;
        }

        System.out.println(competitors.get(0) + " has won the tournament");
    }

    public boolean isPowerOfTwo(int n) {
        return n > 1 && (n & (n - 1)) == 0;
    }
}
