package cs6310.Pokemon;
import cs6310.RandomGenerator;
import cs6310.Skill.Skill;
import cs6310.Skill.AttackSkill.Attack;
import cs6310.Skill.AttackSkill.Tackle2;
import cs6310.Skill.AttackSkill.WaterGun;
import cs6310.Skill.AttackSkill.HydroPump;

import cs6310.Skill.DefendSkill.Block;
import cs6310.Skill.DefendSkill.Endure;
import cs6310.Skill.DefendSkill.Protect;

import java.util.ArrayList;
import java.util.Collections;


public class Squirtle extends Pokemon {
    private Skill attack;
    private Skill tackle;
    private Skill watergun;
    private Skill hydroPump;

    private Skill block;
    private Skill endure;
    private Skill protect;


    public Squirtle(Integer seed){Setup(seed);}
    public Squirtle(){Setup(null);}
    private void Setup(Integer seed){
        this.name = "Squirtle";

        this.attack = new Attack();
        this.tackle = new Tackle2();
        this.watergun = new WaterGun();
        this.hydroPump = new HydroPump();

        this.block=new Block();
        this.endure=new Endure();
        this.protect=new Protect();

        this.skills = initializeSkills();
        if (seed != null) {
            this.seed = seed;
            this.randomGenerator = new RandomGenerator(this.seed, this.hit_point, this.skills);
        }
    }

    @Override
    public ArrayList<Skill> initializeSkills() {
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(this.tackle);
        skills.add(this.watergun);
        skills.add(this.hydroPump);
        skills.add(this.attack);

        skills.add(this.block);
        skills.add(this.endure);
        skills.add(this.protect);

        return skills;
    }


}
