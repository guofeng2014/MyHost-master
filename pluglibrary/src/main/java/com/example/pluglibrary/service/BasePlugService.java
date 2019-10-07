package com.example.pluglibrary.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.pluglibrary.DLIntent;
import com.example.pluglibrary.DPlugManager;

import androidx.annotation.Nullable;

/**
 * Created by guofeng
 * on 2019-09-28.
 */

public class BasePlugService extends Service implements IService {

    /**
     * 代理的service对象,
     * 插件单独运行不需要，
     * 作为插件运行 获得上下文需要从proxyService获得，插件不具备获得上下文
     */
    protected Service proxyService;

    @Override
    public void onAttach(Service proxyService) {
        this.proxyService = proxyService;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 0;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    @Override
    public void onDestroy() {

    }
}
