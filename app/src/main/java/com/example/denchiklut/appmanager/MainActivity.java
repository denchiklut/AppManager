package com.example.denchiklut.appmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_PICK_APK = 1;

    private SwipeRefreshLayout swipeRefreshLayout;

    private AppManager appManager;
    private AppsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);

        appManager = new AppManager(this);
        adapter = new AppsAdapter();

        RecyclerView recyclerView = findViewById(R.id.apps_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        reloadApps();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        MenuItem menuItem = menu.findItem(R.id.search_item);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.setQuery(s.toLowerCase().trim());
                adapter.notifyDataSetChanged();

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.install_item:
                startFilePickerActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_APK && resultCode == RESULT_OK) {
            String apkPath = data.getStringExtra(FilePickerActivity.EXTRA_FILE_PATH);
            Log.i(TAG, "apk path: " + apkPath);

            startAppInstallation(apkPath);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    /**

     * Сообщаем системе, что хотим установить APK

     *

     * @param apkPath Путь до APK-файла

     */
    public void startAppInstallation(String apkPath) {
        Intent installIntent = new Intent(Intent.ACTION_INSTALL_PACKAGE);

        Uri uri;

        if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this,
                    BuildConfig.APPLICATION_ID + ".provider", new File(apkPath));

            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(new File(apkPath));
        }

        installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Создаст новый процесс

        startActivity(installIntent);

    }

    /**

     * Запускаем Activity для выбора файла

     */
    public void  startFilePickerActivity() {
        Intent intent = new Intent(this, FilePickerActivity.class);
        startActivityForResult(intent, REQUEST_CODE_PICK_APK);
    }


    /**

     * Перезагружаем список приложений

     */
    private void reloadApps() {
        List<AppInfo> installedApps = appManager.getInstalledApps();
        adapter.setApps(installedApps);
        adapter.notifyDataSetChanged();

        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Listener для SwipeRefreshLayout
     */
    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            reloadApps();
        }
    };

}
