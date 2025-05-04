package cs6310.Pokemon;
import cs6310.RandomGenerator;
import cs6310.Skill.Skill;
import cs6310.Skill.AttackSkill.Attack;
import cs6310.Skill.AttackSkill.Scratch;
import cs6310.Skill.AttackSkill.Ember;
import cs6310.Skill.AttackSkill.Flamethrower;

import java.util.ArrayList;
import cs6310.Skill.DefendSkill.Block;
import cs6310.Skill.DefendSkill.Endure;
import cs6310.Skill.DefendSkill.Protect;

public class Charmander extends Pokemon {
    private Skill attack;
    private Skill scratch;
    private Skill ember;
    private Skill flamethrower;

    private Skill block;
    private Skill endure;
    private Skill protect;

    public Charmander(Integer seed){Setup(seed);}
    public Charmander(){Setup(null);}
    private void Setup(Integer seed){
        this.name = "Charmander";

        this.attack = new Attack();
        this.scratch = new Scratch();
        this.ember = new Ember();
        this.flamethrower = new Flamethrower();

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
        skills.add(this.attack);
        skills.add(this.scratch);
        skills.add(this.ember);
        skills.add(this.flamethrower);
        skills.add(this.block);
        skills.add(this.endure);
        skills.add(this.protect);

        return skills;
    }




}
