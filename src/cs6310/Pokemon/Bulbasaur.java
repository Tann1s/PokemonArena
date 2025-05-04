package cs6310.Pokemon;
import cs6310.RandomGenerator;
import cs6310.Skill.Skill;
import cs6310.Skill.AttackSkill.Tackle1;
import cs6310.Skill.AttackSkill.VineWhip;
import cs6310.Skill.AttackSkill.RazorLeaf;
import cs6310.Skill.AttackSkill.LeafStorm;

import cs6310.Skill.DefendSkill.Block;
import cs6310.Skill.DefendSkill.Endure;
import cs6310.Skill.DefendSkill.Protect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bulbasaur extends Pokemon {
    private Skill tackle;
    private Skill vineWhip;
    private Skill razorLeaf;
    private Skill leafStorm;

    private Skill block;
    private Skill endure;
    private Skill protect;


    public Bulbasaur(Integer seed){Setup(seed);}
    public Bulbasaur(){Setup(null);}
    private void Setup(Integer seed){
        this.name = "Bulbasaur";

        this.tackle = new Tackle1();
        this.vineWhip = new VineWhip();
        this.razorLeaf = new RazorLeaf();
        this.leafStorm = new LeafStorm();

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
        skills.add(this.tackle);
        skills.add(this.vineWhip);
        skills.add(this.razorLeaf);
        skills.add(this.leafStorm);
        skills.add(this.block);
        skills.add(this.endure);
        skills.add(this.protect);
        return skills;
    }


}
