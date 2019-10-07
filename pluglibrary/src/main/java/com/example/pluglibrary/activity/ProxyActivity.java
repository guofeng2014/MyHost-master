package com.example.pluglibrary.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.pluglibrary.DLConstants;
import com.example.pluglibrary.DPlugManager;
import com.example.pluglibrary.PlugPackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Constructor;

import m.plug.itf.IActivity;

import static com.example.pluglibrary.activity.BasePlugActivity.FROM;
import static com.example.pluglibrary.DLConstants.FROM_EXTERN;

/**
 * create by guofeng
 * date on 2019-09-24
 *
 * 生命周期问题
 * 资源加载问题
 * 启动模式问题
 */
public class ProxyActivity extends Activity {


    private IActivity plugItf;//插件包的接口对象
    private PlugPackage plugInfo;//插件封装类


    public static void jump(Context context, String packageName, String className) {
        Intent intent = new Intent(context, ProxyActivity.class);
        intent.putExtra(DLConstants.INTENT_PACKAGE_NAME, packageName);
        intent.putExtra(DLConstants.INTENT_CLASS_NAME, className);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            //初始化Plug信息
            initPlugInfo();
            //获得插件包的接口对象
            initPlugItf();
            //调用插件的OnCreate方法
            invokePlugOnCreate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //初始化Plug信息
    private void initPlugInfo() {
        plugInfo = DPlugManager.getInstance().getDexPlugPackage(getMPackageName());
    }

    //获得包名称
    private String getMPackageName() {
        return getIntent().getStringExtra(DLConstants.INTENT_PACKAGE_NAME);
    }


    //调用插件的OnCreate方法
    private void invokePlugOnCreate() {
        Bundle savedInstanceState;
        if (plugItf != null) {
            savedInstanceState = new Bundle();
            savedInstanceState.putInt(FROM, FROM_EXTERN);
            plugItf.onCreate(savedInstanceState);
        }
    }

    //获得启动的class
    private String getLauncherClass() {
        String activityName = getIntent().getStringExtra(DLConstants.INTENT_CLASS_NAME);
        if (TextUtils.isEmpty(activityName)) {
            return plugInfo.defaultActivity;
        }
        return activityName;
    }

    //获得插件包的接口对象
    private void initPlugItf() {
        try {
            Class<?> aClass = plugInfo.classLoader.loadClass(getLauncherClass());
            Constructor<?> localConstructor = aClass.getConstructor(new Class[]{});
            Object object = localConstructor.newInstance();
            if (object instanceof IActivity) {//拿到插件的接口对象
                plugItf = (IActivity) object;
                //把当前对象传到插件里面，插件里面不能用this
                plugItf.onAttach(this, plugInfo.packageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "插件解析异常", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onStart() {
        if (plugItf != null) {
            plugItf.onStart();
        }
        super.onStart();
    }

    @Override
    protected void onRestart() {
        if (plugItf != null) {
            plugItf.onRestart();
        }
        super.onRestart();
    }

    @Override
    protected void onResume() {
        if (plugItf != null) {
            plugItf.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (plugItf != null) {
            plugItf.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (plugItf != null) {
            plugItf.onStop();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        plugItf.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (plugItf != null) {
            plugItf.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        if (plugItf != null) {
            plugItf.onRestoreInstanceState(savedInstanceState);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (plugItf != null) {
            plugItf.onNewIntent(intent);
        }
        super.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
        if (plugItf != null) {
            plugItf.onBackPressed();
        }
        super.onBackPressed();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (plugItf != null) {
            plugItf.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        super.onKeyUp(keyCode, event);
        return plugItf.onKeyUp(keyCode, event);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        if (plugItf != null) {
            plugItf.onWindowAttributesChanged(params);
        }
        super.onWindowAttributesChanged(params);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (plugItf != null) {
            plugItf.onWindowFocusChanged(hasFocus);
        }
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (plugItf != null) {
            plugItf.onCreateOptionsMenu(menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (plugItf != null) {
            plugItf.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public AssetManager getAssets() {
        if (plugInfo != null) {
            return plugInfo.assetManager;
        }
        return super.getAssets();
    }

    @Override
    public Resources getResources() {
        if (plugInfo != null) {
            return plugInfo.resources;
        }
        return super.getResources();
    }

    @Override
    public ClassLoader getClassLoader() {
        if (plugInfo != null) {
            return plugInfo.classLoader;
        }
        return super.getClassLoader();
    }


}
