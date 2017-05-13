package com.example.shengchun.elderassistant.settings;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shengchun.elderassistant.R;

public class RegisterActivity extends Activity {
    private EditText et_user,et_password,et_confirm_pwd;
    private Button registerBtn;
    private Toolbar toolbar;
    private DataBaseHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

    }

    private void init() {
        et_user = (EditText) findViewById(R.id.reg_user);
        et_password = (EditText) findViewById(R.id.reg_pwd);
        et_confirm_pwd = (EditText) findViewById(R.id.pop_confirm);
        registerBtn = (Button) findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper = new DataBaseHelper(getBaseContext());
                db = helper.getWritableDatabase();
                String user = et_user.getText().toString();
                String pwd = et_password.getText().toString();
                String confirm = et_confirm_pwd.getText().toString();
                if(user.equals("")){
                    et_user.setError("用户名不能为空");
                    return;
                }
                if(pwd.equals("")){
                    et_password.setError("密码不能为空");
                    return;
                }
                if(!pwd.equals(confirm)){
                    et_confirm_pwd.setError("密码不一致！");
                    et_confirm_pwd.setText("");
                    return;
                }
                //判断用户名是否存在
                Cursor cursor = db.query(DataBaseHelper.TABLENAME,       //表名
                        new String[] {DataBaseHelper.USERNAME},     //查询的列名
                        DataBaseHelper.USERNAME + "=?",
                        new String[] {user},
                        null,null,null
                        );
                if(cursor.getCount() > 0){
                    Toast.makeText(getBaseContext(),"用户名已存在！请更换...",Toast.LENGTH_SHORT).show();
                    et_user.setText("");
                    clearBox();
                    return;
                }
                //不存在可以注册，插入数据
                ContentValues values = new ContentValues();
                values.put(DataBaseHelper.USERNAME,user);
                values.put(DataBaseHelper.PASSWORD,pwd);
                db.insert(DataBaseHelper.TABLENAME,null,values);
                Toast.makeText(getBaseContext(),"注册成功！",Toast.LENGTH_SHORT).show();
            }
        });
        toolbar = (Toolbar) findViewById(R.id.register_toolbar);
        toolbar.setNavigationIcon(R.drawable.back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void clearBox(){
        et_user.setText("");
        et_password.setText("");
        et_confirm_pwd.setText("");
    }
}
