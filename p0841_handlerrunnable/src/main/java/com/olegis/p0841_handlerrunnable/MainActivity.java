package com.olegis.p0841_handlerrunnable;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    final String TAG = "myLogs";
    final int max = 100;

    Handler h;
    ProgressBar pbCount;
    TextView tvInfo;
    CheckBox chbInfo;
    int cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        h = new Handler();
        pbCount = findViewById(R.id.pbCount);
        pbCount.setMax(max);
        pbCount.setProgress(0);

        tvInfo = findViewById(R.id.tvInfo);

        chbInfo = findViewById(R.id.chbInfo);
        chbInfo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvInfo.setVisibility(View.VISIBLE);
                    //показываем информацию
                    h.post(showInfo);
                } else {
                    tvInfo.setVisibility(View.GONE);
                }
            }
        });

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (cnt = 1; cnt < max; cnt++) {
                        TimeUnit.MILLISECONDS.sleep(100);
                        //обновляем ProgressBar
                        h.post(updateProgress);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    //обновление ProgressBar
    Runnable updateProgress = new Runnable() {
        @Override
        public void run() {
            pbCount.setProgress(cnt);
        }
    };

    //показ информации
    Runnable showInfo = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "showInfo");
            tvInfo.setText("Count =" + cnt);
            //планирует сам себя через 1000 мсек
            h.postDelayed(showInfo, 1000);
        }
    };
}
