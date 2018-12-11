package kuderic.com.shibemon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BattleActivity extends Activity {
    Shiba shiba1;
    Shiba shiba2;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        shiba1 = new Shiba();
        shiba2 = new Shiba();

        System.out.println("Shiba names: ");
        System.out.println(shiba1.getName());
        System.out.println(shiba2.getName());


        TextView actionPanel = findViewById(R.id.actionPanel);
        actionPanel.setText("What will " + shiba1.getName() + " do?");
        TextView shiba1Name = findViewById(R.id.shiba1Name);
        shiba1Name.setText(shiba1.getName());
        TextView shiba1Level = findViewById(R.id.shiba1Level);
        shiba1Level.setText("Lv" + shiba1.getLevel());
        TextView shiba1HP = findViewById(R.id.shiba1HP);
        shiba1HP.setText("HP: " + shiba1.getCurrentHealth() + "/" + shiba1.getMaxHealth());


        Button buttonMove1 = findViewById(R.id.move1);
        buttonMove1.setText(shiba1.getMoves()[0].getName());
        buttonMove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shiba1Attack(shiba1.getMoves()[1]);
            }
        });
        Button buttonMove2 = findViewById(R.id.move2);
        buttonMove2.setText(shiba1.getMoves()[1].getName());
        buttonMove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shiba1Attack(shiba1.getMoves()[1]);
            }
        });
        Button buttonMove3 = findViewById(R.id.move3);
        buttonMove3.setText(shiba1.getMoves()[2].getName());
        buttonMove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shiba1Attack(shiba1.getMoves()[2]);
            }
        });
        Button buttonMove4 = findViewById(R.id.move4);
        buttonMove4.setText(shiba1.getMoves()[3].getName());
        buttonMove4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shiba1Attack(shiba1.getMoves()[3]);
            }
        });
    }

    public void shiba1Attack(Shiba.Move move) {
        int damage = shiba1.random(10, 20) * shiba1.getLevel() / 10;

        System.out.println("Shiba 1 uses " + move.getName() + "!");
        Toast toast = Toast.makeText(getApplicationContext(), "Shiba 1 uses " + move.getName() + "!",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
        shiba2.setCurrentHealth(shiba2.getCurrentHealth() - damage);
        System.out.println("Shiba 2's health is " + shiba2.getCurrentHealth());
    }

    protected void updateUI() {

    }
}
