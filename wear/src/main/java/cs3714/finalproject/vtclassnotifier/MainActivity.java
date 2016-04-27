package cs3714.finalproject.vtclassnotifier;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    //    CommHandler handler;
    private TextView classText;
    private TextView availableText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener()
        {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                availableText = (TextView) stub.findViewById(R.id.availableText);
//        classText = (TextView) findViewById(R.id.classText);
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//                vibrator.vibrate(400);
                long[] vibrationPattern = {0, 500, 50, 300};
                //-1 - don't repeat
                final int indexInPatternToRepeat = -1;
                vibrator.vibrate(vibrationPattern, indexInPatternToRepeat);
                Log.d("vibrate","pls");

            }
        });
    }
}



