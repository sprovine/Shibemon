package kuderic.com.shibemon;

import android.graphics.Picture;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Math;
import java.util.ArrayList;

public class Shiba {
    private String name;
    private String gender;
    private String picture;
    private int level;
    private int maxHealth;
    private int currentHealth;
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

    public Shiba() {
        int rand = random(0, names.length - 1);
        name = names[rand];

        if (random(0, 1) == 1) {
            gender = "male";
        } else {
            gender = "female";
        }

        level = random(35, 65);

        maxHealth = level * random(6, 7);
        currentHealth = maxHealth;

        for (int i = 0; i < moves.length; i++) {
            moves[i] = new Move();
            //Check if name is taken. If taken, try again.
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

    public class Move {
        private String name;
        private String[] names = {"Pound", "Double Slap", "Scratch", "Stomp", "Headbutt", "Slam",
                "Bite", "Growl", "Lick", "Flail", "Fake out", "Charge", "Yawn", "Tickle",
                "Mega Punch", "Nuzzle", "Tail Whip", "Roll Out"};

        public String getName() {
            return name;
        }

        public Move() {
            int rand = random(0, names.length - 1);
            name = names[rand];
        }
    }
}