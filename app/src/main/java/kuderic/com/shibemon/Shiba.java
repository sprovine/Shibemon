package kuderic.com.shibemon;

import android.graphics.Picture;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.lang.Math;

public class Shiba implements Serializable {
    private String name;
    private String picture;
    private int level;
    private int maxHealth;
    private int currentHealth;
    public int maxExp;
    public int currentExp;
    public int attack;
    public int defence;
    public int gold;
    public String type;
    private Move[] moves = new Move[4];

    //Array of names to choose from
    private final String[] names = {"Bella", "Lucy", "Daisy", "Luna", "Lola", "Sadie", "Molly",
            "Bailey", "Maggie", "Sophie", "Chloe", "Stella", "Lily", "Penny", "Zoey", "Coco",
            "Roxy", "Gracie", "Mia", "Nala", "Ruby", "Rosie", "Ellie", "Abby", "Zoe", "Piper",
            "Ginger", "Lilly", "Lulu", "Riley", "Sasha", "Lexi", "Pepper", "Emma", "Layla", "Maya",
            "Izzy", "Lady", "Annie", "Olive", "Harley", "Belle", "Dixie", "Millie", "Willow",
            "Princess", "Charlie", "Maddie", "Kona", "Cali", "Ella", "Winnie", "Roxie", "Marley",
            "Cookie", "Hazel", "Scout", "Athena", "Callie", "Phoebe", "Honey", "Angel", "Dakota",
            "Minnie", "Holly", "Missy", "Sugar", "Shelby", "Nova", "Leia", "Josie", "Penelope",
            "Ava", "Gigi", "Peanut", "Fiona", "Cleo", "Jasmine", "Sandy", "Mocha", "Taco"};

    public Shiba(int toLevel) {
        int rand = random(0, names.length - 1);
        name = names[rand];

        level = toLevel;

        currentHealth = maxHealth = 20;
        for (int i = 0; i < level; i++) {
            increaseStats();
        }


        maxExp = 15 * level;
        currentExp = 0;

        for (int i = 0; i < moves.length; i++) {
            moves[i] = new Move();
            //Check if move is already taken. If taken, try again.
            for (int j = 0; j < i; j++) {
                if (moves[i].getName().equals(moves[j].getName())) {
                    i--;
                    break;
                }
            }
        }

        String urlString = "https://api.iextrading.com/1.0/stock/AAPL/batch?types=quote\n";
        System.out.println("Requesting Json from Shibe.online");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                urlString,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        System.out.println("Response received");
                        System.out.println(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                System.out.println(error.toString());
            }
        });


        rand = random(1, 3);
        if (rand == 1) {
            type = "fire";
        }
        if (rand == 2) {
            type = "grass";
        }
        if (rand == 3) {
            type = "water";
        }

        try {
            picture = PictureReader.generatePicture();
        } catch (IOException e) {
            System.out.println("Couldn't find picture file");
        }
    }

    public void setCurrentHealth(int toHealth) {
        currentHealth = toHealth;
        if (currentHealth < 0) {
            currentHealth = 0;
        }
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }

    public void setMaxHealth(int toHealth) {
        maxHealth = toHealth;
    }

    public void increaseMaxHealth(int toHealth) {
        maxHealth += toHealth;
        increaseCurrentHealth(toHealth);
    }

    public void increaseCurrentHealth(int toHealth) {
        setCurrentHealth(currentHealth + toHealth);
    }

    public boolean levelUp() {
        boolean bool = false;
        while (currentExp >= maxExp) {
            bool = true;
            level++;
            increaseStats();
            currentExp -= maxExp;
            maxExp += 15;
        }
        return bool;
    }

    private void increaseStats() {
        increaseMaxHealth(8);
        attack += random(5, 6);
        defence += random(2, 3);
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public int getLevel() {
        return level;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public Move[] getMoves() {
        return moves;
    }

    public static int random(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    public class Move implements Serializable{
        private String name;
        private String type;

        private String[] names = {"Pound", "Double Slap", "Scratch", "Stomp", "Headbutt", "Slam",
                "Bite", "Growl", "Lick", "Flail", "Fake out", "Charge", "Yawn", "Tickle",
                "Mega Punch", "Nuzzle", "Tail Whip", "Roll Out"};

        public String getName() {
            return name;
        }
        public String getType() {
            return type;
        }

        public Move() {
            int rand = random(0, names.length - 1);
            name = names[rand];

            rand = random(1, 3);
            if (rand == 1) {
                type = "fire";
            }
            if (rand == 2) {
                type = "grass";
            }
            if (rand == 3) {
                type = "water";
            }
        }
    }
}