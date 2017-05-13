package com.example.shengchun.elderassistant.settings;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Sencer
 * @create 2017/4/23
 * @vertion 1.0
 * @description
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    final static int VERSION = 1;
    public final static String DATABASENAME = "my_database.db";
    final static String TABLENAME = "accounts";
    final static String ID = "id";
    final static String USERNAME = "username";
    final static String PASSWORD = "password";

    public DataBaseHelper(Context context) {
        super(context, DATABASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //创建一个账户数据表
        String create_sql = "CREATE TABLE "+
                TABLENAME + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USERNAME + " TEXT," +
                PASSWORD + " TEXT);" ;
        db.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
