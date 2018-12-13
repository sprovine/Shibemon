package kuderic.com.shibemon;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PictureReader {
    private static Context context;
    public static void setContext(Context toContext) {
        context = toContext;
    }
    public static String generatePicture() throws IOException {
        InputStream is = context.getResources().openRawResource(R.raw.pictures);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        ArrayList<String> lines = new ArrayList<>();
        String line = reader.readLine();

        while (line != null) {
            //System.out.println(line);
            lines.add(line);
            line = reader.readLine();
        }
        reader.close();

        int rand = Shiba.random(0, lines.size() - 1);
        //System.out.println(lines.get(rand));
        return lines.get(rand);
    }
}
