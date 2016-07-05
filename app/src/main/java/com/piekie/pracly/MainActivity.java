package com.piekie.pracly;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private int connectState;
    private TextView tv_connected;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_connected = (TextView) findViewById(R.id.tv_connected);

        try {
             mSocket = IO.socket("https://still-brook-88697.herokuapp.com");

            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_connected.setText(getResources().getString(R.string.tv_connect_connected));
                        }
                    });
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_connected.setText(getResources().getString(R.string.tv_connect_disconnected));
                        }
                    });
                }
            });

            mSocket.connect();
        } catch (URISyntaxException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
