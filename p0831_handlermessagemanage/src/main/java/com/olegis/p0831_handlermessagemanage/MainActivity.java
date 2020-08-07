package com.olegis.p0831_handlermessagemanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    final String TAG = "myLogs";

    Handler h;

    Handler.Callback hc = new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            Log.d(TAG, "what =" + message.what);
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        h = new Handler(hc);
        sendMessages();
    }

    void sendMessages(){
        Log.d(TAG, "send messages");
        h.sendEmptyMessageDelayed(1, 1000);
        h.sendEmptyMessageDelayed(2, 2000);
        h.sendEmptyMessageDelayed(3, 3000);
    }
}
