package cs6310;

import cs6310.Exceptions.InvalidPokemonNameException;
import cs6310.Pokemon.Pokemon;
import cs6310.Pokemon.Pikachu;
import cs6310.Pokemon.Charmander;
import cs6310.Pokemon.Squirtle;
import cs6310.Pokemon.Bulbasaur;
import cs6310.Pokemon.Eevee;
import cs6310.Pokemon.Gastly;
import cs6310.Pokemon.Jigglypuff;
import cs6310.Pokemon.Machop;
import cs6310.BattlePair;

import javax.swing.*;
import java.util.ArrayList;
import java.util.logging.Logger;


public class GroupBattleManager {
    private String groupAName="";
    private ArrayList<Pokemon> pokemonGroupAList=new ArrayList<>();
    private String groupBName="";
    private ArrayList<Pokemon> pokemonGroupBList=new ArrayList<>();

    private ArrayList<ArrayList<BattlePairRecord>> battlePairRecordsList=new ArrayList<>();

    private  static final Logger LOGGER = LoggerSetup.getLogger();

    private String finalMsg="";
    int seed = 10;
    int swapFreq=1;

    public GroupBattleManager(Integer seed){this.seed=seed;}

    public void setPokemonGroupAName(String pokemonGroupAName){
        this.groupAName=pokemonGroupAName;
    }

    public String getPokemonGroupAName(){
        return this.groupAName;
    }

    public void setPokemonGroupBName(String pokemonGroupBName){
        this.groupBName=pokemonGroupBName;
    }

    public String getPokemonGroupBName(){
        return this.groupBName;
    }

    public void setPokemonGroupAList(String[] pokemonList){
        this.pokemonGroupAList=new ArrayList<Pokemon>();
        for(int idx=0;idx<pokemonList.length;++idx){

                Pokemon  pokemonTmp = this.choosePokemon(pokemonList[idx], this.seed);
                this.pokemonGroupAList.add(pokemonTmp);


        }
    }

    public void setSwapFreq(Integer freq){
        this.swapFreq=freq;
    }

    public void setPokemonGroupBList(String[] pokemonList){
        this.pokemonGroupBList=new ArrayList<Pokemon>();
        for(int idx=0;idx<pokemonList.length;++idx){
                Pokemon  pokemonTmp = this.choosePokemon(pokemonList[idx], this.seed);
                this.pokemonGroupBList.add(pokemonTmp);
        }
    }

    public void groupBattle(){

        while(this.validateGroupWinLoss().equals("Not Finished")){
            ArrayList<BattlePair> battlePairList=this.swap();

            for(int idx=0;idx<this.swapFreq;++idx){
                ArrayList<BattlePairRecord> battlePairRecords=new ArrayList<>();
                for(BattlePair battlePair:battlePairList){

                    BattlePairRecord battleRecord=new BattlePairRecord();

                    Pokemon pokemonInGroupA=battlePair.getGroupAPokemon();

                    Pokemon pokemonInGroupB=battlePair.getGroupBPokemon();

                    assert pokemonInGroupA != null;
                    assert pokemonInGroupB != null;

                    if(pokemonInGroupA.getStatus()=="lost"){
                        battleRecord.addActionInPokemonInGroupA("lost");
                        battleRecord.addActionInGroupBactions("win");
                        pokemonInGroupB.setDamageFromOpponent(0);
                        battleRecord.setPokemonInGroupA(pokemonInGroupA.getPokemonName());
                        battleRecord.setPokemonInGroupB(pokemonInGroupB.getPokemonName());
                        battlePairRecords.add(battleRecord);
                        break;
                    }

                    if(pokemonInGroupB.getStatus()=="lost"){
                        battleRecord.addActionInGroupBactions("lost");
                        battleRecord.addActionInPokemonInGroupA("win");
                        pokemonInGroupA.setDamageFromOpponent(0);
                        battleRecord.setPokemonInGroupA(pokemonInGroupA.getPokemonName());
                        battleRecord.setPokemonInGroupB(pokemonInGroupB.getPokemonName());
                        battlePairRecords.add(battleRecord);
                       break;
                    }


                           Integer damageFromPokemonInGroupA = pokemonInGroupA.groupBattle(pokemonInGroupB);


                           battleRecord.addActionInPokemonInGroupA(pokemonInGroupA.getStatus());
                           battleRecord.setBattleLogInfo(pokemonInGroupA.getLogForCurrentBattle());


                           pokemonInGroupB.setDamageFromOpponent(damageFromPokemonInGroupA);
                           pokemonInGroupB.setOpponentName(pokemonInGroupA.getPokemonName());


                          Integer damageFromPokemonInGroupB = pokemonInGroupB.groupBattle(pokemonInGroupA);
                         battleRecord.setBattleLogInfo(pokemonInGroupB.getLogForCurrentBattle());

                          battleRecord.addActionInGroupBactions(pokemonInGroupB.getStatus());


                          pokemonInGroupA.setOpponentName(pokemonInGroupB.getPokemonName());
                          pokemonInGroupA.setDamageFromOpponent(damageFromPokemonInGroupB);



                    battleRecord.setPokemonInGroupA(pokemonInGroupA.getPokemonName());
                    battleRecord.setPokemonInGroupB(pokemonInGroupB.getPokemonName());





                    battlePairRecords.add(battleRecord);
                }
                this.battlePairRecordsList.add(battlePairRecords);
            }
        }
    }

    public ArrayList<ArrayList<BattlePairRecord>> getBattlePairRecords() {
        return battlePairRecordsList;
    }

    public void setFinalMsg(String finalMsg) {
        this.finalMsg = finalMsg;
    }

    public String getFinalMsg(){
        return this.finalMsg;
    }

    //the purpose of swap() is finding the group A's pokemon's opponent in group B
   //
   //normally, pokemon in group A should find the the pokemon(opponent) with closet hit point in group B
   //group A's size may not the same as group B
   //sometimes group A size(pokemon number) larger, some times group B size lager.
   //
   //if group A and group B has the different size, which means some pokemon in group A or group B cannot find its opponent.
   //
   //if pokemon.getHitPoint() is 0, then it means this pokemon can NOT join the battle
    public ArrayList<BattlePair> swap(){

        ArrayList<Pokemon> eligibleGroupA = new ArrayList<>();
        for (Pokemon pokemon : pokemonGroupAList) {
            if (pokemon.getHitPoint() > 0) {
                eligibleGroupA.add(pokemon);
            }
        }

        ArrayList<Pokemon> eligibleGroupB = new ArrayList<>();
        for (Pokemon pokemon : pokemonGroupBList) {
            if (pokemon.getHitPoint() > 0) {
                eligibleGroupB.add(pokemon);
            }
        }

        ArrayList<BattlePair> result = new ArrayList<>();
        boolean[] usedGroupB = new boolean[eligibleGroupB.size()];

        for (Pokemon a : eligibleGroupA) {
            int closestIndex = -1;
            int minDifference = Integer.MAX_VALUE;
            for (int i = 0; i < eligibleGroupB.size(); i++) {
                if (!usedGroupB[i]) {
                    int diff = Math.abs(a.getHitPoint() - eligibleGroupB.get(i).getHitPoint());
                    if (diff < minDifference) {
                        minDifference = diff;
                        closestIndex = i;
                    }
                }
            }

            if (closestIndex != -1) {
                result.add(new BattlePair(a, eligibleGroupB.get(closestIndex)));
                usedGroupB[closestIndex] = true;
            }

            // Stop if the result size equals the smaller group size
            if (result.size() == Math.min(pokemonGroupAList.size(), pokemonGroupBList.size())) {
                break;
            }
        }

        return result;



    }

    public void setSeed(Integer seed){
        this.seed=seed;
    }

    private String validateGroupWinLoss(){

        int groupAHitPoint=0;
        int groupBHitPoint=0;

        for(Pokemon a: this.pokemonGroupAList){
            groupAHitPoint+=a.getHitPoint();
        }

        for(Pokemon b: this.pokemonGroupBList){
            groupBHitPoint+=b.getHitPoint();
        }



        if(groupAHitPoint==0 && groupBHitPoint>0){
            LOGGER.info(this.groupBName+" wins and "+this.groupAName+" loss");
            this.finalMsg=this.groupBName+" wins and "+this.groupAName+" loss";
            return "groupB";
        }

        if(groupAHitPoint>0 && groupBHitPoint==0){
            LOGGER.info(this.groupAName+" wins and "+this.groupBName+" loss");
            this.finalMsg=this.groupBName+" wins and "+this.groupAName+" loss";
            return "groupA";
        }

        if(groupAHitPoint==0 && groupBHitPoint==0){
            LOGGER.info("Draw");
            this.finalMsg="Draw";
            return "draw";
        }


        return "Not Finished";
    }



    private Pokemon choosePokemon(String pokemonName, Integer seedAssigned) {
        pokemonName = pokemonName.trim();

        switch(pokemonName){
            case "Pikachu":
                return new Pikachu(this.seed);

            case "Bulbasaur":
                return new Bulbasaur(this.seed);

            case "Charmander":
                return new Charmander(this.seed);

            case "Squirtle":
                return new Squirtle(this.seed);

            case "Eevee":
                return new Eevee(this.seed);

            case "Jigglypuff":
                return new Jigglypuff(this.seed);

            case "Machop":
                return new Machop(this.seed);

            case "Gastly":
                return new Gastly(this.seed);
        }



        return null;
    }

}
