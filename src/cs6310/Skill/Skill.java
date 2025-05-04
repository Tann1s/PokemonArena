package cs6310.Skill;

public class Skill implements Comparable<Skill>{
    public final String name;
    public final int damage;
    public Skill(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }
    // Getters
    public String getName() {
        return name;
    }

    public Integer getDamage() {
        return damage;
    }

    @Override
    public int compareTo(Skill skill) {
                return Integer.compare(this.damage, skill.damage);
            }
        }



