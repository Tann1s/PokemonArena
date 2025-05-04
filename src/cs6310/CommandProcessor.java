package cs6310;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Arrays;


import cs6310.Exceptions.InvalidPokemonNameException;
import cs6310.Exceptions.BattleLostException;
import cs6310.Pokemon.Pokemon;
import cs6310.GroupBattleManager;


public class CommandProcessor {
//    private static final Logger LOGGER = Logger.getLogger(CommandProcessor.class.getName());
    private  static final Logger LOGGER = LoggerSetup.getLogger();
    int seed = 10;

    private Object pokemonA;
    private Object pokemonB;

    //attributes for group battle
    private GroupBattleManager groupBattleManager=new GroupBattleManager(this.seed);;




    public void ProcessCommands(String[] args) {
        var commandLineInput = new Scanner(System.in);
        var delimiter = ",";

        while (true) {
            String wholeInputLine = commandLineInput.nextLine();

            String ret=this.executeInput(wholeInputLine);

            if(ret=="stop"){
                commandLineInput.close();
                break;
            }

        }

    }

    private String executeInput(String input) {
        input = input.trim();
        if(this.isComment(input)){
            LOGGER.info("> "+input);
            return "comment";
        }

        if(this.isSetSeedCommand(input)){
            LOGGER.info("> "+input);
            this.setSeed(input);
            return "setseed";
        }

        if(this.isRemoveSeedCommand(input)){
            LOGGER.info("> "+input);
            this.removeSeed(input);
            return "removeseed";
        }

        if(this.isBattleCommand(input)){
            LOGGER.info("> "+input);
            this.battle(input);
            return "battle";
        }

        if(this.isStopCommand(input)){
            LOGGER.info("> "+input);
            this.stop();
            return "stop";
        }
        if(this.isDisplayInfo(input)){
            LOGGER.info("> "+input);
            this.displayInfo(input);
            return "displayInfo";
        }

        if(this.isTournamentCommand(input)){
            LOGGER.info("> "+input);
            this.tournament(input);
            return "tournament";
        }

        if(this.isSwapFreqCommand(input)){
            LOGGER.info("> "+input);
            this.setSwapFreq(input);
            return "swap";

        }

        if(this.isGroupBattleCommand(input)){
            LOGGER.info("> "+input);
            this.initGroupBattle(input);
            this.startGroupBattle();
            return "groupBattle";
        }
        return "no valid command";
    }

    private boolean isComment(String input){
        if(input.startsWith("//"))
           return true;
       else
           return false;
    }

    private boolean isCommand(String input){
        if(!input.startsWith("//"))
            return true;
        else
            return false;
    }

    private boolean isBattleCommand(String input){
        if(!this.isCommand(input)){
            return false;
        }
        String delimiter = ",";
        String[] tokens=input.split(delimiter);
        if(input.startsWith("battle")){
            return true;
        }else{
            return false;
        }
    }

    private boolean isSetSeedCommand(String input){
        if(!this.isCommand(input)){
            return false;
        }
        String delimiter = ",";
        String[] tokens=input.split(delimiter);
        if(input.startsWith("setseed")&&tokens.length==2){
             return true;
        }else{
            return false;
        }
    }

    private boolean isRemoveSeedCommand(String input){
        if(!this.isCommand(input)){
            return false;
        }
        if(input.startsWith("removeseed")){
            return true;
        }else{
            return false;
        }

    }

    private boolean isStopCommand(String input){
        if(!this.isCommand(input)){
            return false;
        }
        if(input.startsWith("stop")){
            return true;
        }else{
            return false;
        }
    }

    private boolean isDisplayInfo(String input){
        if(!this.isCommand(input)){
            return false;
        }
        if(input.startsWith("displayinfo")){
            return true;
        }else{
            return false;
        }
    }

    private boolean isTournamentCommand(String input){
        if(!this.isCommand(input)){
            return false;
        }

        if(input.startsWith("tournament")){
            return true;
        }else{
            return false;
        }

    }

    private boolean isGroupBattleCommand(String input){
        if(!this.isCommand(input)){
            return false;
        }

        if(input.startsWith("groupBattle")){
             return true;
        }else{
            return false;
        }
    }

    private boolean isSwapFreqCommand(String input){
        if(!this.isCommand(input)) {
            return false;
        }


        String delimiter = ",";
        String[] tokens=input.split(delimiter);
        if(input.startsWith("setSwapFreq")&&tokens.length==2){
            return true;
        }else{
            return false;
        }
    }




    //groupBattle command is like groupBattle,groupNameA, pokemon1,pokemon2,VS,groupNameB,pokemon3,pokemon4
    private void initGroupBattle(String input){
        if(!this.isGroupBattleCommand(input)){
            return;
        }

        this.groupBattleManager.setSeed(this.seed);

        String trimmedInput = input.substring(input.indexOf(",") + 1).trim();
        String[] groups = trimmedInput.split("\\s*VS\\s*");


        if (groups.length != 2) {
            System.out.println("Invalid input format.");
            return;
        }

        String cleanedGroupA = groups[0].trim();
        if (cleanedGroupA.startsWith(",")) {
            cleanedGroupA = cleanedGroupA.substring(1).trim();
        }

        // Split the first group by commas to separate the group name from the Pokémon
        String[] groupA = cleanedGroupA.trim().split("\\s*,\\s*");


        this.groupBattleManager.setPokemonGroupAName(groupA[0].trim());
        this.groupBattleManager.setPokemonGroupAList(Arrays.copyOfRange(groupA, 1, groupA.length));

        String cleanedGroupB = groups[1].trim();
        if (cleanedGroupB.startsWith(",")) {
            cleanedGroupB = cleanedGroupB.substring(1).trim();
        }

        // Split the second group by commas to separate the group name from the Pokémon
        String[] groupB = cleanedGroupB.trim().split("\\s*,\\s*");

        this.groupBattleManager.setPokemonGroupBName(groupB[0].trim());
        this.groupBattleManager.setPokemonGroupBList(Arrays.copyOfRange(groupB, 1, groupB.length));

    }

    private void startGroupBattle(){
        this.groupBattleManager.groupBattle();
    }

    //set groupBattle swap frequency
    //command is like swapFreq,1 or swapFreq,2 ...
    //swapFreq,1 means swap every 1 round of battle
    //swapFreq,2 means swap every 2 round of battle
    private void setSwapFreq(String input){
        if(!this.isSwapFreqCommand(input)){
            return;
        }

        String delimiter = ",";
        String[] tokens=input.split(delimiter);

        String swapFreqStr=tokens[1].trim();

        this.groupBattleManager.setSwapFreq(Integer.valueOf(swapFreqStr));
    }







    private void battle(String input){
        this.pokemonA = null;
        this.pokemonB = null;

        String delimiter = ",";
        String[] tokens = input.split(delimiter);
        if(tokens.length !=3){
            LOGGER.log(Level.WARNING, "Participant size invalid.");
            return;
        }
        //initial Pokemon A
        String pokemonAName = tokens[1];


        try {
            this.pokemonA = choosePokemon(pokemonAName, this.seed);
        } catch (InvalidPokemonNameException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        //initial Pokemon B
        String pokemonBName = tokens[2];

        try {
            this.pokemonB = choosePokemon(pokemonBName, this.seed + 1);
        } catch (InvalidPokemonNameException e) {
            this.pokemonB=null;
            if(this.pokemonA !=null) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        }

        //if pokemonA is null, no matter pokemonB is null or not, pokemonB wins
        if(this.pokemonA ==null){
            LOGGER.info(pokemonAName + " has lost");
            LOGGER.info(pokemonBName + " has won the battle");
            return;
        }

        //if pokemonB is null but pokemonA is not null, pokemon A wins
        if(this.pokemonA !=null && this.pokemonB==null){
            LOGGER.info(pokemonBName + " has lost");
            LOGGER.info(pokemonAName + " has won the battle");
            return;
        }

        //if both pokemon A & B are not null, go for the battle
        if(this.pokemonA !=null && this.pokemonB !=null){
            this.startBattle(pokemonAName,pokemonBName);
        }

        /*
        if (this.pokemonA != null && this.pokemonB == null){
            //TODO: Weather need to handle throw exception BattleLostException in battle?
            //Rui: No need to throw BattleLostException
            LOGGER.info(pokemonBName + " has lost");
            LOGGER.info(pokemonAName + " has won the battle");
            return;
        } else if (this.pokemonA == null && this.pokemonB != null){
            //TODO: Weather need to handle throw exception BattleLostException in battle?
            //Rui: No need to throw BattleLostException
            LOGGER.info(pokemonAName + " has lost");
            LOGGER.info(pokemonBName + " has won the battle");
            return;
        }
        else if (this.pokemonA != null && this.pokemonB != null) {
            //display battle info
            LOGGER.info("> // battle between " + pokemonAName + " & " + pokemonBName);
            LOGGER.info("> " + input);
            this.startBattle();
        } else {
            //TODO: what if both Pokemons are null?
            //Rui: If both Pokemons are null, then the second Pokemon (Pokemon B) wins, see test result 10
            LOGGER.info(pokemonAName + " has lost");
            LOGGER.info(pokemonBName + " has won the battle");
            return;
        }
         */

    }

    private void startBattle(String pokemonAName,String pokemonBName){
        if(this.pokemonA !=null && this.pokemonB !=null){
            Class<?> cls = this.pokemonA.getClass();
            try {
                Method battleMethod = cls.getMethod("Battle", Object.class, Integer.class);
                try {
                    battleMethod.invoke(this.pokemonA, this.pokemonB, 0);
                } catch (InvocationTargetException e) {
                    Throwable cause = e.getCause();

                    if (cause instanceof BattleLostException) {

                        BattleLostException battleLostException = (BattleLostException) cause;

                        String tmpLoser = battleLostException.getLosingPokemonName().trim();

                        if (pokemonAName.equals(tmpLoser)) {
                            LOGGER.info(pokemonAName+" has lost");
                            LOGGER.info(pokemonBName+" has won the battle");
                        } else {
                            LOGGER.info(pokemonBName+" has lost");
                            LOGGER.info(pokemonAName+" has won the battle");
                        }
                    }
                }
            }catch (NoSuchMethodException e) {
                LOGGER.log(Level.SEVERE, "The method Battle(Object, Integer) was not found.");
                System.err.println("The method Battle(Object, Integer) was not found.");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }



    private void setSeed(String input){
        String delimiter = ",";
        String[] tokens=input.split(delimiter);

        String seedStr=tokens[1].trim();
        this.seed= Integer.valueOf(seedStr);
    }

    private void removeSeed(String input){
       this.seed=1;
    }

    private void stop(){
        LOGGER.info("stop acknowledged");
        LOGGER.info("simulation terminated");
    }

    private void displayInfo(String input){
        String delimiter = ",";
        String[] tokens=input.split(delimiter);
        String pokemonName=tokens[1];
        pokemonName = pokemonName.trim();
        Object pokemonInstance = null;
        try {
            Class<?> pokemonClass = Class.forName("cs6310.Pokemon." + pokemonName);
            Method diplayMethod = pokemonClass.getMethod("DisplayInfo");
            pokemonInstance = pokemonClass.getDeclaredConstructor().newInstance();
            diplayMethod.invoke(pokemonInstance);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "An unexpected error occurred", e);
        }
    }

    /*function battleBteweenTwoPokemons are only used for tournament*/
    /*return the loser*/
    private String battleBetweenTwoPokemons(ArrayList<String> pokemonList,int roundIdx){

        String loserAtThisRound="";
        if(pokemonList.size() !=2)
            return loserAtThisRound;

        String pokemonTmpAName= pokemonList.get(0);
        String pokemonTmpBName= pokemonList.get(1);

        Object pokemonTmpA=null;
        Object pokemonTmpB=null;

        LOGGER.info("Starting tournament round "+roundIdx+" with "+pokemonTmpAName+" and "+pokemonTmpBName);

        try {
            pokemonTmpA = this.choosePokemon(pokemonTmpAName, this.seed);
        }catch(InvalidPokemonNameException e){
            LOGGER.info(e.getMessage());
        }

        try {
            pokemonTmpB = this.choosePokemon(pokemonTmpBName, this.seed + 1);
        }catch(InvalidPokemonNameException e){
            pokemonTmpB=null;
            if(pokemonTmpA !=null){
                LOGGER.warning(e.getMessage());
            }
        }

        if(pokemonTmpA ==null){
            LOGGER.info(pokemonTmpAName+" has lost");
            LOGGER.info(pokemonTmpBName+" has won the battle");
            LOGGER.info(pokemonTmpBName+" has won round "+roundIdx);
            loserAtThisRound=pokemonTmpAName;
        }

        if(pokemonTmpA !=null && pokemonTmpB==null){
            LOGGER.info(pokemonTmpBName+" has lost");
            LOGGER.info(pokemonTmpAName+" has won the battle");
            LOGGER.info(pokemonTmpAName+" has won round "+roundIdx);
            loserAtThisRound=pokemonTmpBName;
        }

        if(pokemonTmpA !=null && pokemonTmpB !=null) {
            Class<?> cls = pokemonTmpA.getClass();
            try {
                Method battleMethod = cls.getMethod("Battle", Object.class, Integer.class);
                try {
                    battleMethod.invoke(pokemonTmpA, pokemonTmpB, 0);
                } catch (InvocationTargetException e) {
                    Throwable cause = e.getCause();

                    if (cause instanceof BattleLostException) {

                        BattleLostException battleLostException = (BattleLostException) cause;

                        String tmpLoser = battleLostException.getLosingPokemonName().trim();
                        loserAtThisRound = tmpLoser;

                        if (pokemonTmpAName.equals(tmpLoser)) {
                            LOGGER.info(pokemonTmpAName+" has lost");
                            LOGGER.info(pokemonTmpBName+" has won the battle");
                            LOGGER.info(pokemonTmpBName + " has won round " + roundIdx);
                        } else {
                            LOGGER.info(pokemonTmpBName+" has lost");
                            LOGGER.info(pokemonTmpAName+" has won the battle");
                            LOGGER.info(pokemonTmpAName + " has won round " + roundIdx);
                        }
                    }
                }
            }catch (NoSuchMethodException e) {
                System.err.println("The method Battle(Object, Integer) was not found.");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }



     return loserAtThisRound;
    }

    private void tournamentWithPokemonList(ArrayList<String> pokemonList, int roundIdx){

        if(pokemonList.size()==2){
            String loserAtThisRound=this.battleBetweenTwoPokemons(pokemonList,roundIdx);

            String pokemonTmpAName= pokemonList.get(0).trim();
            String pokemonTmpBName= pokemonList.get(1).trim();

            if(loserAtThisRound.equals(pokemonTmpAName)){
                //pokemonTmpB is the winner
                LOGGER.info(pokemonTmpBName+" has won the tournament");
            }else{

                //pokemonTmpA is the winner
                LOGGER.info(pokemonTmpAName+" has won the tournament");
            }
            return;
        }

        int tokenIdx=0;

        ArrayList<String> newPokemonList = new ArrayList<String>();

        while(tokenIdx<pokemonList.size()-1){

            String pokemonTmpAName= pokemonList.get(tokenIdx).trim();
            String pokemonTmpBName= pokemonList.get(tokenIdx + 1).trim();
            ArrayList<String> tmpPokemonList = new ArrayList<String>();

            tmpPokemonList.add(pokemonTmpAName);
            tmpPokemonList.add(pokemonTmpBName);

            String loserAtThisRound=this.battleBetweenTwoPokemons(tmpPokemonList,roundIdx);

            roundIdx+=1;

            if(pokemonTmpAName.equals(loserAtThisRound)){
                //pokemon B win this round
                newPokemonList.add(pokemonTmpBName);
            }else{
                newPokemonList.add(pokemonTmpAName);
            }

            tokenIdx+=2;

        }

        this.tournamentWithPokemonList(newPokemonList,roundIdx);

    }

    private void tournament(String input){
        if(!this.isTournamentCommand(input)){
            return;
        }

        String delimiter = ",";
        String[] tokens=input.split(delimiter);

        int pokemonSize=tokens.length-1;

        //make sure pokemon size large or equal 4 and power of 2
        if (pokemonSize < 4 || (pokemonSize & (pokemonSize - 1)) != 0) {
            LOGGER.info("Participant size invalid.");
            return;
        }

        ArrayList<String> pokemonList = new ArrayList<String>();
        for(int idx=1;idx<tokens.length;++idx){
            pokemonList.add(tokens[idx]);
        }

        //start tournament
        this.tournamentWithPokemonList(pokemonList,1);
    }

    private Object choosePokemon(String pokemonName, Integer seedAssigned) throws InvalidPokemonNameException {
        pokemonName = pokemonName.trim();
        Object pokemonInstance = null;
        try {
            Class<?> pokemonClass = Class.forName("cs6310.Pokemon." + pokemonName);
            pokemonInstance = pokemonClass.getDeclaredConstructor(Integer.class).newInstance(seedAssigned);
        } catch (ClassNotFoundException e) {
            String msg = "Name: " + pokemonName + " was invalid and has forfeited the battle";
            LOGGER.log(Level.SEVERE, msg);
            throw new InvalidPokemonNameException(msg);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            LOGGER.log(Level.SEVERE, "An unexpected error occurred", e);
        }
        return pokemonInstance;
    }
}
