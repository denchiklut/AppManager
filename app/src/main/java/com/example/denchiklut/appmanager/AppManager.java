package com.example.denchiklut.appmanager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

public class AppManager {

    private final PackageManager packageManager;

    public AppManager(Context context) {
        packageManager = context.getPackageManager();
    }

    public List<AppInfo> getInstalledApps() {

        List<AppInfo> installedApps = new ArrayList<>();

        List<PackageInfo> intstalledPackages = packageManager.getInstalledPackages(0);

        for (PackageInfo installedApp: intstalledPackages) {
            AppInfo appInfo = new AppInfo(
                    installedApp.packageName,
                    installedApp.versionCode,
                    installedApp.versionName,
                    installedApp.applicationInfo.loadLabel(packageManager).toString(),
                    installedApp.applicationInfo.sourceDir,
                    installedApp.applicationInfo.loadIcon(packageManager)
            );

            installedApps.add(appInfo);
        }

        return installedApps;

    }
}
