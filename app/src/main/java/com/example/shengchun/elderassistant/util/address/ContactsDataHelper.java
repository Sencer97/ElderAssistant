package com.example.shengchun.elderassistant.util.address;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Sencer
 * @create 2017/4/27
 * @vertion 1.0
 * @description   联系人数据库
 */

public class ContactsDataHelper extends SQLiteOpenHelper {
    final static int VERSION = 1;
    final static String DATABASENAME = "contact.db";
    final static String TABLENAME = "contacts";
    final static String ID = "id";
    final static String NAME = "name";
    final static String PHONE_NUM = "phoneNum";

    public ContactsDataHelper(Context context) {
        super(context, DATABASENAME, null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建联系人表
        String sql = "CREATE TABLE "+
                this.TABLENAME +"(" +
                this.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                this.NAME + " TEXT,"+
                this.PHONE_NUM + " TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
