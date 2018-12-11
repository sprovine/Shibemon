package kuderic.com.shibemon;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

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

        picture = generatePicture();
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
        return (int)(Math.random() * range) + min;
    }

    private String generatePicture() {
        int rand = random(0, pictures.length - 1);
        return pictures[rand];
    }

    private final String[] pictures = {
            "https://cdn.shibe.online/shibes/8754184c28c1b87f03f4879155ea74cbc0abe6c6.jpg",
            "https://cdn.shibe.online/shibes/080c7e9757b0f04b7d323d17aceaa213c7f0477a.jpg",
            "https://cdn.shibe.online/shibes/73ac3769e561542243be0418aa11b6f769757725.jpg",
            "https://cdn.shibe.online/shibes/22cec5fcfe9488ad02fe2a1349981c2fd7b76012.jpg",
            "https://cdn.shibe.online/shibes/4ed3b895a8ce4cdbe242fa6df7feedb5eff929e8.jpg",
            "https://cdn.shibe.online/shibes/88a4bf0270e5ce10ee7f438b084a8bdeb351aa9f.jpg",
            "https://cdn.shibe.online/shibes/7046f956569e4c5a72f00b2bf7724723cc36ad0b.jpg",
            "https://cdn.shibe.online/shibes/1af220726ed4c65ac32ea6225fef41029678c82f.jpg",
            "https://cdn.shibe.online/shibes/245a00644be72bb6c2bcc72c37e7f1d660e72873.jpg",
            "https://cdn.shibe.online/shibes/da6ebd1c3c8d578e628cdcf971e5843beae92069.jpg",
            "https://cdn.shibe.online/shibes/244f646c8c14ec43394bb46b006bede658a64948.jpg",
            "https://cdn.shibe.online/shibes/4c779def35f0ca6d99f85a40468aa038d8968f51.jpg",
            "https://cdn.shibe.online/shibes/867dc8043f5b401ca768610395f9a902.jpg",
            "https://cdn.shibe.online/shibes/a706c9a7cc57714ae67c5e4c7111afc10186786e.jpg",
            "https://cdn.shibe.online/shibes/951831082ff26db118e1113727eae2230d5ea729.jpg",
            "https://cdn.shibe.online/shibes/eae2b0ac9df4bbf06b03c4e8faeac003306c2867.jpg",
            "https://cdn.shibe.online/shibes/1fc93d4db2b09a105d056e47db90c9609541a679.jpg",
            "https://cdn.shibe.online/shibes/1eb4af9c79fa11197e9fe6f9abc875eacfb131fc.jpg",
            "https://cdn.shibe.online/shibes/48b25a82c00ed52ec94ce910144831df1e152147.jpg",
            "https://cdn.shibe.online/shibes/26f85d7a82efc8784117bdf602c460bf5158ab41.jpg"};

    public class Move {
        private String name;
        private String[] names = {"Pound", "Double Slap", "Scratch", "Stomp", "Headbutt", "Slam",
                "Bite", "Growl", "Lick", "Flail", "Fake out", "Charge", "Yawn", "Tickle",
                "Mega Punch", "Nuzzle", "Tail Whip", "Quick Attack", "Roll Out"};

        public String getName() {
            return name;
        }

        public Move() {
            int rand = random(0, names.length - 1);
            name = names[rand];
        }
    }
}