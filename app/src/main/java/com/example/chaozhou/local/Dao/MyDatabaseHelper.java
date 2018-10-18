package com.example.chaozhou.local.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "user.db";
    public static final String TABLE_NAME = "user";

    private Context mcontent;


    public static final String CREATE_User = "CREATE TABLE "+ TABLE_NAME +" ("
            + "uid  integer PRIMARY KEY Autoincrement ,"
            + "uname varchar(20) ,"
            + "phonenumber varchar(20) ,"
            + "password varchar(20) ,"
            + "birthday varchar(20) ,"
            + "age integer ,"
            + "sex varchar(10),"
            + "head varchar(20) )";


    public MyDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        mcontent = context;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_User);
        Toast.makeText(mcontent, "数据库创建成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
