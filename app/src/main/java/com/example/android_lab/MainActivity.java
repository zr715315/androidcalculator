package com.example.android_lab;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import tools.ScreenUtil;
import tools.StatusBarUtils;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;
    private ImageView imageView;
    private boolean isHideFirst = true;
    private Button signButton;
    private Button loginButton;
    private SharedPreferences sp;
    private String password;
    private String user;
    private EditText passwordEditText;

    @Override
    ///菜单选择
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp=getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle("我的应用");
        actionBar.setDisplayHomeAsUpEnabled(true);
        Resources res = getResources();
        Drawable myDrawable = res.getDrawable(R.drawable.background);
        actionBar.setBackgroundDrawable(myDrawable);





        StatusBarUtils.setColor(this,0x000000);
        StatusBarUtils.setTextDark(this,true);

        editText = (EditText) findViewById(R.id.editText);
        passwordEditText=(EditText)findViewById(R.id.editTextPassword);
        imageView = (ImageView) findViewById(R.id.imageView3);
        imageView.setOnClickListener(this);
        imageView.setImageResource(R.drawable.close);

        signButton=(Button)findViewById(R.id.signButton);
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , sign_page.class);
                startActivity(i);
            }
        });
        loginButton=(Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user=editText.getText().toString();
                if(sp.contains(user)){
                    password=sp.getString(user,"0");
                    if(!password.equals(passwordEditText.getText().toString())){
                        show("密码错误");


                    }else{
                        Intent i=new Intent( MainActivity.this,count_page.class);
                        startActivity(i);
                        show("登录成功");
                    }
                }else{
                    show("不存在该用户");
                }


            }
        });
    }

    @Override
    //小眼睛点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView3:
                if (isHideFirst == true) {
                    imageView.setImageResource(R.drawable.open);

                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                    passwordEditText.setTransformationMethod(method1);
                    isHideFirst = false;
                } else {
                    imageView.setImageResource(R.drawable.close);

                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    passwordEditText.setTransformationMethod(method);
                    isHideFirst = true;

                }
                // 光标的位置
                int index =  passwordEditText.getText().toString().length();
                passwordEditText.setSelection(index);
                break;

        }
    }


    public void show(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
