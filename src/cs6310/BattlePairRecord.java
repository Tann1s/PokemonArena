package cs6310;

import java.util.ArrayList;

public class BattlePairRecord {
    private String pokemonInGroupA="";

    private ArrayList<String> pokemonInGroupAactions=new ArrayList<>();

    private ArrayList<String> pokemonInGroupBactions=new ArrayList<>();


    private String pokemonInGroupB="";

    private String BattleLogInfo="";

    private String winLossInfo="undecided";

    private String winner="";

    private String loser="";


    public void setPokemonInGroupA(String pokemonInGroupA) {
        this.pokemonInGroupA = pokemonInGroupA;
    }

    public String getPokemonInGroupA() {
        return pokemonInGroupA;
    }

    public void setPokemonInGroupB(String pokemonInGroupB) {
        this.pokemonInGroupB = pokemonInGroupB;
    }

    public String getPokemonInGroupB() {
        return pokemonInGroupB;
    }

    public void setBattleLogInfo(String battleLogInfo) {
        BattleLogInfo += battleLogInfo;
    }

    public String getBattleLogInfo() {
        return BattleLogInfo;
    }

    public void setWinLossInfo(String winLossInfo) {
        this.winLossInfo = winLossInfo;
    }

    public String getWinLossInfo() {
        return winLossInfo;
    }

    public void setLoser(String loser) {
        this.loser = loser;
    }

    public String getLoser() {
        return loser;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }

    public void setPokemonInGroupAactions(ArrayList<String> pokemonInGroupAactions) {
        this.pokemonInGroupAactions = pokemonInGroupAactions;
    }

    public void addActionInPokemonInGroupA(String action){
        this.pokemonInGroupAactions.add(action);
    }

    public ArrayList<String> getPokemonInGroupAactions() {
        return pokemonInGroupAactions;
    }

    public void setPokemonInGroupBactions(ArrayList<String> pokemonInGroupBactions) {
        this.pokemonInGroupBactions = pokemonInGroupBactions;
    }

    public void addActionInGroupBactions(String action){
        this.pokemonInGroupBactions.add(action);
    }

    public ArrayList<String> getPokemonInGroupBactions() {
        return pokemonInGroupBactions;
    }

}

