package com.example.myhost;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * create by guofeng
 * date on 2019-09-24
 */
public class FileUtil {

    public static final String SD_FONDER = "plugFonder";

    private String getRootPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + SD_FONDER;
    }


    //获得所有APK的路径
    public List<String> getAllApkPath() {
        final List<String> result = new ArrayList<>();
        final File file = new File(getRootPath());
        if (file.exists()) {
            File[] list = file.listFiles();
            if (list != null) {
                for (File item : list) {
                    if (item.isFile()) {
                        result.add(item.toString());
                    }
                }
            }
        }
        return result;
    }


}
