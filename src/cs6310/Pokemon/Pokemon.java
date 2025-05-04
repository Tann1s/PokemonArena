package cs6310.Pokemon;

import cs6310.LoggerSetup;
import cs6310.RandomGenerator;
import cs6310.Skill.DefendSkill.DefendSkill;
import cs6310.Skill.AttackSkill.AttackSkill;
import cs6310.Skill.Skill;
import cs6310.Exceptions.BattleLostException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class Pokemon {
    public String name;
    public Integer seed;
    public ArrayList<Skill> skills;
    private  static final Logger LOGGER = LoggerSetup.getLogger();

    public int hit_point=25;

    public Integer damageFromOpponent=0;
    public String opponentName="";

    //undecied,attacking,received,defend,lost,win
    public String status="undecied";
    public String logForCurrentBattle="";
    public Skill currentSkill=null;

    public abstract ArrayList<Skill> initializeSkills();

    public RandomGenerator randomGenerator;


    //other Pokemon might not be derived from Pokemon class
    //battle with others
    public void Battle(Object otherPokemon, Integer damage) throws BattleLostException{
        String otherPokemonName = otherPokemon.getClass().getSimpleName();

        //defend skill
        //Pokemon is supposed to reduce damage by current defend skill
        if(this.currentSkill instanceof DefendSkill){
            //this pokemon died
            //otherPokemon won
            int damageReceived=damage-this.currentSkill.getDamage();
            if(damageReceived<0)
                 damageReceived=0;

            if(this.hit_point<=damageReceived){
                this.hit_point=0;
                LOGGER.info(this.name + " successfully reduced " + otherPokemonName + "'s damage by " + this.currentSkill.getDamage() + " with " + this.currentSkill.getName());
                LOGGER.info(this.name+" has received "+damageReceived+" dmg, remaining hp is 0");

                throw new BattleLostException(this.name+" has lost",this.name);
            }else{
                //this pokemon did not die
                //re-generate current skill
                this.hit_point=this.hit_point-damageReceived;
                //if input damage is 0, which means other Pokemon is defending itself or
                if(damage !=0) {
                    LOGGER.info(this.name + " successfully reduced " + otherPokemonName + "'s damage by " + this.currentSkill.getDamage() + " with " + this.currentSkill.getName());
                    LOGGER.info(this.name + " has received " + damageReceived + " dmg, remaining hp is " + this.hit_point);
                }

                this.randomGenerator.setHitPoint(this.hit_point);
                this.currentSkill=this.randomGenerator.getSkill();
                //defend skill can only be used for next battle round
                if(this.currentSkill instanceof DefendSkill){
                    //zero damage for other pokemon
                    LOGGER.info(this.name+" is attempting to defend with "+this.currentSkill.getName());
                    this.handleBattleWithOtherPokemon(otherPokemon,0);

                }else{

                    LOGGER.info(this.name+" is attacking with "+this.currentSkill.getName()+" for "+this.currentSkill.getDamage()+" damage to "+otherPokemonName);
                    this.handleBattleWithOtherPokemon(otherPokemon,this.currentSkill.getDamage());
                }
            }

        }else{
            //get full damage firstly
            //this pokemon died
            //other Pokemon won
            if(this.hit_point<=damage){
                this.hit_point=0;
                LOGGER.info(this.name+" has received "+damage+" dmg, remaining hp is 0");
                throw new BattleLostException(this.name+" has lost",this.name);
            }else{
                this.hit_point=this.hit_point-damage;
                this.randomGenerator.setHitPoint(this.hit_point);
                this.currentSkill=this.randomGenerator.getSkill();
                if(damage !=0) {
                    LOGGER.info(this.name + " has received " + damage + " dmg, remaining hp is " + this.hit_point);
                }
                //defend skill can only be used for next battle round
                if(this.currentSkill instanceof DefendSkill){
                    //zero damage for other pokemon
                    LOGGER.info(this.name+" is attempting to defend with "+this.currentSkill.getName());
                    this.handleBattleWithOtherPokemon(otherPokemon,0);

                }else{

                    LOGGER.info(this.name+" is attacking with "+this.currentSkill.getName()+" for "+this.currentSkill.getDamage()+" damage to "+otherPokemonName);
                    this.handleBattleWithOtherPokemon(otherPokemon,this.currentSkill.getDamage());
                }

            }
        }
    }

    //to be continued
    //groupBattleManager object will be responsible for assigning opponent for current pokemon
    //return damage for other pokemon (opponent)
    public Integer groupBattle(Pokemon otherPokemon){
        if(otherPokemon.getStatus()=="lost")
            return 0;
        String otherPokemonName=otherPokemon.getPokemonName();
        Integer damageRtn=0;
        this.logForCurrentBattle="";
        //defend skill
        //Pokemon is supposed to reduce damage by current defend skill
        if(this.currentSkill instanceof DefendSkill){
            //this pokemon died
            //otherPokemon won
            int damageReceived=this.damageFromOpponent-this.currentSkill.getDamage();
            if(damageReceived<0)
                damageReceived=0;

            if(this.hit_point<=damageReceived){
                this.hit_point=0;
                LOGGER.info(this.name + " successfully reduced " + otherPokemonName + "'s damage by " + this.currentSkill.getDamage() + " with " + this.currentSkill.getName());
                this.logForCurrentBattle+=this.name + " successfully reduced " + otherPokemonName + "'s damage by " + this.currentSkill.getDamage() + " with " + this.currentSkill.getName()+"\n";
                if(this.opponentName !="") {
                    LOGGER.info(this.name + " has received " + damageReceived + " dmg from " + this.opponentName + ", remaining hp is 0");
                    this.logForCurrentBattle += this.name + " has received " + damageReceived + " dmg from " + this.opponentName + ", remaining hp is 0\n";
                }
                LOGGER.info(this.opponentName+" has lost");
                this.logForCurrentBattle+=this.opponentName+" has lost\n";
                this.status="win";
                otherPokemon.setStatus("lost");
            }else{
                //this pokemon did not die
                //re-generate current skill
                this.hit_point=this.hit_point-damageReceived;
                //if input damage is 0, which means other Pokemon is defending itself or
                if(this.damageFromOpponent !=0) {
                    LOGGER.info(this.name + " successfully reduced " + otherPokemonName + "'s damage by " + this.currentSkill.getDamage() + " with " + this.currentSkill.getName());
                    this.logForCurrentBattle += this.name + " successfully reduced " + otherPokemonName + "'s damage by " + this.currentSkill.getDamage() + " with " + this.currentSkill.getName()+"\n";

                    if(this.opponentName !="") {
                        this.status="received";
                        LOGGER.info(this.name + " has received " + damageReceived + " dmg from " + this.opponentName + ", remaining hp is " + this.hit_point);
                        this.logForCurrentBattle+=this.name + " has received " + damageReceived + " dmg from " + this.opponentName + ", remaining hp is " + this.hit_point+"\n";
                    }
                }

                this.randomGenerator.setHitPoint(this.hit_point);
                this.currentSkill=this.randomGenerator.getSkill();
                //defend skill can only be used for next battle round
                if(this.currentSkill instanceof DefendSkill){
                    //zero damage for other pokemon
                    this.status="defend";
                    LOGGER.info(this.name+" is attempting to defend with "+this.currentSkill.getName());
                    this.logForCurrentBattle+=this.name+" is attempting to defend with "+this.currentSkill.getName()+"\n";
                    damageRtn=0;
                }else{
                    this.status="attacking";
                    LOGGER.info(this.name+" is attacking with "+this.currentSkill.getName()+" for "+this.currentSkill.getDamage()+" damage to "+otherPokemonName);
                    this.logForCurrentBattle+=this.name+" is attacking with "+this.currentSkill.getName()+" for "+this.currentSkill.getDamage()+" damage to "+otherPokemonName+"\n";
                    damageRtn=this.currentSkill.getDamage();
                }
            }

        }else{
            //get full damage firstly
            //this pokemon died
            //other Pokemon won
            if(this.hit_point<=this.damageFromOpponent){
                this.hit_point=0;
                if(this.opponentName !="")
                LOGGER.info(this.name+" has received "+this.damageFromOpponent+" dmg from "+this.opponentName+", remaining hp is 0");
                LOGGER.info(this.name+" has lost");
                this.status="lost";
                otherPokemon.setStatus("win");
            }else{
                this.hit_point=this.hit_point-this.damageFromOpponent;
                this.randomGenerator.setHitPoint(this.hit_point);
                this.currentSkill=this.randomGenerator.getSkill();
                if(this.damageFromOpponent !=0 && this.opponentName!="") {
                    this.status="received";
                    LOGGER.info(this.name + " has received " + this.damageFromOpponent + " dmg from "+this.opponentName+", remaining hp is " + this.hit_point);
                }
                //defend skill can only be used for next battle round
                if(this.currentSkill instanceof DefendSkill){
                    //zero damage for other pokemon
                    this.status="defend";
                    LOGGER.info(this.name+" is attempting to defend with "+this.currentSkill.getName());
                    damageRtn=0;
                }else{
                    this.status="attacking";
                    LOGGER.info(this.name+" is attacking with "+this.currentSkill.getName()+" for "+this.currentSkill.getDamage()+" damage to "+otherPokemonName);
                    damageRtn=this.currentSkill.getDamage();
                }

            }
        }

        return damageRtn;

    }

    public String getPokemonName(){
        return this.name;
    }

    public int getHitPoint(){
        return this.hit_point;
    }

    public String getStatus(){
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogForCurrentBattle(){
        return this.logForCurrentBattle;
    }

    public String status(){
        return this.status;
    }

    public Integer damageFromOpponent(){
        return this.damageFromOpponent;
    }

    public void setDamageFromOpponent(Integer damage){
        this.damageFromOpponent=damage;
    }

    public void setOpponentName(String opponentName){
        this.opponentName=opponentName;
    }



    private void handleBattleWithOtherPokemon(Object otherPokemon, Integer damage) throws BattleLostException{
        try {
            Class<?> cls = otherPokemon.getClass();
            Method battleMethod = cls.getMethod("Battle", Object.class, Integer.class);
            try {
                battleMethod.invoke(otherPokemon, this, damage);
            }catch(InvocationTargetException e){
                Throwable cause = e.getCause();
                if (cause instanceof BattleLostException){
                    String otherPokemonName=otherPokemon.getClass().getSimpleName();
                    throw new BattleLostException(otherPokemonName+" has lost",((BattleLostException) cause).getLosingPokemonName());
                }
            }
        } catch (NoSuchMethodException e) {
            LOGGER.log(Level.SEVERE, "The method Battle(Object, Integer) was not found.");
            System.err.println("The method Battle(Object, Integer) was not found.");
        }  catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    public void DisplayInfo() {
        LOGGER.info("Pokemon: " + this.name + " has " + this.hit_point + " hp");
        ArrayList<Skill> skills = new ArrayList<>();
        skills = this.initializeSkills();
        ArrayList<AttackSkill> attackSkills = new ArrayList<>();
        ArrayList<DefendSkill> defenseSkills = new ArrayList<>();

        for (Skill s: skills) {
            if (s instanceof AttackSkill){
                attackSkills.add((AttackSkill) s);
            }else{
                defenseSkills.add((DefendSkill) s);
            }
        };

        LOGGER.info("Attack Skills:");
        Collections.sort(attackSkills);

        for (var i = 0; i < attackSkills.size(); i++) {
            var skill = attackSkills.get(i);
            LOGGER.info("Name: " + skill.getName() + " Damage: " + skill.getDamage());
        }

        Collections.sort(defenseSkills);
        LOGGER.info("Defense Skills:");
        for (var i = 0; i < defenseSkills.size(); i++) {
            var skill = defenseSkills.get(i);
            LOGGER.info("Name: " + skill.getName() + " Defense: " + skill.getDamage());
        }
    }
    
}


