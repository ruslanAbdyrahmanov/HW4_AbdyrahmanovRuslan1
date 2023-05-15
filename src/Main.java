import java.util.Objects;
import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 45;
    public static String bossDefence;
    public static int[] heroesHealth = {270, 260, 250, 340,};
    public static int[] heroesDamage = {10, 15, 20, 0,};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Healer",};
    public static int roundNumber = 0;

    public static void main(String[] args) {
        printStatistics();

        while (!isGameFinished()) {
            playRound();
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHit();
        medicHealing();
        printStatistics();
    }

    public static void medicHealing() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && heroesHealth[3] > 0) {
                if (heroesHealth[3] < 100) {
                    heroesHealth[3] = heroesHealth[3] - bossDamage;
                }
                else {
                    Random random = new Random();
                    int randomHealth = random.nextInt(40-10) + 10;
                    heroesHealth[(int)Math.floor(Math.random() * heroesHealth.length)]  += randomHealth;
                    System.out.println("Медик вылечил героя" + randomHealth);
                    break;
                }
            }
            else if (heroesHealth[3] <= 0){
                heroesHealth[3] = 0;
                heroesHealth[i] -= bossDamage;
            }
        }
    }
    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                heroesHealth[i] = Math.max(heroesHealth[i] - bossDamage, 0);
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (Objects.equals(heroesAttackType[i], bossDefence)) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2;
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                bossHealth = Math.max(bossHealth - damage, 0);
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int j : heroesHealth) {
            if (j > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " -------------");
        System.out.println("Boss health: " + bossHealth + " damage: " +
                bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            if(heroesHealth[i]<=0){
                heroesHealth[i]=0;
            System.out.println(heroesAttackType[i] +
                    " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
        else {
                System.out.println(heroesAttackType[i] +
                        " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
        }
    }
}