package kuderic.com.shibemon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class BattleActivity extends Activity {
    private static boolean canAttack = true;
    private Shiba shiba1;
    private Shiba shiba2;
    final static int hpBarWidth = 125; //in dp. use dpToPx() to find pixel ratio
    final static double speedMultiplier = (double) 1.5; //how much faster everything plays
    final static double timeMultiplier = 1 / speedMultiplier; //inverse multiplier for time actions take


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        System.out.println("time multiplier is " + timeMultiplier);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        Button shopButton = findViewById(R.id.buttonBattleShop);
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
                startActivity(intent);
            }
        });

        PictureReader.setContext(this);
        shiba1 = new Shiba(15);
        shiba2 = createShiba2(14);
        updateUI();
        updateImages();
        updateMoves();
    }

    protected void shibaAttack(final Shiba shiba, final Shiba.Move move) {
        if (!canAttack) {
            System.out.println("cant attack");
            return;
        }
        playBark();

        displayToast(shiba.getName() + " uses " + move.getName() + "!", true);

        int damage = 0;
        double damageMultiplier = 1;

        if (shiba == shiba1) {
            damage = calculateDamage(shiba, shiba2, move);
            damageMultiplier = calculateMultiplier(move.getType(), shiba2.type);

            shiba2.setCurrentHealth(shiba2.getCurrentHealth() - damage);

            Animation anim = AnimationUtils.
                    loadAnimation(getApplicationContext(), R.anim.shiba1attackshiba2);
            anim.setDuration((long) (anim.getDuration() * timeMultiplier));
            findViewById(R.id.shiba1).startAnimation(anim);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation anim = AnimationUtils.
                            loadAnimation(getApplicationContext(), R.anim.shiba1attackshiba2part2);
                    anim.setDuration((long) (anim.getDuration() * timeMultiplier));
                    findViewById(R.id.shiba1).startAnimation(anim);
                }
            }, (long) (1200 * timeMultiplier));
        } else if (shiba == shiba2) {
            damage = calculateDamage(shiba, shiba1, move);
            damageMultiplier = calculateMultiplier(move.getType(), shiba2.type);

            shiba1.setCurrentHealth(shiba1.getCurrentHealth() - damage);

            Animation anim = AnimationUtils.
                    loadAnimation(getApplicationContext(), R.anim.shiba2attackshiba1);
            anim.setDuration((long) (anim.getDuration() * timeMultiplier));
            findViewById(R.id.shiba2).startAnimation(anim);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation anim = AnimationUtils.
                            loadAnimation(getApplicationContext(), R.anim.shiba2attackshiba1part2);
                    anim.setDuration((long) (anim.getDuration() * timeMultiplier));
                    findViewById(R.id.shiba2).startAnimation(anim);
                }
            }, (long) (1200 * timeMultiplier));
        }

        final double finalDamageMultiplier = damageMultiplier;
        final int finalDamage = damage;
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                String message = "It does " + finalDamage + " damage!";
                if (finalDamageMultiplier == .75) {
                    message += "\nIt wasn't very effective.";
                } else if (finalDamageMultiplier == 1.25) {
                    message += "\nIt was super effective!";
                }
                displayToast(message, true);
                updateUI();
                if (shiba2.getCurrentHealth() == 0) {
                    shiba2Died();
                } else if (shiba1.getCurrentHealth() == 0) {
                    playWhimper();
                    Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
                    startActivity(intent);
                } else if (shiba == shiba1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            canAttack = true;
                            int rand = Shiba.random(0, 3);
                            shibaAttack(shiba2, shiba2.getMoves()[rand]);
                        }
                    }, (long) (2000 * timeMultiplier));
                }
            }
        },(long) (2000 * timeMultiplier));
    }

    private int calculateDamage(Shiba shiba1, Shiba shiba2, Shiba.Move move) {
        int damage = Shiba.random(90, 100) * shiba1.attack / 100;
        double damageMultiplier = calculateMultiplier(move.getType(), shiba2.type);
        int calc = (int) ((damage - (int) (shiba2.defence * .8)) * damageMultiplier);
        if (calc < 0) {
            return 0;
        }
        return calc;
    }

    private double calculateMultiplier(String type, String type1) {
        if (type.equals("fire")) {
            if (type1.equals("water")) {
                return 0.75;
            } else if (type1.equals("grass")) {
                return 1.25;
            }
        } else if (type.equals("water")) {
            if (type1.equals("grass")) {
                return 0.75;
            } else if (type1.equals("fire")) {
                return 1.25;
            }
        } else if (type.equals("grass")) {
            if (type1.equals("fire")) {
                return 0.75;
            } else if (type1.equals("water")) {
                return 1.25;
            }
        }
        return 1;
    }

    protected void displayToast(String message, boolean wait) {
        Toast toast = Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
        if (wait) {
            canAttack = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    canAttack = true;
                }
            }, (long) (2000 * timeMultiplier));
        }
    }


    @SuppressLint("SetTextI18n")
    protected void updateUI() {

        TextView actionPanel = findViewById(R.id.actionPanelText);
        actionPanel.setText("What will " + shiba1.getName() + " do?");

        TextView shiba1Name = findViewById(R.id.shiba1Name);
        shiba1Name.setText(shiba1.getName());
        TextView shiba1Level = findViewById(R.id.shiba1Level);
        shiba1Level.setText("Lv" + shiba1.getLevel());
        TextView shiba1HP = findViewById(R.id.shiba1HP);
        shiba1HP.setText("HP: " + shiba1.getCurrentHealth() + "/" + shiba1.getMaxHealth());
        View shiba1HPBarGreen = findViewById(R.id.shiba1HPBarGreen);
        shiba1HPBarGreen.getLayoutParams().width = dpToPx(hpBarWidth) *
                shiba1.getCurrentHealth() / shiba1.getMaxHealth();
        shiba1HPBarGreen.requestLayout();

        LinearLayout shiba1Info = findViewById(R.id.shiba1Info);
        setBackgroundType(shiba1Info, shiba1);

        TextView shiba2Name = findViewById(R.id.shiba2Name);
        shiba2Name.setText(shiba2.getName());
        TextView shiba2Level = findViewById(R.id.shiba2Level);
        shiba2Level.setText("Lv" + shiba2.getLevel());
        TextView shiba2HP = findViewById(R.id.shiba2HP);
        shiba2HP.setText("HP: " + shiba2.getCurrentHealth() + "/" + shiba2.getMaxHealth());
        View shiba2HPBarGreen = findViewById(R.id.shiba2HPBarGreen);
        shiba2HPBarGreen.getLayoutParams().width = dpToPx(hpBarWidth) *
                shiba2.getCurrentHealth() / shiba2.getMaxHealth();
        shiba2HPBarGreen.requestLayout();
        
        LinearLayout shiba2Info = findViewById(R.id.shiba2Info);
        setBackgroundType(shiba2Info, shiba2);
    }

    private void updateImages() {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .skipMemoryCache(true);

        Glide.with(this).load(shiba1.getPicture()).apply(options)
                .into((ImageView) findViewById(R.id.shiba1));
        Glide.with(this).load(shiba2.getPicture()).apply(options)
                .into((ImageView) findViewById(R.id.shiba2));
    }

    private void updateMoves() {
        Button buttonMove1 = findViewById(R.id.move1);
        buttonMove1.setText(shiba1.getMoves()[0].getName());
        setBackgroundType(buttonMove1, shiba1.getMoves()[0]);
        buttonMove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shibaAttack(shiba1, shiba1.getMoves()[0]);
            }
        });
        Button buttonMove2 = findViewById(R.id.move2);
        buttonMove2.setText(shiba1.getMoves()[1].getName());
        setBackgroundType(buttonMove2, shiba1.getMoves()[1]);
        buttonMove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shibaAttack(shiba1, shiba1.getMoves()[1]);
            }
        });
        Button buttonMove3 = findViewById(R.id.move3);
        buttonMove3.setText(shiba1.getMoves()[2].getName());
        setBackgroundType(buttonMove3, shiba1.getMoves()[2]);
        buttonMove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shibaAttack(shiba1, shiba1.getMoves()[2]);
            }
        });
        Button buttonMove4 = findViewById(R.id.move4);
        buttonMove4.setText(shiba1.getMoves()[3].getName());
        setBackgroundType(buttonMove4, shiba1.getMoves()[3]);
        buttonMove4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shibaAttack(shiba1, shiba1.getMoves()[3]);
            }
        });
    }

    public void setBackgroundType(Button button, Shiba.Move move) {
        if (move.getType().equals("fire")) {
            button.setBackgroundResource(R.drawable.fire);
        } else if (move.getType().equals("water")) {
            button.setBackgroundResource(R.drawable.water);
        } else if (move.getType().equals("grass")) {
            button.setBackgroundResource(R.drawable.grass);
        }
    }
    public void setBackgroundType(LinearLayout layout, Shiba shiba) {
        if (shiba.type.equals("fire")) {
            layout.setBackgroundResource(R.drawable.fire);
        } else if (shiba.type.equals("water")) {
            layout.setBackgroundResource(R.drawable.water);
        } else if (shiba.type.equals("grass")) {
            layout.setBackgroundResource(R.drawable.grass);
        }
    }

    public int dpToPx(int dp) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    private Shiba createShiba2(int level) {
        return new Shiba(level);
    }

    private void shiba2Died() {

        playWhimper();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                displayToast(shiba2.getName() + " has fainted.",
                        true);
            }
        }, (long) (2000 * timeMultiplier));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                shiba2 = createShiba2(10);
                updateImages();
                updateUI();
                displayToast("A wild " + shiba2.getName() + " has appeared!",
                        true);
            }
        }, (long) (4000 * timeMultiplier));
    }

    private void playWhimper() {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.whimper);

        int startFrom = Shiba.random(0, 24000);
        int endAfter = Shiba.random(22, 36) / 10 * 1000;

        mp.seekTo(startFrom);
        mp.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mp != null) {
                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                    mp.reset();
                }
            }
        }, endAfter);
    }

    private void playBark() {
        System.out.println("playing bark");
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.bark1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mp.start();
            }
        }, 700);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mp != null) {
                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                    mp.reset();
                }
            }
        }, 2000);
    }
}
