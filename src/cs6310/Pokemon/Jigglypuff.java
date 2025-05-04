package cs6310.Pokemon;
import cs6310.RandomGenerator;
import cs6310.Skill.Skill;
import cs6310.Skill.AttackSkill.Sing;
import cs6310.Skill.AttackSkill.Pound;
import cs6310.Skill.AttackSkill.DisarmingVoice;
import cs6310.Skill.AttackSkill.HyperVoice;

import cs6310.Skill.DefendSkill.Block;
import cs6310.Skill.DefendSkill.Endure;
import cs6310.Skill.DefendSkill.Protect;

import java.util.ArrayList;

public class Jigglypuff extends Pokemon {
    private Skill sing;
    private Skill pound;
    private Skill disarmingVoice;
    private Skill hyperVoice;

    private Skill block;
    private Skill endure;
    private Skill protect;

    public Jigglypuff(Integer seed){Setup(seed);}
    public Jigglypuff(){Setup(null);}
    private void Setup(Integer seed){
        this.name = "Jigglypuff";

        this.sing = new Sing();
        this.pound = new Pound();
        this.disarmingVoice = new DisarmingVoice();
        this.hyperVoice = new HyperVoice();

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
        skills.add(this.sing);
        skills.add(this.pound);
        skills.add(this.disarmingVoice);
        skills.add(this.hyperVoice);
        skills.add(this.block);
        skills.add(this.endure);
        skills.add(this.protect);

        return skills;
    }
}

