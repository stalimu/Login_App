package com.example.admin.simpleloginapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by Admin on 7/13/2016.

 */
//Database class to hold the sign up and login in table
public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "members.db";
    public static final String TABLE_NAME = "watu";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT NOT NULL,PASSWORD TEXT NOT NULL); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String name,String pword){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3, pword);
        long result= db.insert(TABLE_NAME,null,contentValues);
        if(result== -1)
            return false;
        else
            return true;


    }
    public Cursor viewAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =  db.rawQuery("select * from "+ TABLE_NAME,null);
        return res;

    }
    public boolean Login(String username, String password) throws SQLException
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * from " + TABLE_NAME + " WHERE USERNAME=? and PASSWORD=? ", new String[]{username, password});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }
}
