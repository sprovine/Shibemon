package kuderic.com.shibemon;

import java.lang.Math;

public class Shiba {
    private String name;
    private String gender;
    private String picture;
    private int level;
    private int maxHealth;
    private int currentHealth;
    private Move[] moves = new Move[4];

    //Array of names to choose from
    private static String[] names = {"Bella", "Lucy", "Daisy", "Luna", "Lola", "Sadie", "Molly",
            "Bailey", "Maggie", "Sophie", "Chloe", "Stella", "Lily", "Penny", "Zoey", "Coco",
            "Roxy", "Gracie", "Mia", "Nala", "Ruby", "Rosie", "Ellie", "Abby", "Zoe", "Piper",
            "Ginger", "Lilly", "Lulu", "Riley", "Sasha", "Lexi", "Pepper", "Emma", "Layla", "Maya",
            "Izzy", "Lady", "Annie", "Olive", "Harley", "Belle", "Dixie", "Millie", "Willow",
            "Princess", "Charlie", "Maddie", "Kona", "Cali", "Ella", "Winnie", "Roxie", "Marley",
            "Cookie", "Hazel", "Scout", "Athena", "Callie", "Phoebe", "Honey", "Angel", "Dakota",
            "Minnie", "Holly", "Missy", "Sugar", "Shelby", "Nova", "Leia", "Josie", "Penelope",
            "Ava", "Gigi", "Peanut", "Fiona", "Cleo", "Jasmine", "Sandy", "Mocha", "Taco"};


    public Shiba() {
        int rand = random(0, names.length - 1);
        name = names[rand];

        if (random(0, 1) == 1) {
            gender = "male";
        } else {
            gender = "female";
        }

        level = random(1, 100);

        maxHealth = level * random(1, 10);
        currentHealth = maxHealth;

        for (int i = 0; i < moves.length; i++) {
            moves[i] = new Move();
        }
    }

    public void setCurrentHealth(int toHealth) {
        currentHealth = toHealth;
        if (currentHealth < 0) {
            currentHealth = 0;
        }
    }

    public String getName() {
        return name;
    }
    public String getPicture() {
        return picture;
    }
    public String getGender() {
        return gender;
    }
    public int getLevel() {
        return level;
    }
    public int getCurrentHealth() {
        return currentHealth;
    }
    public Move[] getMoves() {
        return moves;
    }

    private int random(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public class Move {
        private String name;
        private String[] names = {"Pound", "Double Slap", "Scratch", "Stomp", "Headbutt", "Slam",
                "Bite", "Growl", "Lick"};

        public String getName() {
            return name;
        }

        public Move() {
            int rand = random(0, names.length - 1);
            name = names[rand];
        }
    }
}