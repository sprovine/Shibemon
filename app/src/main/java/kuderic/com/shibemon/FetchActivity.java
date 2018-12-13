package kuderic.com.shibemon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

public class FetchActivity extends Activity {
    private Shiba shiba1;
    private Shiba shiba2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        shiba1 = (Shiba) bundle.getSerializable("SHIBA1");
        shiba2 = (Shiba) bundle.getSerializable("SHIBA2");

        //Play button listener
        Button playButton = findViewById(R.id.backFetchButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FetchActivity.this, ShopActivity.class);
                bundleShibas(intent);
                startActivity(intent);
            }
        });
    }

    private void bundleShibas(Intent intent) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("SHIBA1", shiba1);
        bundle.putSerializable("SHIBA2", shiba2);
        intent.putExtras(bundle);
    }
}
