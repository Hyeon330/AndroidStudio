package org.techtown.socket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    TextView sendTextView;
    TextView serverTextView;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        sendTextView = findViewById(R.id.sendTextView);
        serverTextView = findViewById(R.id.serverTextView);

        Button button = findViewById(R.id.sendButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String data = editText.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        send(data);
                    }
                }).start();
            }
        });

        Button button2 = findViewById(R.id.startServer);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startServer();
                    }
                }).start();
            }
        });
    }

    public void printClientLog(final String data) {
        Log.d("MainActivity", data);

        handler.post(new Runnable() {
            @Override
            public void run() {
                sendTextView.append(data + "\n");
            }
        });
    }

    public void printServerLog(final String data) {
        Log.d("MainActivity", data);

        handler.post(new Runnable() {
            @Override
            public void run() {
                serverTextView.append(data + "\n");
            }
        });
    }

    public void send(String data) {
        try {
            int portNumber = 5001;
            Socket sock = new Socket("localhost", portNumber);
            printClientLog("소켓 연결!!");

            ObjectOutputStream outputStream = new ObjectOutputStream(sock.getOutputStream());
            outputStream.writeObject(data);
            outputStream.flush();
            printClientLog("데이터 전송!!");

            ObjectInputStream inputStream = new ObjectInputStream(sock.getInputStream());
            printClientLog("서버로부터 받음: " + inputStream.readObject());
            sock.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        try {
            int portNumber = 5001;

            ServerSocket server = new ServerSocket(portNumber);
            printServerLog("서버 시작함: " + portNumber);

            while (true) {
                Socket sock = server.accept();
                InetAddress clientHost = sock.getLocalAddress();
                int clientPort = sock.getPort();
                printServerLog("클라이언트 연결됨: " + clientHost + " : " + clientPort);

                ObjectInputStream inputStream = new ObjectInputStream(sock.getInputStream());
                Object obj = inputStream.readObject();
                printServerLog("데이터 받음: " + obj);

                ObjectOutputStream outputStream = new ObjectOutputStream(sock.getOutputStream());
                outputStream.writeObject(obj + "from Server.");
                outputStream.flush();
                printServerLog("데이터 보냄.");

                sock.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}