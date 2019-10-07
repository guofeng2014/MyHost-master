package com.example.pluglibrary;

import android.content.Intent;

/**
 * create by guofeng
 * date on 2019-09-25
 */
public class DLIntent extends Intent {

    public String packageName;

    public String mPlugnClass;

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setmPlugnClass(String mPlugnClass) {
        this.mPlugnClass = mPlugnClass;
    }
}
