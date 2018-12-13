package kuderic.com.shibemon;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, BackgroundMusic.class));
        /*
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.doodlebob);
        int maxVolume = 100;
        int currVolume = 0;
        float log1 = (float)(Math.log(maxVolume - currVolume)/Math.log(maxVolume));
        mediaPlayer.setVolume(1 - log1, 1 - log1);
        mediaPlayer.start();*/

        //Play button listener
        Button playButton = findViewById(R.id.backButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Battle intent received");
                Intent intent = new Intent(MainActivity.this, BattleActivity.class);
                startActivity(intent);
            }
        });
    }

}
