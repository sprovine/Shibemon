package kuderic.com.shibemon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button playButton = (Button)findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),BattleActivity.class));
            }
        });
    }

    protected void startBattle(View view) {
        System.out.println("Battle intent received");
        // Note: Activity.this gets the content of Activity
        Intent intent = new Intent(MainActivity.this, BattleActivity.class);
        startActivity(intent);
    }

}
