package kuderic.com.shibemon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

public class ShopActivity extends Activity {
    private Shiba shiba1;
    private Shiba shiba2;

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
    }

    private void bundleShibas(Intent intent) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("SHIBA1", (Serializable) shiba1);
        bundle.putSerializable("SHIBA2", (Serializable) shiba2);
        intent.putExtras(bundle);
    }
}
