package com.aspsine.multithreaddownload.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aspsine.multithreaddownload.demo.entity.AppInfo;
import com.aspsine.multithreaddownload.demo.ui.fragment.AppDetailFragment;

import hitamigos.sourceget.R;

public class AppDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        Intent intent = getIntent();
        AppInfo appInfo = (AppInfo) intent.getSerializableExtra("EXTRA_APPINFO");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AppDetailFragment.newInstance(appInfo))
                    .commit();
        }
       // getSupportActionBar().setTitle(appInfo.getName());
    }

}
