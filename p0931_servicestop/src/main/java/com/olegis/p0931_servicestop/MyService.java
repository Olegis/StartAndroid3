package com.olegis.p0931_servicestop;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    final String TAG = "myLogs";
    ExecutorService es;
    Object someRes;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "MyService onCreate");
        es = Executors.newFixedThreadPool(1);
        someRes = new Object();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MyService onDestroy");
        someRes = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "MyService onStartCommand");
        int time = intent.getIntExtra("time", 1);
        MyRun mr = new MyRun(time, startId);
        es.execute(mr);
        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class MyRun implements Runnable{

        int time;
        int startId;

        public MyRun(int time, int startId){
            this.time = time;
            this.startId = startId;
            Log.d(TAG, "MyRun#" + startId +  " create");
        }

        @Override
        public void run() {
            Log.d(TAG, "MyRun#" + startId + " start, time = " + time);
            try {
                TimeUnit.SECONDS.sleep(time);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            try {
                Log.d(TAG, "MyRun#" + startId + " someRes = " + someRes.getClass());
            }catch (NullPointerException e){
                Log.d(TAG, "MyRun#" + startId + " error, null pointer");
            }
            stop();
        }

        private void stop() {
            Log.d(TAG, "MyRun#" + startId + "end, stopSelf(" + startId + ")");
            stopSelf(startId);
        }
    }
}
