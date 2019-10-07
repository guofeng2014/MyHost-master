package com.example.pluglibrary.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.pluglibrary.DLConstants;
import com.example.pluglibrary.DLIntent;
import com.example.pluglibrary.DPlugManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import m.plug.itf.IActivity;

/**
 * create by guofeng
 * date on 2019-09-25
 */
public class BasePlugActivity extends Activity implements IActivity {

    public static final String FROM = "FROM";

    //起到开关作用的,决定插件是否吧可以单独运行
    private int from = DLConstants.DEFAULT_FROM;

    protected Activity that;

    private String mPackageName;

    @Override
    public void onAttach(Activity mProxy, String packageName) {
        that = mProxy;
        mPackageName = packageName;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            from = savedInstanceState.getInt(FROM, DLConstants.FROM_INNER);
        }
        if (from == DLConstants.FROM_INNER) {
            super.onCreate(savedInstanceState);
        }
    }


    //跳转到指定的界面
    public void startActivity(Context context, DLIntent dlIntent) {
        try {
            if (DLConstants.FROM_EXTERN == from) {
                DPlugManager.getInstance().startActivity(that, dlIntent);
            } else {
                Intent intent = new Intent();
                intent.setClassName(dlIntent.packageName, dlIntent.mPlugnClass);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //startService启动service
    public void startServices(Context context, DLIntent intent) {
        if (DLConstants.FROM_EXTERN == from) {//作为插件运行
            DPlugManager.getInstance().startService(that, intent);
        } else {//单独运行
            Intent intent1 = new Intent();
            intent1.setClassName(intent.packageName, intent.mPlugnClass);
            context.startService(intent1);
        }
    }


    //bindService启动service
    public void bindService(Context context, DLIntent intent, ServiceConnection connection, int flag) {
        if (DLConstants.FROM_EXTERN == from) {//作为插件运行
            DPlugManager.getInstance().onBindService(that, intent, connection, flag);
        } else {//单独运行
            Intent intent1 = new Intent();
            intent1.setClassName(intent.packageName, intent.mPlugnClass);
            context.bindService(intent1, connection, flag);
        }
    }

    @Override
    public void setContentView(View view) {
        if (from == DLConstants.FROM_INNER) {
            super.setContentView(view);
        } else {
            that.setContentView(view);
        }

    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (from == DLConstants.FROM_INNER) {
            super.setContentView(view, params);
        } else {
            that.setContentView(view, params);
        }
    }


    @Override
    public void setContentView(int layoutResID) {
        if (from == DLConstants.FROM_INNER) {
            super.setContentView(layoutResID);
        } else {
            that.setContentView(layoutResID);
        }
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        if (from == DLConstants.FROM_INNER) {
            super.addContentView(view, params);
        } else {
            that.addContentView(view, params);
        }
    }

    @Override
    public <T extends View> T findViewById(int id) {
        if (from == DLConstants.FROM_INNER) {
            return super.findViewById(id);
        } else {
            return that.findViewById(id);
        }
    }

    @Override
    public Intent getIntent() {
        if (from == DLConstants.FROM_INNER) {
            return super.getIntent();
        } else {
            return that.getIntent();
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        if (from == DLConstants.FROM_INNER) {
            return super.getClassLoader();
        } else {
            return that.getClassLoader();
        }
    }

    @Override
    public Resources getResources() {
        if (from == DLConstants.FROM_INNER) {
            return super.getResources();
        } else {
            return that.getResources();
        }
    }


    //============有问题========
    @Override
    public String getPackageName() {
        if (from == DLConstants.FROM_INNER) {
            return super.getPackageName();
        } else {//需要返回插件的包名称
            return mPackageName;
        }
    }


    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        if (from == DLConstants.FROM_INNER) {
            return super.getLayoutInflater();
        } else {
            return that.getLayoutInflater();
        }
    }

    @NonNull
    @Override
    public MenuInflater getMenuInflater() {
        if (from == DLConstants.FROM_INNER) {
            return super.getMenuInflater();
        } else {
            return that.getMenuInflater();
        }
    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        if (from == DLConstants.FROM_INNER) {
            return super.getSharedPreferences(name, mode);
        } else {
            return that.getSharedPreferences(name, mode);
        }
    }

    @Override
    public Context getApplicationContext() {
        if (from == DLConstants.FROM_INNER) {
            return super.getApplicationContext();
        } else {
            return that.getApplicationContext();
        }
    }

    @Override
    public WindowManager getWindowManager() {
        if (from == DLConstants.FROM_INNER) {
            return super.getWindowManager();
        } else {
            return that.getWindowManager();
        }
    }

    @Override
    public Window getWindow() {
        if (from == DLConstants.FROM_INNER) {
            return super.getWindow();
        } else {
            return that.getWindow();
        }
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if (from == DLConstants.FROM_INNER) {
            return super.getSystemService(name);
        } else {
            return that.getSystemService(name);
        }
    }

    @Override
    public void onStart() {
        if (from == DLConstants.FROM_INNER) {
            super.onStart();
        }
    }

    @Override
    public void finish() {
        if (from == DLConstants.FROM_INNER) {
            super.finish();
        } else {
            that.finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (from == DLConstants.FROM_INNER) {
            super.onBackPressed();
        }
    }

    @Override
    public void onRestart() {
        if (from == DLConstants.FROM_INNER) {
            super.onRestart();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (from == DLConstants.FROM_INNER) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onResume() {
        if (from == DLConstants.FROM_INNER) {
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if (from == DLConstants.FROM_INNER) {
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if (from == DLConstants.FROM_INNER) {
            super.onStop();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (from == DLConstants.FROM_INNER) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (from == DLConstants.FROM_INNER) {
            return super.onKeyUp(keyCode, event);
        }
        return false;
    }


    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        if (from == DLConstants.FROM_INNER) {
            super.onWindowAttributesChanged(params);
        }
    }


    public void onWindowFocusChanged(boolean hasFocus) {
        if (from == DLConstants.FROM_INNER) {
            super.onWindowFocusChanged(hasFocus);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (from == DLConstants.FROM_INNER) {
            return super.onCreateOptionsMenu(menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (from == DLConstants.FROM_INNER) {
            return onOptionsItemSelected(item);
        }
        return false;
    }


    @Override
    public void onDestroy() {
        if (from == DLConstants.FROM_INNER) {
            super.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (from == DLConstants.FROM_INNER) {
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (from == DLConstants.FROM_INNER) {
            super.onNewIntent(intent);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (from == DLConstants.FROM_INNER) {
            super.onRestoreInstanceState(savedInstanceState);
        }

    }


}
