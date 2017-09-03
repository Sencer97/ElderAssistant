package com.example.shengchun.elderassistant.status;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shengchun.elderassistant.R;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class YunDB extends Activity {
    private static final String TAG = "YunDB";
    private Button button;
// 阿里云RDS数据库
// private String url = "jdbc:mysql://rm-wz91129o5164mdj32.mysql.rds.aliyuncs.com:3306/mydb";
//本地数据库
    private String url = "jdbc:mysql://localhost:3306/vocabulary";
    private String user = "root";
//    private String password = "sencer123=";
    private String password = "123456";
    private java.sql.Connection con = null;
    private Statement stmt = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yun_db);
        init();
    }
    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Toast.makeText(getBaseContext(), "获取ing...", Toast.LENGTH_SHORT).show();

            new Thread() {
                @Override
                public void run() {
                    try {
                        Log.d(TAG, "onClick: ");
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection(url, user, password);
                        String sql = "select * from newwords";
                        String str = "";
                        stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);
                        Log.d(TAG, "onClick: "+str);
                        while (rs.next()) {
                            //     str = rs.getString("id")+rs.getString("name")+rs.getString("age");
                            str = rs.getString(0) + rs.getString(1);
                        }
                        Log.d(TAG, "onClick: "+str);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        Log.d(TAG, "class: " + e.toString());
                    } catch (SQLException e) {
                        Log.d(TAG, "sql: " + e.toString());
                        e.printStackTrace();
                        Log.d(TAG,"connect failed...");
                    }
                }
            }.start();
        }
    };
    private void init() {
        button = (Button) findViewById(R.id.testBtn);
        button.setOnClickListener(click);

    }
}
