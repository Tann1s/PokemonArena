package cs6310.Skill.AttackSkill;

import cs6310.Skill.Skill;

public abstract class AttackSkill extends Skill {

    public final String action = "Attack";

    protected AttackSkill(String name, int damage) {
       super(name, damage);
    }
}


