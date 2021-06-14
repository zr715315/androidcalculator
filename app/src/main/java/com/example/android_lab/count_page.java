package com.example.android_lab;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import tools.StatusBarUtils;

public class count_page extends AppCompatActivity {
    private boolean ShowActionBarFlag=true;
    private TextView textView;
    private String expression = "";
    private boolean end = false;
    private int countOperate=2;
    private Button[] buttons=new Button[18];
    private int[] ids = new int[]{R.id.b1,R.id.b2,R.id.b3,R.id.b4,R.id.b5,R.id.b6,R.id.b7,
            R.id.b8,R.id.b9,R.id.b10,R.id.b11,R.id.b12,R.id.b13,R.id.b14,R.id.b15,R.id.b16,R.id.b17,R.id.b18
    };

///绑定菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }//绑定actionBar

    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle("计算器");
        actionBar.setDisplayHomeAsUpEnabled(true);
        ///actionBar背景设置
        Resources res = getResources();
        Drawable myDrawable = res.getDrawable(R.drawable.background);///用的png
        actionBar.setBackgroundDrawable(myDrawable);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_page);

        ImageButton button1=(ImageButton)findViewById(R.id.imageButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ShowActionBarFlag==true){
                    actionBar.show();
                    ShowActionBarFlag=false;
                }

                else{
                    actionBar.hide();
                    ShowActionBarFlag=true;
                }

            }
        });


///沉浸式状态栏目
        StatusBarUtils.setColor(this,0x000000);
        StatusBarUtils.setTextDark(this,true);


        for(int i=0;i<ids.length;i++){
            buttons[i] = (Button)findViewById(ids[i]);
            buttons[i].setOnClickListener(this::onClick);
        }
        textView = (TextView)findViewById(R.id.textView);
        }

        //actionbar操作
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        else if(item.getItemId()==R.id.action_settings){
            Toast.makeText(this,"打开成功",Toast.LENGTH_SHORT).show();
            about();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
//跳转
    private void about() {
        Intent i = new Intent(count_page.this , about.class);
        startActivity(i);
    }


    public void onClick(View view) {
        int id = view.getId();
        Button button = (Button)view.findViewById(id);
        String current = button.getText().toString();




        if(end){ //如果上一次算式已经结束，则先清零
            expression = "";
            end = false;
        }
        if(current.equals("c")){
            expression = "";
            countOperate=0;
        }else if(current.equals("back")){ //如果点击退格
            if(expression.length()>1){ //算式长度大于1
                expression = expression.substring(0,expression.length()-1);//退一格
                int i = expression.length()-1;
                char tmp = expression.charAt(i); //获得最后一个字符
                char tmpFront = tmp;
                for(;i>=0;i--){ //向前搜索最近的 +-*/和.，并退出
                    tmpFront = expression.charAt(i);
                    if(tmpFront=='.'||tmpFront=='+'||tmpFront=='-'||tmpFront=='*'||tmpFront=='/'){
                        break;
                    }
                }
                //    Toast.makeText(this, "tmp = "+tmp, Toast.LENGTH_SHORT).show();
                if(tmp>='0'&&tmp<='9'){ //最后一个字符为数字，则识别数赋值为0
                    countOperate=0;
                }else if(tmp==tmpFront&&tmpFront!='.') countOperate=2; //如果为+-*/，赋值为2
                else if(tmpFront=='.') countOperate=1; //如果前面有小数点赋值为1
            }else if(expression.length()==1){
                expression = "";
            }
        }else if(current.equals(".")){
            if(expression.equals("")||countOperate==2){
                expression+="0"+current;
                countOperate = 1;  //小数点按过之后赋值为1
            }
            if(countOperate==0){
                expression+=".";
                countOperate = 1;
            }
        }else if(current.equals("+")||current.equals("-")||current.equals("*")||current.equals("/")){
            if(countOperate==0){
                expression+=current;
                countOperate = 2;  //  +-*/按过之后赋值为2
            }
        }else if(current.equals("=")){ //按下=时，计算结果并显示
            double result = (double) Math.round(count()*10000)/10000;
            expression=result+"";
            end = true;
        }
        else{
            if(expression.length()>=1){
                char tmp1 = expression.charAt(expression.length()-1);
                if(tmp1=='0'&&expression.length()==1){
                    expression = expression.substring(0,expression.length()-1);
                }
                else if(tmp1=='0'&&expression.length()>1){
                    char tmp2 = expression.charAt(expression.length()-2);
                    if(tmp2=='+'||tmp2=='-'||tmp2=='*'||tmp2=='/'){
                        expression = expression.substring(0,expression.length()-1);
                    }
                }
            }
            expression+=current;
            if(countOperate==2||countOperate==1) countOperate=0;
        }





        textView.setText(expression); //显示出来




    }
    //计算逻辑，求expression表达式的值
    private double count(){
        double result=0;
        double tNum=1,lowNum=0.1,num=0;
        char tmp=0;
        int operate = 1; //识别+-*/，为+时为正数，为-时为负数，为×时为-2/2,为/时为3/-3;
        boolean point = false;
        for(int i=0;i<expression.length();i++){ //遍历表达式
            tmp = expression.charAt(i);
            if(tmp=='.'){ //因为可能出现小数，此处进行判断是否有小数出现
                point = true;
                lowNum = 0.1;
            }else if(tmp=='+'||tmp=='-'){
                if(operate!=3&&operate!=-3){ //此处判断通用，适用于+-*
                    tNum *= num;
                }else{ //计算/
                    tNum /= num;
                }
                //    Toast.makeText(this, "tNum = "+tNum, Toast.LENGTH_SHORT).show();
                if(operate<0){ //累加入最终的结果
                    result -= tNum;
                }else{
                    result += tNum;
                }
                operate = tmp=='+'?1:-1;
                num = 0;
                tNum = 1;
                point = false;
            }else if(tmp=='*'){
                if(operate!=3&&operate!=-3){
                    tNum *= num;
                }else{
                    tNum /= num;
                }
                operate = operate<0?-2:2;
                point = false;
                num = 0;
            }else if(tmp=='/'){
                if(operate!=3&&operate!=-3){
                    tNum *= num;
                }else{
                    tNum /= num;
                }
                operate = operate<0?-3:3;
                point = false;
                num = 0;
            }else{
                //读取expression中的每个数字，doube型
                if(!point){
                    num = num*10+tmp-'0';
                }else{
                    num += (tmp-'0')*lowNum;
                    lowNum*=0.1;
                }
            }
        }
        //循环遍历结束，计算最后一个运算符后面的数
        if(operate!=3&&operate!=-3){
            tNum *= num;
        }else{
            tNum /= num;
        }
        if(operate<0){
            result -= tNum;
        }else{
            result += tNum;
        }
        return result;
    }
}


