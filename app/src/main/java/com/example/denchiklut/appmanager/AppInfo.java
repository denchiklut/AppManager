package com.example.denchiklut.appmanager;

import android.graphics.drawable.Drawable;

public class AppInfo {

    private final String packageName;
    private final int versionCode;
    private final String versionName;
    private final String name;
    private final String dir;
    private final Drawable icon;

    public AppInfo(String packageName, int versionCode, String versionName, String name, String dir, Drawable icon) {
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.name = name;
        this.dir = dir;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "packageName='" + packageName + '\'' +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", name='" + name + '\'' +
                ", dir='" + dir + '\'' +
                ", icon=" + icon +
                '}';
    }

    public String getPackageName() {
        return packageName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getName() {
        return name;
    }

    public String getDir() {
        return dir;
    }

    public Drawable getIcon() {
        return icon;
    }
}
