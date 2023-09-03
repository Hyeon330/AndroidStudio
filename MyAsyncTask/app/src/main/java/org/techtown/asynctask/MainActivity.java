package org.techtown.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        BackgroundTask task = new BackgroundTask();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.execute();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.cancel();
            }
        });

    }

    class BackgroundTask extends AsyncTask<Integer, Integer, Integer> {

        // 쓰레드로 실행되기 전 상태
        @Override
        protected void onPreExecute() {
            value = 0;
            progressBar.setProgress(value);
        }

        // 쓰레드로 실행된 후 상태
        @Override
        protected void onPostExecute(Integer integer) {
            progressBar.setProgress(0);
        }

        // 중간중간 ui를 업데이트하고싶을 때
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0].intValue());
        }

        // 쓰레드로 실행된 상태
        @Override
        protected Integer doInBackground(Integer... integers) {
            while (isCancelled() == false) {
                value += 1;

                if (value >= 100) {
                    break;
                }

                // 여기서 onProgressUpdate가 실행 됨
                publishProgress(value);

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {}
            }

            // 작업이 모두 끝나면 onPostExecute 로 넘어감
            return value;
        }
    }
}