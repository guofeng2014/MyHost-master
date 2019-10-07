package com.example.pluglibrary.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.pluglibrary.DPlugManager;
import com.example.pluglibrary.PlugPackage;

import androidx.annotation.Nullable;
import dalvik.system.DexClassLoader;

import static com.example.pluglibrary.DLConstants.INTENT_CLASS_NAME;
import static com.example.pluglibrary.DLConstants.INTENT_PACKAGE_NAME;

/**
 * Created by guofeng
 * on 2019-09-28.
 * 代理启动service的
 * <p>
 * 多个service问题
 */
public class ProxyService extends Service {
    //插件的包名称
    private String plugPackageName;
    //插件的类名称,当前代理要调用插件的生命周期
    private String plugClassName;
    //代理class的对象
    private IService plugService;

    //启动代理服务
    public static void startService(Context context, String plugPackageName, String plugClassName) {
        Intent intent = null;
        try {
            intent = new Intent(context, ProxyService.class);
            intent.putExtra(INTENT_PACKAGE_NAME, plugPackageName);
            intent.putExtra(INTENT_CLASS_NAME, plugClassName);
            context.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //启动代理服务
    public static void onBindService(Context context, String plugPackageName, String plugClassName, ServiceConnection connection, int flag) {
        Intent intent = new Intent(context, ProxyService.class);
        intent.putExtra(INTENT_PACKAGE_NAME, plugPackageName);
        intent.putExtra(INTENT_CLASS_NAME, plugClassName);
        context.bindService(intent, connection, flag);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        plugPackageName = intent.getStringExtra(INTENT_PACKAGE_NAME);
        plugClassName = intent.getStringExtra(INTENT_CLASS_NAME);
        //根据classLoader初始化插件接口对象
        initIServiceObject();
        //调用插件的生命周期方法
        if (plugService != null) {
            plugService.onCreate();
            plugService.onStartCommand(intent, flags, startId);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //初始化IService接口对象
    private void initIServiceObject() {
        try {
            PlugPackage plugInfo = DPlugManager.getInstance().getDexPlugPackage(plugPackageName);
            DexClassLoader classLoader = plugInfo.classLoader;
            Class<?> aClass = classLoader.loadClass(plugClassName);
            Object object = aClass.newInstance();
            if (object instanceof IService) {
                plugService = (IService) object;
                plugService.onAttach(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        plugPackageName = intent.getStringExtra(INTENT_PACKAGE_NAME);
        plugClassName = intent.getStringExtra(INTENT_CLASS_NAME);
        //根据classLoader初始化插件接口对象
        initIServiceObject();
        //调用插件的生命周期方法
        if (plugService != null) {
            plugService.onCreate();
            plugService.onBind(intent);
        }
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (plugService != null) {
            plugService.onUnbind(intent);
        }
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        if (plugService != null) {
            plugService.onDestroy();
        }
        super.onDestroy();
    }
}
