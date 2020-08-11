package com.olegis.p0911_asynctaskrotate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    final String TAG = "myLogs";

    MyTask mt;
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "create MainActivity: " + this.hashCode());

        tvInfo = findViewById(R.id.tvInfo);

        mt = (MyTask) getLastNonConfigurationInstance();
        if (mt == null) {
            mt = new MyTask();
            mt.execute();
        }

        //передаем в MyTask ссылку на текущее MainActivity
        mt.link(this);

        Log.d(TAG, "create MyTask" + mt.hashCode());
    }


    public final Object onRetainNonConfigurationInstance() {

        //удаляем из MyTask ссылку на старое MainActivity
        mt.unLink();
        return mt;
    }

    static class MyTask extends AsyncTask<String, Integer, Void> {

        MainActivity activity;

        //получаем ссылку на MainActivity
        void link(MainActivity act) {
            activity = act;
        }

        //обновляем ссылку
        void unLink() {
            activity = null;
        }

        @Override
        protected Void doInBackground(String... voids) {
            try {
                for (int i = 1; i <= 10; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    publishProgress(i);
                    Log.d("myLogs", "i = " + i + ", MyTask: " + this.hashCode() + ", MainActivity: " + activity.hashCode());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            activity.tvInfo.setText("i = " + values[0]);
        }
    }
}
