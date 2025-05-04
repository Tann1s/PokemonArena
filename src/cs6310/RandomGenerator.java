package cs6310;

import cs6310.Skill.Skill;
import cs6310.Skill.AttackSkill.AttackSkill;
import cs6310.Skill.DefendSkill.DefendSkill;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class RandomGenerator {

    private int seed;
    private int hit_point=25;
    private int max_hit_point=25;
    //skill list is based on Pokemon definition
    private ArrayList<Skill> skill_list;
    final private ArrayList<Skill> attack_skill_list=new ArrayList<>();
    final private ArrayList<Skill> defend_skill_list=new ArrayList<>();
    private Random random = new Random();
    //current action should be either 'Attack' or 'Defend'


    //constructor with defualt max_hit_point(25)
     public RandomGenerator(int seed, int hit_point,ArrayList<Skill> skill_list){
         this.seed=seed;
         this.random.setSeed(this.seed);
         this.hit_point=hit_point;
         this.skill_list=new ArrayList<>(skill_list);
         this.randomGeneratorConfig();
     }

     //public functions
     //getter functions


    public Skill getSkill() {
         return this.selectSkill();
    }

    public void setHitPoint(int hit_point){
         this.hit_point=hit_point;
    }


    /*Private methods: used for generating currentAction and currentSkill based on constructor*/
    //RandomGenerator Configuration
    private void randomGeneratorConfig(){
        this.initAttackAndDefendList();
    }

     private Skill selectSkill(){
          if(this.selectAction()=="Defend"){
              if(!this.defend_skill_list.isEmpty()){
                 int index = this.random.nextInt(defend_skill_list.size());
                  return defend_skill_list.get(index);
              }
          }else{
              if(!this.attack_skill_list.isEmpty()){
                  int index = this.random.nextInt(attack_skill_list.size());
                  return attack_skill_list.get(index);

              }
          }
          return null;
    }

    //used for select current action and value assignment for currentAction
    private String selectAction(){
        double ratio = (double) this.hit_point / this.max_hit_point;
        int likelihoodToAttack;

        if (ratio >= 0.7) {
            likelihoodToAttack = 2; // 80% likelihood, attacking if random number is 2-9
        } else if (ratio >= 0.3) {
            likelihoodToAttack = 5; // 50% likelihood, attacking if random number is 5-9
        } else {
            likelihoodToAttack = 7; // 30% likelihood, attacking if random number is 7-9
        }

       int randomNumber = random.nextInt(10); // Generates a number between 0 and 9

        if (randomNumber >= likelihoodToAttack) {
            return "Attack";
        } else {
            return "Defend";
        }
    }


    //used for init attack and defend list and sorting purpose
    private void initAttackAndDefendList(){
        for (Skill skill : this.skill_list) {
            if(skill instanceof AttackSkill){
                this.attack_skill_list.add(skill);
            }
            if(skill instanceof DefendSkill){
                this.defend_skill_list.add(skill);
            }
        }
        this.sortAttackAndDefendList();
    }

    private void sortAttackAndDefendList(){
        Collections.sort(attack_skill_list);
        Collections.sort(defend_skill_list);
    }


}
