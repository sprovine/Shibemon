package kuderic.com.shibemon;

import android.app.Activity;
import android.os.Bundle;

public class BattleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Shiba shiba1 = new Shiba();
        Shiba shiba2 = new Shiba();
        System.out.println(shiba1.getName());
        System.out.println(shiba2.getName());
    }
}
