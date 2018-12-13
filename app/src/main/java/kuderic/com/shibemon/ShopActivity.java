package kuderic.com.shibemon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShopActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Button playButton = findViewById(R.id.backShopButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, BattleActivity.class);
                startActivity(intent);
            }
        });

        Button fetchButton = findViewById(R.id.buttonShopFetch);
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, FetchActivity.class);
                startActivity(intent);
            }
        });
    }
}
