package com.example.jinason.project1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

//task name, location and status

public class DataBaseManager2 {
    public static final String DB_NAME = "Todo";
    public static final String DB_TABLE = "Todo";
    public static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE +
            " (NAME TEXT PRIMARY KEY, Location TEXT, Status TEXT);";
    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public DataBaseManager2(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public DataBaseManager2 openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addRow(String name, String location, String status) {
        ContentValues newProduct = new ContentValues();
        newProduct.put("NAME", name);
        newProduct.put("Location", location);
        newProduct.put("Status", status);
        System.out.println(name + location + status);

        try {
            db.insertOrThrow(DB_TABLE, null, newProduct);
        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean update(String name, String location, String status) {
        ContentValues newProduct = new ContentValues();
        newProduct.put("NAME", name);
        newProduct.put("Location", location);
        newProduct.put("Status", status);
        System.out.println(name + location + status);

       try {
            db.update(DB_TABLE,newProduct,"NAME = '"+name+"'",null);
        } catch (Exception e) {
            Log.e("Error in updating rows ", e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delete(String name){
        try {
            db.delete(DB_TABLE, "Name='"+name+"'",null);
        } catch (Exception e) {
            Log.e("Error in deleting rows ", e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }


    ////////made it///////////
    public ArrayList<String> retrieveRow(String condition) {
        String[] columns = new String[]{"Name", "Location", "Status"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        ArrayList<String> tablerows = new ArrayList<>();
        String content = " ";
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            if (condition.equals(cursor.getString(2))) {
                content = cursor.getString(0) + ", " + cursor.getString(1);
                tablerows.add(content);
            }
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return tablerows;
    }


    /////////made it///////////
    public ArrayList<String> retrieveKey() {
        String[] columns = new String[]{"Name", "Location", "Status"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        ArrayList<String> key = new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            key.add(cursor.getString(0));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        Collections.sort(key);
        return key;
    }

    /////////made it///////////
    public String findElement(String name, int col) {
        String element = " ";
        String[] columns = new String[]{"Name", "Location", "Status"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            if (name.equals(cursor.getString(0))){
                element = cursor.getString(col);
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return element;
    }

    public void clearRecords()
    {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE, null, null);
    }


    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper(Context c) {
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Products table", "Upgrading database i.e. dropping table and recreating it");
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }
    }
}