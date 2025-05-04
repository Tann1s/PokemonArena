package cs6310.Pokemon;
import cs6310.RandomGenerator;
import cs6310.Skill.Skill;
import cs6310.Skill.AttackSkill.TailWhip;
import cs6310.Skill.AttackSkill.Tackle;
import cs6310.Skill.AttackSkill.Swift;
import cs6310.Skill.AttackSkill.LastResort;

import cs6310.Skill.DefendSkill.Block;
import cs6310.Skill.DefendSkill.Endure;
import cs6310.Skill.DefendSkill.Protect;

import java.util.ArrayList;

public class Eevee extends Pokemon {
    private Skill tailWhip;
    private Skill tackle;
    private Skill swift;
    private Skill lastResort;

    private Skill block;
    private Skill endure;
    private Skill protect;

    public Eevee(Integer seed){Setup(seed);}
    public Eevee(){Setup(null);}
    private void Setup(Integer seed){
        this.name = "Eevee";

        this.tailWhip = new TailWhip();
        this.tackle = new Tackle();
        this.swift = new Swift();
        this.lastResort = new LastResort();

        this.block=new Block();
        this.endure=new Endure();
        this.protect=new Protect();

        this.skills = initializeSkills();
        if (seed != null){
            this.seed = seed;
            this.randomGenerator=new RandomGenerator(this.seed,this.hit_point,this.skills);
        }
    }

    @Override
    public ArrayList<Skill> initializeSkills() {
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(this.tailWhip);
        skills.add(this.tackle);
        skills.add(this.swift);
        skills.add(this.lastResort);
        skills.add(this.block);
        skills.add(this.endure);
        skills.add(this.protect);

        return skills;
    }
}
