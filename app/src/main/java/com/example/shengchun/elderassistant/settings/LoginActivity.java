package com.example.shengchun.elderassistant.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shengchun.elderassistant.R;

public class LoginActivity extends Activity {
    private EditText et_user, et_password;
    private CheckBox checkBox;
    private Button loginBtn;
    private Context context;
    private TextView register;
    private boolean isBlue = true;
    private Toolbar toolbar;
    private DataBaseHelper helper;
    private SQLiteDatabase sdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.register:
                    //去注册账号
                    startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                    break;
                case R.id.login_btn:
                    String user = et_user.getText().toString();
                    String pwd = et_password.getText().toString();
                    if(user.equals("") || pwd.equals("")){
                        return;
                    }
                    else {
                        //登录校验
                        helper = new DataBaseHelper(getBaseContext());
                        sdb = helper.getReadableDatabase();
                        //判断用户名是否存在
                        Cursor cursor = sdb.query(DataBaseHelper.TABLENAME,       //表名
                                new String[]{DataBaseHelper.PASSWORD},     //查询的列名
                                DataBaseHelper.USERNAME + "=?",
                                new String[]{user},
                                null, null, null
                        );
                        if (cursor.getCount() == 0) {
                            et_user.setError("该用户不存在！");
                            et_user.setText("");
                            et_password.setText("");
                            return;
                        }
                        cursor.moveToFirst();
                        String password = cursor.getString(0);
                        if (password.equals(pwd)) {
                            Toast.makeText(getBaseContext(), "登陆成功!", Toast.LENGTH_SHORT).show();
                        } else {
                            et_password.setError("密码错误，请重新输入！");
                            et_password.setText("");
                        }
                    }
                    break;
            }
        }
    };
    private void init() {
        context = getBaseContext();
        et_user = (EditText) findViewById(R.id.user);
        et_password = (EditText) findViewById(R.id.pwd);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        loginBtn = (Button) findViewById(R.id.login_btn);
        register = (TextView) findViewById(R.id.register);
        toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        toolbar.setNavigationIcon(R.drawable.back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                   // Toast.makeText(context,"记住密码",Toast.LENGTH_SHORT).show();
                }else{
                   // Toast.makeText(context,"不记住密码",Toast.LENGTH_SHORT).show();
                }
            }
        });
        loginBtn.setOnClickListener(click);
        register.setOnClickListener(click);

    }
}
