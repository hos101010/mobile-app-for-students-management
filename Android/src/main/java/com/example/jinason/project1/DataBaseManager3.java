package com.example.jinason.project1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

//Exam : unit name, date, time and location


public class DataBaseManager3 {
    public static final String DB_NAME = "Exam";
    public static final String DB_TABLE = "Exam";
    public static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE +
            " (UnitName TEXT PRIMARY KEY, Date TEXT, Time TEXT, Location TEXT, StudentID INTEGER, FOREIGN KEY(StudentID) REFERENCES StudentInfo(StudentID));";
    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public DataBaseManager3(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public DataBaseManager3 openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close()
    {
        helper.close();
    }

    public boolean addRow(String unit, String date, String time, String location, int stu_id) {
        ContentValues newProduct = new ContentValues();
        newProduct.put("UnitName", unit);
        newProduct.put("Date", date);
        newProduct.put("Time", time);
        newProduct.put("Location", location);
        newProduct.put("StudentID", stu_id);
        System.out.println(unit + date + time + location + stu_id);

        try {
            db.insertOrThrow(DB_TABLE, null, newProduct);
        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean update(String unit, String date, String time, String location, int stu_id) {
        ContentValues newProduct = new ContentValues();
        newProduct.put("UnitName", unit);
        newProduct.put("Date", date);
        newProduct.put("Time", time);
        newProduct.put("Location", location);
        newProduct.put("StudentID", stu_id);
        System.out.println(unit + date + time + location + stu_id);

        try {
            db.update(DB_TABLE,newProduct,"NAME = '"+unit+"'",null);
        } catch (Exception e) {
            Log.e("Error in updating rows ", e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delete(String unit){
        try {
            db.delete(DB_TABLE, "UnitName='"+unit+"'",null);
        } catch (Exception e) {
            Log.e("Error in deleting rows ", e.toString());
            e.printStackTrace();
            return false;
        }
        System.out.print("\n DELETE!!!!!!!!!!!!!!!!!!!\n");
        return true;
    }

    public ArrayList<String> retrieveRow(int stu_id) {
        String[] columns = new String[]{"UnitName", "Date", "Time", "Location", "StudentID"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        ArrayList<String> tablerows = new ArrayList<>();
        String content = "";
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            if (stu_id == cursor.getInt(4)) {
                content = cursor.getString(0) + " " + cursor.getString(1) + " " +
                        cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getInt(4);
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
        String[] columns = new String[]{"UnitName", "Date", "Time", "Location", "StudentID"};
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
    public String findElement(String unit, int col) {
        String element = " ";
        String[] columns = new String[]{"UnitName", "Date", "Time", "Location", "StudentID"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            if (unit.equals(cursor.getString(0))){
                element = cursor.getString(col);
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return element;
    }

    /////////made it///////////
    public ArrayList findEarlier_withNonChecked(Date criteria, int stu_id){
        String[] columns = new String[]{"UnitName", "Date", "Time", "Location", "StudentID"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        ArrayList earlier_exam_list = new ArrayList();
        Date date = criteria;

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            //change the text of exam time to Date Type
            try {
                date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(cursor.getString(1)+" " + cursor.getString(2));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (stu_id == cursor.getInt(4)) {
                if (date.compareTo(criteria) < 0) {
                    earlier_exam_list.add(new MyDataModel(
                            cursor.getString(0) + "," + cursor.getString(1) + "," + cursor.getString(2)
                                    + "," + cursor.getString(3), false));
                }
            }
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return earlier_exam_list;
    }

    /////////made it//////////////
    public ArrayList findLater_withNonChecked(Date criteria, int stu_id){
        String[] columns = new String[]{"UnitName", "Date", "Time", "Location", "StudentID"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        ArrayList later_exam_list = new ArrayList();
        Date date = criteria;

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            //change the text of exam time to Date Type
            try {
                date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(cursor.getString(1)+" " + cursor.getString(2));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (stu_id == cursor.getInt(4)) {
                if (date.compareTo(criteria) >= 0) {
                    later_exam_list.add(new MyDataModel(
                            cursor.getString(0) + "," + cursor.getString(1) + "," + cursor.getString(2)
                                    + "," + cursor.getString(3), false));
                }
            }
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return later_exam_list;
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