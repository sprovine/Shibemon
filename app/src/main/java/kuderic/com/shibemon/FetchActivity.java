package kuderic.com.shibemon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FetchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch);

        //Play button listener
        Button playButton = findViewById(R.id.backFetchButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FetchActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });
    }

}
