package cs6310.Skill.DefendSkill;

import cs6310.Skill.Skill;

public abstract class DefendSkill extends Skill {
    public final String action = "Defend";

    protected DefendSkill(String name, int damage) {
        super(name, damage);
    }
}


