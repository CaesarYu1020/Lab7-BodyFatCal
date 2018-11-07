package com.example.caesaryu.lab7_2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    protected EditText etH, etW;
    protected RadioButton boy, girl;
    protected RadioGroup rg;
    protected Button btn;
    protected TextView stdWeight, bodyFat;
    int gender = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etH = findViewById(R.id.etH);
        etW = findViewById(R.id.etW);
        boy = findViewById(R.id.boy);
        girl = findViewById(R.id.girl);

        rg = findViewById(R.id.radioGroup);
        btn = findViewById(R.id.button);
        stdWeight = findViewById(R.id.stdWeight);
        bodyFat = findViewById(R.id.bodyFat);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (rg.getCheckedRadioButtonId()) {
                    case R.id.boy:
                        gender = 1;
                        break;
                    case R.id.girl:
                        gender = 2;
                        break;
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAsyncTask();
            }
        });
    }

    private void runAsyncTask() {
        new AsyncTask<Void, Integer, Boolean>() {
            private ProgressDialog dialog = new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.setMessage("計算中");
                dialog.setCancelable(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.show();
            }

            @Override
            protected Boolean doInBackground(Void... voids) {

                int progress = 0;
                while (progress <= 100) {
                    try {
                        Thread.sleep(50);
                        publishProgress(Integer.valueOf(progress));
                        progress++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                dialog.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                dialog.dismiss();
                double cal_Tall = Double.parseDouble(etH.getText().toString());
                double cal_Weight = Double.parseDouble(etW.getText().toString());
                double cal_stdW = 0;
                double cal_bodyFat = 0;
                if (gender == 1) {
                    cal_stdW = 22 * cal_Tall / 100 * cal_Tall / 100;
                    cal_bodyFat = (cal_Weight - (0.88 * cal_stdW)) / cal_Weight * 100;

                }
                if (gender == 2) {
                    cal_stdW = 22 * cal_Tall / 100 * cal_Tall / 100;
                    cal_bodyFat = (cal_Weight - (0.82 * cal_stdW)) / cal_Weight * 100;

                }
                stdWeight.setText(String.format("%.2f",cal_stdW));
                bodyFat.setText(String.format("%.2f",cal_bodyFat));

            }
        }.execute();
    }
}
