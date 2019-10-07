package com.example.mplugb;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.pluglibrary.service.BasePlugService;

import androidx.annotation.Nullable;

/**
 * Created by guofeng
 * on 2019-09-28.
 */

public class PlugBService extends BasePlugService {

    private static final String TAG = "PlugBService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        //输出插件B的日志
        loopLog();
        return super.onStartCommand(intent, flags, startId);
    }

    //循环输出插件B的日志
    private void loopLog() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(4000);
                        Log.d(TAG, "run: 循环输出插件B的日志");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
