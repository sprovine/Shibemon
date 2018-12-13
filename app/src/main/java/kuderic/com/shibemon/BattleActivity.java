package kuderic.com.shibemon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
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


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);


        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Main page intent received");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        PictureReader.setContext(this);
        shiba1 = createShiba();
        shiba2 = createShiba();

        updateUI();

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

    protected void shibaAttack(final Shiba shiba, final Shiba.Move move) {
        System.out.println(shiba.getName() + " is attacking.");
        if (!canAttack) {
            System.out.println("cant attack");
            return;
        }
        final int finalDamage = shiba.random(15, 25) * shiba.getLevel() / 10;

        displayToast(shiba.getName() + " uses " + move.getName() + "!", true);

        if (shiba == shiba1) {
            shiba2.setCurrentHealth(shiba2.getCurrentHealth() - finalDamage);
            System.out.println(shiba2.getName() + " health is " + shiba2.getCurrentHealth());

            findViewById(R.id.shiba1).startAnimation(AnimationUtils.
                    loadAnimation(getApplicationContext(), R.anim.shiba1attackshiba2));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.shiba1).startAnimation(AnimationUtils.
                            loadAnimation(getApplicationContext(), R.anim.shiba1attackshiba2part2));
                }
            }, 1200);
        } else {
            shiba1.setCurrentHealth(shiba1.getCurrentHealth() - finalDamage);
            System.out.println(shiba1.getName() + " health is " + shiba1.getCurrentHealth());

            findViewById(R.id.shiba2).startAnimation(AnimationUtils.
                    loadAnimation(getApplicationContext(), R.anim.shiba2attackshiba1));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.shiba2).startAnimation(AnimationUtils.
                            loadAnimation(getApplicationContext(), R.anim.shiba2attackshiba1part2));
                }
            }, 1200);
        }


        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                displayToast("It does " + finalDamage + " damage!", true);
                updateUI();
                if (shiba2.getCurrentHealth() == 0) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            displayToast(shiba2.getName() + " has fainted.",
                                    true);
                        }
                    }, 2000);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            shiba2 = createShiba();
                            updateUI();
                            displayToast("A wild " + shiba2.getName() + " has appeared!",
                                    true);
                        }
                    }, 4000);
                    return;
                }
                if (shiba1.getCurrentHealth() == 0) {
                    Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
                    startActivity(intent);
                }
                if (shiba == shiba1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            canAttack = true;
                            int rand = Shiba.random(0, 3);
                            shibaAttack(shiba2, shiba2.getMoves()[rand]);
                        }
                    }, 2000);
                }
            }
        },2000);
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
            }, 2000);
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

        System.out.println("picture 1 url");
        System.out.println(shiba1.getPicture());

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

    public int dpToPx(int dp) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    private Shiba createShiba() {
        return new Shiba();
    }
}
