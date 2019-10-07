package com.example.myhost;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pluglibrary.DPlugManager;
import com.example.pluglibrary.activity.ProxyActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestSdPermission();
        initView();
    }


    private void initView() {
        List<String> allApkPath = new FileUtil().getAllApkPath();
        DPlugManager.getInstance().initAllPlugInfo(this, allApkPath);
        ListView mListView = findViewById(R.id.mListView);
        PlugListAdapter adapter = new PlugListAdapter(this);
        mListView.setAdapter(adapter);
        adapter.setData(allApkPath);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String dexPath = (String) adapterView.getItemAtPosition(i);
                //根据APK文件获得包名称 和 默认Activity类名称
                PackageInfo packageInfo = DPlugManager.getInstance().getPackageInfo(MainActivity.this, dexPath);
                if (packageInfo == null) return;
                String defActivity = DPlugManager.getInstance().getDefaultActivity(packageInfo);
                //启动代理Activity
                ProxyActivity.jump(MainActivity.this, packageInfo.packageName, defActivity);
            }
        });

    }


    //适配器
    private static class PlugListAdapter extends BaseAdapter {

        private Activity activity;

        public PlugListAdapter(Activity activity) {
            this.activity = activity;
        }

        private final List<String> plugList = new ArrayList<>();

        private void setData(List<String> data) {
            plugList.clear();
            if (data != null) {
                plugList.addAll(data);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return plugList.size();
        }

        @Override
        public String getItem(int i) {
            return plugList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(activity).inflate(R.layout.item_plug, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.mTextView.setText("第" + i + "个插件");
            return view;
        }

        private static class ViewHolder {

            private TextView mTextView;

            private ViewHolder(View convertView) {
                this.mTextView = convertView.findViewById(R.id.mTextView);
            }
        }
    }


    private void requestSdPermission() {
        try {
            //检测是否有写的权限
            int p1 = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int p2 = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            int p3 = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.INSTALL_SHORTCUT);

            if (p1 != PackageManager.PERMISSION_GRANTED
                    || p2 != PackageManager.PERMISSION_GRANTED
                    || p3 != PackageManager.PERMISSION_GRANTED
            ) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INSTALL_SHORTCUT}, REQUEST_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
