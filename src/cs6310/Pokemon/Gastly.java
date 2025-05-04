package cs6310.Pokemon;
import cs6310.RandomGenerator;
import cs6310.Skill.Skill;
import cs6310.Skill.AttackSkill.Hypnosis;
import cs6310.Skill.AttackSkill.Lick;
import cs6310.Skill.AttackSkill.NightShade;
import cs6310.Skill.AttackSkill.ShadowBall;

import cs6310.Skill.DefendSkill.Block;
import cs6310.Skill.DefendSkill.Endure;
import cs6310.Skill.DefendSkill.Protect;

import java.util.ArrayList;

public class Gastly extends Pokemon {
    private Skill hypnosis;
    private Skill lick;
    private Skill nightShade;
    private Skill shadowBall;

    private Skill block;
    private Skill endure;
    private Skill protect;

    public Gastly(Integer seed){Setup(seed);}
    public Gastly(){Setup(null);}
    private void Setup(Integer seed){
        this.name = "Gastly";

        this.hypnosis = new Hypnosis();
        this.lick = new Lick();
        this.nightShade = new NightShade();
        this.shadowBall = new ShadowBall();

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
        skills.add(this.hypnosis);
        skills.add(this.lick);
        skills.add(this.nightShade);
        skills.add(this.shadowBall);
        skills.add(this.block);
        skills.add(this.endure);
        skills.add(this.protect);

        return skills;
    }
}
