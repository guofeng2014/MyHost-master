package com.example.pluglibrary.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by guofeng
 * on 2019-09-28.
 */

public interface IService {

    void onAttach(Service proxyService);

    void onCreate();

    int onStartCommand(Intent intent, int flags, int startId);

    IBinder onBind(Intent intent);

    boolean onUnbind(Intent intent);

    void onDestroy();
}
