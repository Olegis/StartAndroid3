package com.olegis.p0901_asynctaskstatys;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    MyTask mt;
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = findViewById(R.id.tvInfo);
    }

    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                startTask();
                break;
            case R.id.btnStatus:
                showStatus();
                break;
            default:
                break;
        }
    }

    private void startTask() {
        mt = new MyTask();
        mt.execute();
    }

    private void showStatus() {
        if (mt != null) {
            if (mt.isCancelled()) {
                Toast.makeText(this, "CANCELLED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, mt.getStatus().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvInfo.setText("Begin");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                for (int i = 0; i < 5; i++) {
                    if (isCancelled()) return null;
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tvInfo.setText("End");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            tvInfo.setText("Cancel");
        }
    }
}
