package kuderic.com.shibemon;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class BackgroundMusic extends Service {
    private static final String LOGCAT = null;
    MediaPlayer objPlayer;

    public void onCreate(){
        super.onCreate();
        Log.d(LOGCAT, "Service Started!");
        objPlayer = MediaPlayer.create(this,R.raw.chiptune);
        int maxVolume = 100;
        int currVolume = 50;
        float log1 = (float)(Math.log(maxVolume - currVolume)/Math.log(maxVolume));
        objPlayer.setVolume(1 - log1, 1 - log1);
        objPlayer.setLooping(true);
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        objPlayer.start();
        Log.d(LOGCAT, "Media Player started!");
        if(objPlayer.isLooping() != true){
            Log.d(LOGCAT, "Problem in Playing Audio");
        }
        return Service.START_STICKY;
    }

    public void onStop(){
        objPlayer.stop();
        objPlayer.release();
    }

    public void onPause(){
        objPlayer.stop();
        objPlayer.release();
    }

    public void onDestroy(){
        objPlayer.stop();
        objPlayer.release();
    }
    @Override
    public IBinder onBind(Intent objIndent) {
        return null;
    }
}