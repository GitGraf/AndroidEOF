package com.example.streamreadingtest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    Button sendBtn;
    EditText serverIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        serverIp = findViewById(R.id.serverIP);
        sendBtn = findViewById(R.id.send_it);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String host = serverIp.getText().toString();
                for(int i = 1024; i <= 102400; i+= 1024) {
                    runSocketClient(host, i);
                    try {
                        // Add some delay so logs are written in order
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void runSocketClient(final String host, final int numberOfBytes) {
        Runnable client = new Runnable() {
            @Override
            public void run() {
                try (Socket socket = new Socket(host, 12345)) {
                    DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
                    DataInputStream dataIn = new DataInputStream(socket.getInputStream());
                    dataOut.writeInt(numberOfBytes);
                    dataOut.flush();

                    // Insert breakpoint (or Thread.sleep(100)) here
                    // and observe that EOFExceptions become more rare.

                    byte[] data = new byte[numberOfBytes];
                    dataIn.readFully(data);
                    Log.i("Test", data.length + " bytes: successfully read");
                } catch(IOException ex) {
                    Log.e("Test", numberOfBytes + " bytes: " + ex.getClass().getSimpleName());
                }
            }
        };

        new Thread(client).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
