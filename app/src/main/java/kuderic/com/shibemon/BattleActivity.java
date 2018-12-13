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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class BattleActivity extends Activity {
    private static boolean canAttack = true;
    private Shiba shiba1;
    private Shiba shiba2;
    final static int hpBarWidth = 125; //in dp. use dpToPx() to find pixel ratio
    final static double speedMultiplier = (double) 3.0; //how much faster everything plays
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
        shiba1 = createShiba(20);
        shiba2 = createShiba(10);
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
        final int finalDamage = shiba.random(15, 25) * shiba.getLevel() / 10;

        displayToast(shiba.getName() + " uses " + move.getName() + "!", true);

        if (shiba == shiba1) {
            shiba2.setCurrentHealth(shiba2.getCurrentHealth() - finalDamage);

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
        }
        if (shiba == shiba2) {
            shiba1.setCurrentHealth(shiba1.getCurrentHealth() - finalDamage);

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

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                displayToast("It does " + finalDamage + " damage!", true);
                updateUI();
                if (shiba2.getCurrentHealth() == 0) {
                    shiba2Died();
                } else if (shiba1.getCurrentHealth() == 0) {
                    playWhimper();
                    Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
                    startActivity(intent);
                } else if (shiba == shiba1) {//Let shiba2 attack
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
        buttonMove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shibaAttack(shiba1, shiba1.getMoves()[0]);
            }
        });
        Button buttonMove2 = findViewById(R.id.move2);
        buttonMove2.setText(shiba1.getMoves()[1].getName());
        buttonMove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shibaAttack(shiba1, shiba1.getMoves()[1]);
            }
        });
        Button buttonMove3 = findViewById(R.id.move3);
        buttonMove3.setText(shiba1.getMoves()[2].getName());
        buttonMove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shibaAttack(shiba1, shiba1.getMoves()[2]);
            }
        });
        Button buttonMove4 = findViewById(R.id.move4);
        buttonMove4.setText(shiba1.getMoves()[3].getName());
        buttonMove4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shibaAttack(shiba1, shiba1.getMoves()[3]);
            }
        });
    }

    public int dpToPx(int dp) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    private Shiba createShiba(int level) {
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
                shiba2 = createShiba(10);
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
