package com.example.android_lab;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import tools.StatusBarUtils;


public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle("详情");
        Resources res = getResources();
        Drawable myDrawable = res.getDrawable(R.drawable.background);///用的png
        actionBar.setBackgroundDrawable(myDrawable);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        StatusBarUtils.setColor(this,0x000000);
        StatusBarUtils.setTextDark(this,true);
    }
}