package com.example.pluglibrary;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import dalvik.system.DexClassLoader;

/**
 * create by guofeng
 * date on 2019-09-24
 */
public class PlugPackage {

    public String packageName;

    public DexClassLoader classLoader;

    public String defaultActivity;

    public AssetManager assetManager;

    public Resources resources;

    public PackageInfo packageInfo;

    public PlugPackage() {
    }

}
