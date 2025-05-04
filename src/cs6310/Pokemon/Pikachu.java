package cs6310.Pokemon;
import cs6310.Skill.Skill;
import cs6310.Skill.AttackSkill.AttackSkill;
import cs6310.Skill.AttackSkill.Growl;
import cs6310.Skill.AttackSkill.TailWhip;
import cs6310.Skill.AttackSkill.ThunderShock;
import cs6310.Skill.AttackSkill.Thunder;
import cs6310.Skill.DefendSkill.Block;
import cs6310.Skill.DefendSkill.Endure;
import cs6310.Skill.DefendSkill.Protect;

import cs6310.RandomGenerator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

public class Pikachu extends Pokemon {
    private Skill growl;
    private Skill tailWhip;
    private Skill thunderShock;
    private Skill thunder;

    private Skill block;
    private Skill endure;
    private Skill protect;




    public Pikachu(Integer seed){Setup(seed);}
    public Pikachu(){Setup(null);}
    private void Setup(Integer seed){
        this.name="Pikachu";

        this.growl = new Growl();
        this.tailWhip = new TailWhip();
        this.thunderShock = new ThunderShock();
        this.thunder = new Thunder();

        this.block=new Block();
        this.endure=new Endure();
        this.protect=new Protect();

        this.skills = initializeSkills();

        if (seed != null) {
            this.seed = seed;
            this.randomGenerator=new RandomGenerator(this.seed,this.hit_point,this.skills);
        }
    }

    @Override
    public ArrayList<Skill> initializeSkills() {
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(this.growl);
        skills.add(this.tailWhip);
        skills.add(this.thunderShock);
        skills.add(this.thunder);
        skills.add(this.block);
        skills.add(this.endure);
        skills.add(this.protect);
        return skills;
    }



    /*Battle method is a receiver function
    * if Pikachu got attacked by other pokemon
    * Then, this function should be invoked
    * Object otherPokemon (the attacker)
    * damage: damage level is from the attack skill of the other Pokemon
    * */



}
