package cs6310;


import java.lang.reflect.InvocationTargetException;


public class PokemonLoader {

    public static Object loadPokemon(String className) throws ClassNotFoundException {
        Object pokemon = null;
        String fullClassName = "cs6310.Pokemon." + className;
        try {
            Class<?> clazz = Class.forName(fullClassName);
            pokemon = clazz.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException(className);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            System.err.println("Could not instantiate class: " + className);
        }
        return pokemon;
    }

    public static Object loadPokemon(String className, int seed) throws ClassNotFoundException {
        Object pokemon = null;
        String fullClassName = "cs6310.Pokemon." + className;
        try {
            Class<?> clazz = Class.forName(fullClassName);
            pokemon = clazz.getDeclaredConstructor(Integer.class).newInstance(seed);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException(className);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            System.err.println("Could not instantiate class: " + className);
        }
        return pokemon;
    }

}

