package com.example.mplugb;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.pluglibrary.activity.BasePlugActivity;
import com.example.pluglibrary.DLIntent;

/**
 * create by guofeng
 * date on 2019-09-25
 */
public class PlugLoginBActivity extends BasePlugActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plug_login_b);


        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DLIntent intent = new DLIntent();
                intent.setPackageName("com.example.mplugb");
                intent.setmPlugnClass("com.example.mplugb.MainActivity");
                startActivity(PlugLoginBActivity.this, intent);
            }
        });
    }
}
