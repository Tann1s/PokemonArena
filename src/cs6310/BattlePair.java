package cs6310;
import cs6310.Pokemon.Pokemon;
public class BattlePair {
    private Pokemon pokemonInGroupA=null;
    private Pokemon pokemonInGroupB=null;


    BattlePair(Pokemon groupAPokemon,Pokemon groupBPokemon){
        this.pokemonInGroupA=groupAPokemon;
        this.pokemonInGroupB=groupBPokemon;
    }

    public Pokemon getGroupAPokemon(){
        return this.pokemonInGroupA;
    }

    public Pokemon getGroupBPokemon(){
        return this.pokemonInGroupB;
    }
}
