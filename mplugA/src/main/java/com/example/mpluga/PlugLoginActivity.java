package com.example.mpluga;

import android.os.Bundle;
import android.view.View;

import com.example.pluglibrary.activity.BasePlugActivity;
import com.example.pluglibrary.DLIntent;

/**
 * create by guofeng
 * date on 2019-09-24
 */
public class PlugLoginActivity extends BasePlugActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plug_login_a);

        findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DLIntent intent = new DLIntent();
                intent.setPackageName("com.example.mpluga");
                intent.setmPlugnClass("com.example.mpluga.MainActivity");
                startActivity(PlugLoginActivity.this, intent);
            }
        });


        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //切换到另外一个APK
        findViewById(R.id.mChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DLIntent intent = new DLIntent();
                intent.setPackageName("com.example.mplugb");
                intent.setmPlugnClass("com.example.mplugb.PlugLoginBActivity");
                startActivity(PlugLoginActivity.this, intent);
            }
        });

    }

}
