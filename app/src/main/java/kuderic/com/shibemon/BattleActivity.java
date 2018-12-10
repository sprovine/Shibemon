package kuderic.com.shibemon;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class BattleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        Shiba shiba1 = new Shiba();
        Shiba shiba2 = new Shiba();
        System.out.println("Shiba names: ");
        System.out.println(shiba1.getName());
        System.out.println(shiba2.getName());


        TextView actionView = findViewById(R.id.actionView);
        actionView.setText("What will " + shiba1.getName() + " do?");


        Button buttonMove1 = findViewById(R.id.move1);
        buttonMove1.setText(shiba1.getMoves()[0].getName());
        Button buttonMove2 = findViewById(R.id.move2);
        buttonMove2.setText(shiba1.getMoves()[1].getName());
        Button buttonMove3 = findViewById(R.id.move3);
        buttonMove3.setText(shiba1.getMoves()[2].getName());
        Button buttonMove4 = findViewById(R.id.move4);
        buttonMove4.setText(shiba1.getMoves()[3].getName());
    }
}
