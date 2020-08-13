package com.olegis.p0951_servicebackpendingintent;

import android.app.PendingIntent;
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

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "MyService onCreate");
        es = Executors.newFixedThreadPool(2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MyService onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "MyService onStartCommand");

        int time = intent.getIntExtra(MainActivity.PARAM_TIME, 1);
        PendingIntent pi = intent.getParcelableExtra(MainActivity.PARAM_PINTENT);

        MyRun mr = new MyRun(time, startId, pi);
        es.execute(mr);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class MyRun implements Runnable {

        int time;
        int startId;
        PendingIntent pi;

        public MyRun(int time, int startId, PendingIntent pi) {
            this.time = time;
            this.startId = startId;
            this.pi = pi;
            Log.d(TAG, "MyRun#" + startId + " create");
        }

        @Override
        public void run() {
            Log.d(TAG, "MyRun#" + startId + " start, time = " + time);
            try {
                //сообщаем о старте задачи
                pi.send(MainActivity.STATUS_START);

                //начинаем выполение задачи
                TimeUnit.SECONDS.sleep(time);

                //сообщаем об окончании задачи
                Intent intent = new Intent().putExtra(MainActivity.PARAM_RESULT, time * 100);
                pi.send(MyService.this, MainActivity.STATUS_FINISH, intent);
            }catch (InterruptedException e){
                e.printStackTrace();
            }catch (PendingIntent.CanceledException e){
                e.printStackTrace();
            }
            stop();
        }

        private void stop() {
            Log.d(TAG, "MyRun#" + startId + " end, stopSelfResult(" + startId + ") = " + stopSelfResult(startId));
        }
    }
}
