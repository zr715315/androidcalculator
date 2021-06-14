package com.example.android_lab;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tools.StatusBarUtils;

public class sign_page extends AppCompatActivity {

   public EditText passwordEditText1;
    private SharedPreferences sp;
    public EditText passwordEditText;
    public EditText editText;
    public Button button;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_page);
        sp=getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        button=(Button)findViewById(R.id.signButton);
        editText=(EditText)findViewById(R.id.editText);
        passwordEditText1=(EditText)findViewById(R.id.editTextTextPassword2);
        passwordEditText=(EditText)findViewById(R.id.editTextTextPassword);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle("我的应用");

        actionBar.setDisplayHomeAsUpEnabled(true);
        Resources res = getResources();
        Drawable myDrawable = res.getDrawable(R.drawable.background);
        actionBar.setBackgroundDrawable(myDrawable);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText()==null||passwordEditText.getText()==null||passwordEditText1.getText()==null){
                    show("请完成输入后点击注册");
                }
                else if(!(passwordEditText.getText().toString()).equals(passwordEditText1.getText().toString())) {
                    show("两次密码输入不一样");
                }else {
                    SharedPreferences.Editor editor=sp.edit();
                    if(sp.contains(editText.getText().toString())){
                        show("用户名已存在");
                    }else{
                        editor.putString((editText.getText()).toString(),(passwordEditText.getText()).toString());
                        editor.commit();
                        Intent i=new Intent( sign_page.this,MainActivity.class);
                        startActivity(i);
                        show("注册成功");
                    }
                    }

            }
        });
        StatusBarUtils.setColor(this,0x000000);
        StatusBarUtils.setTextDark(this,true);
    }


    public void show(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}


