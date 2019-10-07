package com.example.pluglibrary;

/**
 * create by guofeng
 * date on 2019-09-26
 */
public final class DLConstants {

    //插件单独作为APK运行
    public static final int FROM_INNER = 0;

    //插件必须依赖于宿主来运行,
    public static final int FROM_EXTERN = 1;

    /**
     * {@link #FROM_INNER} 插件单独apk运行，this.startActivity,
     * {@link #FROM_EXTERN} 插件在宿主APK里面运行that.startActivity()
     */
    public static final int DEFAULT_FROM = FROM_EXTERN;
    //启动插件的包名称
    public static final String INTENT_PACKAGE_NAME = "intent_package_name";
    //启动插件的类名称
    public static final String INTENT_CLASS_NAME = "intent_class_name";
}
