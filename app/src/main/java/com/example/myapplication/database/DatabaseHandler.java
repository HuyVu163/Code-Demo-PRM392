package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.bean.UserBean;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String USER_TABLE_NAME = "Users";
    private static final String ID_FIELD = "Id";

    private static final String USERNAME_FIELD = "username";
    private static final String PASSWORD_FIELD = "password";
    private static final String FIRST_NAME_FIELD = "first_name";
    private static final String LAST_NAME_FIELD = "last_name";
    private static final String EMAIL_FIELD = "email";
    private static final String PHONE_FIELD = "phone";
    private static final String CAMPUS_FIELD = "campus";
    private static final String ROLE_FIELD = "role";

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + " (" +
                    ID_FIELD + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USERNAME_FIELD + " TEXT, " +
                    PASSWORD_FIELD + " TEXT, " +
                    FIRST_NAME_FIELD + " TEXT, " +
                    LAST_NAME_FIELD + " TEXT, " +
                    EMAIL_FIELD + " TEXT, " +
                    PHONE_FIELD + " TEXT, " +
                    CAMPUS_FIELD + " TEXT, " +
                    ROLE_FIELD + " TEXT)";
    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
            onCreate(db);
    }

    public void insertUser(UserBean user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME_FIELD, user.getUsername());
        values.put(PASSWORD_FIELD, user.getPassword());
        values.put(FIRST_NAME_FIELD, user.getFirstname());
        values.put(PASSWORD_FIELD, user.getPassword());
        values.put(EMAIL_FIELD, user.getEmail());
        values.put(PHONE_FIELD, user.getPhone());
        values.put(ROLE_FIELD, user.getPhone());
        values.put(CAMPUS_FIELD, user.getCampus());
        db.insert(USER_TABLE_NAME, null, values);
//        db.close();
    }

//    public  UserBean getUserByUsername(String username){
//        SQLiteDatabase db = getReadableDatabase();
//        String query = "SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USERNAME_FIELD + " = ? ";
//        String[] args = {username};
//        Cursor cursor = db.rawQuery(query, args);
//        UserBean user = null;
//        if(cursor.moveToFirst()) {
//            user = new UserBean();
//            int index = cursor.getColumnIndexOrThrow(ID_FIELD);
//            user.setId(cursor.getInt(index));
//            index = cursor.getColumnIndexOrThrow(USERNAME_FIELD);
//            user.setUsername(cursor.getString(index));
//            index = cursor.getColumnIndexOrThrow(PASSWORD_FIELD);
//            user.setPassword(cursor.getString(index));
//            index = cursor.getColumnIndexOrThrow(FIRST_NAME_FIELD);
//            user.setFirstname(cursor.getString(index));
//            index = cursor.getColumnIndexOrThrow(LAST_NAME_FIELD);
//            user.setLastName(cursor.getString(index));
//            index = cursor.getColumnIndexOrThrow(EMAIL_FIELD);
//            user.setEmail(cursor.getString(index));
//            index = cursor.getColumnIndexOrThrow(PHONE_FIELD);
//            user.setPhone(cursor.getString(index));
//        }
//        return user;
//    }

    public UserBean getUserByUsername(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USERNAME_FIELD + " = ?";
        String[] args = {username};
        Cursor cursor = db.rawQuery(query, args);
        UserBean userBean = null;
        if (cursor.moveToFirst()){
            userBean = new UserBean();
            userBean.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CAMPUS_FIELD)));
            userBean.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(USERNAME_FIELD)));
            userBean.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD_FIELD)));
            userBean.setFirstname(cursor.getString(cursor.getColumnIndexOrThrow(FIRST_NAME_FIELD)));
            userBean.setLastName(cursor.getString(cursor.getColumnIndexOrThrow(LAST_NAME_FIELD)));
            userBean.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(EMAIL_FIELD)));
            userBean.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(PHONE_FIELD)));
            userBean.setCampus(cursor.getString(cursor.getColumnIndexOrThrow(CAMPUS_FIELD)));
            userBean.setRole(cursor.getString(cursor.getColumnIndexOrThrow(ROLE_FIELD)));
        }
        cursor.close();
//        db.close();
        return userBean;
    }

    public void deleteUser(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(USER_TABLE_NAME, ID_FIELD + " = ?", new String[]{String.valueOf(id)});
//        db.close();
    }
    public boolean checkUser(String username, String password){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(USER_TABLE_NAME, null, USERNAME_FIELD + " = ? AND " + PASSWORD_FIELD + " = ?",
                new String[]{USERNAME_FIELD, PASSWORD_FIELD}, null, null, null);
        if(cursor.moveToFirst()){
            cursor.close();
            // db.close
            return true;
        }
        return false;
    }
}


