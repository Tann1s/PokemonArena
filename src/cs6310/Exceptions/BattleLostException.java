package cs6310.Exceptions;

public class BattleLostException extends Exception {
    private String LosingPokemonName="";

    public BattleLostException(String errorMessage, String pokemonName) {
        super(errorMessage);
        this.LosingPokemonName = pokemonName;
    }

    public String getLosingPokemonName() {
        return LosingPokemonName;
    }
}
