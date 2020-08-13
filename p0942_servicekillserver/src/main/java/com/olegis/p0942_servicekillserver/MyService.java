package com.olegis.p0942_servicekillserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    final String TAG = "myLogs";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "MyService onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MyService onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "MyService onStartCommand");
        readFlags(flags);
        MyRun mr = new MyRun(startId);
        new Thread(mr).start();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void readFlags(int flags) {
        if ((flags & START_FLAG_REDELIVERY) == START_FLAG_REDELIVERY)
            Log.d(TAG, "START_FLAG_REDELIVERY");
        if ((flags & START_FLAG_RETRY) == START_FLAG_RETRY)
            Log.d(TAG, "START_FLAG_RETRY");


    }

    private class MyRun implements Runnable{

        int startId;

        public MyRun(int startId){
            this.startId = startId;
            Log.d(TAG, "MyRun#" + startId + " create");
        }

        @Override
        public void run() {
            Log.d(TAG, "MyRun#" + startId + " start");
            try {
                TimeUnit.SECONDS.sleep(15);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            stop();
        }

        private void stop() {
            Log.d(TAG, "MyRun#" + startId + "end, stopSelfResult(" + startId + ") = " + stopSelfResult(startId));
        }
    }
}
