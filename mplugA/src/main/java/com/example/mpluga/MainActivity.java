package com.example.mpluga;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.pluglibrary.DLIntent;
import com.example.pluglibrary.activity.BasePlugActivity;

/**
 * create by guofeng
 * date on 2019-09-25
 */
public class MainActivity extends BasePlugActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.mTextStartService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DLIntent intent = new DLIntent();
                intent.mPlugnClass = "com.example.mpluga.PlugAService";
                intent.packageName = "com.example.mpluga";
                startServices(MainActivity.this, intent);
            }
        });

        findViewById(R.id.mTextBindService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DLIntent intent = new DLIntent();
                intent.mPlugnClass = "com.example.mpluga.PlugAService";
                intent.packageName = "com.example.mpluga";
                bindService(MainActivity.this, intent, connection, 0);
            }
        });
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

}
