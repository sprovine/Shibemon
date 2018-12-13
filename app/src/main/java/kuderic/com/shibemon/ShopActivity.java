package kuderic.com.shibemon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class ShopActivity extends Activity {
    private Shiba shiba1;
    private Shiba shiba2;
    private int cost1;
    private int cost2;
    private int cost3;
    private int cost4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        shiba1 = (Shiba) bundle.getSerializable("SHIBA1");
        shiba2 = (Shiba) bundle.getSerializable("SHIBA2");

        Button playButton = findViewById(R.id.backShopButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, BattleActivity.class);
                bundleShibas(intent);
                startActivity(intent);
            }
        });


        Button fetchButton = findViewById(R.id.buttonShopFetch);
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, FetchActivity.class);
                bundleShibas(intent);
                startActivity(intent);
            }
        });
        fetchButton.setVisibility(View.INVISIBLE);

        updateUI();
    }

    private void updateUI() {
        updateCosts();

        Button buttonMove1 = findViewById(R.id.buttonHeal);
        buttonMove1.setText("HEAL (" + cost1 + " GOLD)");
        buttonMove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cost1 == 0) {
                    displayToast(shiba1.getName() + " doesn't need healing dumbie.");
                } else if (shiba1.gold >= cost1){
                    shiba1.gold -= cost1;
                    shiba1.setCurrentHealth(shiba1.getMaxHealth());
                    displayToast(shiba1.getName() + " has been healed!");
                    updateUI();
                } else {
                    displayToast("You don't have enough gold!");
                }
            }
        });

        Button buttonMove2 = findViewById(R.id.buttonAttackUp);
        buttonMove2.setText("ATTACK+ (" + cost2 + " GOLD)");
        buttonMove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shiba1.gold >= cost2){
                    shiba1.gold -= cost2;
                    shiba1.attack += 4;
                    displayToast(shiba1.getName() + "has gained 4 attack!");
                    updateUI();
                } else {
                    displayToast("You don't have enough gold!");
                }
            }
        });

        Button buttonMove3 = findViewById(R.id.buttonDefenseUp);
        buttonMove3.setText("DEFENCE+ (" + cost3 + " GOLD)");
        buttonMove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shiba1.gold >= cost3){
                    shiba1.gold -= cost3;
                    shiba1.defence += 3;
                    displayToast(shiba1.getName() + "has gained 3 defence!");
                    updateUI();
                } else {
                    displayToast("You don't have enough gold!");
                }
            }
        });

        Button buttonMove4 = findViewById(R.id.buttonMaxHPUp);
        buttonMove4.setText("MAX HP+ (" + cost4 + " GOLD)");
        buttonMove4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shiba1.gold >= cost4){
                    shiba1.increaseMaxHealth(10);
                    shiba1.gold -= cost4;
                    displayToast(shiba1.getName() + "has gained 10 Max HP!");
                    updateUI();
                } else {
                    displayToast("You don't have enough gold!");
                }
            }
        });

        TextView tv = findViewById(R.id.gold);
        tv.setText("GOLD: " + shiba1.gold);
    }

    private void updateCosts() {
        cost1 = (shiba1.getMaxHealth() - shiba1.getCurrentHealth());
        cost2 = (int) (shiba1.attack * 2 * .75);
        cost3 = (int) (shiba1.defence * 3 *.75);
        cost4 = (int) (shiba1.getMaxHealth() * .60);
    }

    private void bundleShibas(Intent intent) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("SHIBA1", shiba1);
        bundle.putSerializable("SHIBA2", shiba2);
        intent.putExtras(bundle);
    }

    protected void displayToast(String message ) {
        Toast toast = Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

}
