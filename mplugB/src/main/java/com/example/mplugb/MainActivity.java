package com.example.mplugb;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.pluglibrary.DLIntent;
import com.example.pluglibrary.DPlugManager;
import com.example.pluglibrary.activity.BasePlugActivity;

/**
 * create by guofeng
 * date on 2019-09-26
 */
public class MainActivity extends BasePlugActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.mStartService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DLIntent intent = new DLIntent();
                intent.packageName = "com.example.mplugb";
                intent.mPlugnClass = "com.example.mplugb.PlugBService";
                startServices(MainActivity.this, intent);
            }
        });

        findViewById(R.id.mBindService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DLIntent intent = new DLIntent();
                intent.packageName = "com.example.mplugb";
                intent.mPlugnClass = "com.example.mplugb.PlugBService";
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
