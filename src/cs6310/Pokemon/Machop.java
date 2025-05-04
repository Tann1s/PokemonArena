package cs6310.Pokemon;
import cs6310.RandomGenerator;
import cs6310.Skill.Skill;
import cs6310.Skill.AttackSkill.Leer;
import cs6310.Skill.AttackSkill.KarateChop;
import cs6310.Skill.AttackSkill.LowKick;
import cs6310.Skill.AttackSkill.SeismicToss;

import cs6310.Skill.DefendSkill.Block;
import cs6310.Skill.DefendSkill.Endure;
import cs6310.Skill.DefendSkill.Protect;

import java.util.ArrayList;

public class Machop extends Pokemon {
    private Skill leer;
    private Skill karateChop;
    private Skill lowKick;
    private Skill seismicToss;

    private Skill block;
    private Skill endure;
    private Skill protect;

    public Machop(Integer seed){Setup(seed);}
    public Machop(){Setup(null);}
    private void Setup(Integer seed){
        this.name = "Machop";

        this.leer = new Leer();
        this.karateChop = new KarateChop();
        this.lowKick = new LowKick();
        this.seismicToss = new SeismicToss();

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
        skills.add(this.leer);
        skills.add(this.karateChop);
        skills.add(this.lowKick);
        skills.add(this.seismicToss);
        skills.add(this.block);
        skills.add(this.endure);
        skills.add(this.protect);

        return skills;
    }
}
