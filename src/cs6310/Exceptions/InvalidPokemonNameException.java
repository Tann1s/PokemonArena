package cs6310.Exceptions;

public class InvalidPokemonNameException extends Exception {
    public InvalidPokemonNameException(String message) {
        super(message);
    }
}
