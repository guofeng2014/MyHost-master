package com.example.pluglibrary;

import android.app.Activity;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.example.pluglibrary.activity.ProxyActivity;
import com.example.pluglibrary.service.ProxyService;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexClassLoader;

/**
 * create by guofeng
 * date on 2019-09-25
 */
public class DPlugManager {

    private final Map<String, PlugPackage> cache = new HashMap<>();

    private DPlugManager() {
    }

    private static DPlugManager instance = new DPlugManager();

    public static DPlugManager getInstance() {
        return instance;
    }


    //初始化所有插件 转化为PlugPackage封装类
    public void initAllPlugInfo(Activity activity, List<String> apkList) {
        for (String apk : apkList) {
            //package信息
            PackageInfo packageInfo = getPackageInfo(activity, apk);
            if (packageInfo == null) continue;
            //包名称作为key,一个APK就是一个完整的应用 只有唯一的一个包名称
            PlugPackage plugPackage = cache.get(packageInfo.packageName);
            if (plugPackage == null) {
                plugPackage = new PlugPackage();
                plugPackage.classLoader = createDexClassLoader(activity, apk);
                plugPackage.assetManager = createAssetManager(apk);
                plugPackage.packageInfo = packageInfo;
                plugPackage.defaultActivity = getDefaultActivity(plugPackage.packageInfo);
                plugPackage.packageName = plugPackage.packageInfo.packageName;
                plugPackage.resources = createResource(activity, plugPackage.assetManager);
                cache.put(packageInfo.packageName, plugPackage);
            }
        }
    }


    //获得所有插件
    public Map<String, PlugPackage> getCache() {
        return cache;
    }


    //启动跳转到代理Activity
    public void startActivity(Context context, DLIntent intent) {
        ProxyActivity.jump(context, intent.packageName, intent.mPlugnClass);
    }

    //startService启动跳转到代理的service
    public void startService(Context context, DLIntent intent) {
        ProxyService.startService(context, intent.packageName, intent.mPlugnClass);
    }

    //bindService启动跳转到代理的service
    public void onBindService(Context context, DLIntent intent, ServiceConnection connection, int flag) {
        ProxyService.onBindService(context, intent.packageName, intent.mPlugnClass, connection, flag);
    }

    //获得APK的插件信息
    public PlugPackage getDexPlugPackage(String packageName) {
        return cache.get(packageName);
    }

    public String getDefaultActivity(PackageInfo packageInfo) {
        if (packageInfo != null) {
            if (packageInfo.activities != null && packageInfo.activities.length > 0) {
                return packageInfo.activities[0].name;
            }
        }
        return "";
    }

    private Resources createResource(Activity activity, AssetManager assetManager) {
        Resources superRes = activity.getResources();
        Resources resources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        return resources;
    }

    private AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, apkPath);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PackageInfo getPackageInfo(Activity activity, String apkPath) {
        PackageManager pm = activity.getPackageManager();
        return pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
    }

    private DexClassLoader createDexClassLoader(Activity activity, String apkPath) {
        File dexOutputDir = activity.getDir("dex", Context.MODE_PRIVATE);
        return new DexClassLoader(apkPath, dexOutputDir.toString(), null, activity.getClassLoader());
    }


}
