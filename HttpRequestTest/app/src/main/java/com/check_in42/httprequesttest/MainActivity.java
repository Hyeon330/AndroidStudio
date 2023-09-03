package com.check_in42.httprequesttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String urlStr = editText.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request(urlStr);
                    }
                }).start();
            }
        });
    }

    public void request(String urlStr) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL("https://api.42check-in.kr/tablet/reservations");

            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            if (conn != null) {
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                int resCode = conn.getResponseCode();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                while (true) {
                    line = br.readLine();
                    if (line == null)
                        break ;

                    sb.append(line + "\n");
                }
                br.close();
                conn.disconnect();
            }
        } catch (Exception e) {
            println("예외 발생함: " + e.toString());
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ArrayList<ConferenceRoomDTO> conferenceRoomDTOS = null;
        try {
            conferenceRoomDTOS = mapper.readValue(sb.toString(), new TypeReference<ArrayList<ConferenceRoomDTO>>() {});
        } catch (Exception e) {
            Log.e("MappingFailed", e.getMessage());
        }

        println("응답-> " + sb.toString());
        if (conferenceRoomDTOS != null) {
            println(conferenceRoomDTOS.get(0).getIntraId());
        } else {
            println("null");
        }
    }

    public void println(final String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.append(data + "\n");
            }
        });
    }
}