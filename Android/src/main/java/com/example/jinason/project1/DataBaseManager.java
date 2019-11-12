package com.example.jinason.project1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

// StudentInfo : student Id (unique for each student),first name, last name, gender, course study, age and address. +profile +exam
public class DataBaseManager {
    public static final String DB_NAME = "StudentInfo";
    public static final String DB_TABLE = "StudentInfo";
    public static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE +
            " (StudentID INTEGER PRIMARY KEY, FirstName TEXT, LastName TEXT, Gender TEXT, CourseStudy TEXT, Age INTEGER, Address TEXT, Profile TEXT, Exam TEXT);";
    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public DataBaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public DataBaseManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close()
    {
        helper.close();
    }

    public boolean addRow(Integer id, String firstName, String lastName, String g, String cs, Integer age, String ad, String image, String exam) {
        ContentValues newProduct = new ContentValues();
        newProduct.put("StudentID", id);
        newProduct.put("FirstName", firstName);
        newProduct.put("LastName", lastName);
        newProduct.put("Gender", g);
        newProduct.put("CourseStudy", cs);
        newProduct.put("Age", age);
        newProduct.put("Address", ad);
        newProduct.put("Profile", image);
        newProduct.put("Exam", exam);
        System.out.println(id + firstName + lastName + g + cs + age + ad  + image + exam);

        try {
            db.insertOrThrow(DB_TABLE, null, newProduct);
        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean update(Integer student_id, String firstName, String lastName, String g, String cs, Integer age, String ad, String image, String exam) {
        ContentValues newProduct = new ContentValues();
        newProduct.put("StudentID", student_id);
        newProduct.put("FirstName", firstName);
        newProduct.put("LastName", lastName);
        newProduct.put("Gender", g);
        newProduct.put("CourseStudy", cs);
        newProduct.put("Age", age);
        newProduct.put("Address", ad);
        newProduct.put("Profile", image);
        newProduct.put("Exam", exam);
        System.out.println(student_id + firstName + lastName + g + cs + age + ad + image + exam);

       try {
            db.update(DB_TABLE,newProduct,"StudentID = '"+Integer.toString(student_id)+"'",null);//?", new String[] { Integer.toString(student_id) });
        } catch (Exception e) {
            Log.e("Error in updating rows ", e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delete(Integer student_id){
        try {
            db.delete(DB_TABLE, "StudentID='"+Integer.toString(student_id)+"'",null);
        } catch (Exception e) {
            Log.e("Error in deleting rows ", e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public String retrieveRow() {
        String[] columns = new String[]{"StudentID", "FirstName", "LastName", "Gender", "CourseStudy", "Age", "Address", "Profile", "Exam"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        String tablerows = "";
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            tablerows = tablerows + cursor.getInt(0) + ", " + cursor.getString(1) + ", " +
                    cursor.getString(2) + ", " + cursor.getString(3) + ", " + cursor.getString(4) + ", " +
                    cursor.getInt(5) + ", " + cursor.getString(6) + ", " + cursor.getString(7) + cursor.getString(8)+ "\n";
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return tablerows;
    }


    /////////made it///////////
    public ArrayList<Integer> retrieveKey() {
        String[] columns = new String[]{"StudentID", "FirstName", "LastName", "Gender", "CourseStudy", "Age", "Address", "Profile", "Exam"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        ArrayList<Integer> key = new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            key.add(cursor.getInt(0));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        Collections.sort(key);
        return key;
    }

    /////////made it///////////
    public String findElement(int id, int col) {
        String element = " ";
        String[] columns = new String[]{"StudentID", "FirstName", "LastName", "Gender", "CourseStudy", "Age", "Address", "Profile", "Exam"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            if (id==cursor.getInt(0)){
                element = cursor.getString(col);
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return element;
    }

    /////////made it///////////
    public Integer findAge(int id) {
        int age = 0;
        String[] columns = new String[]{"StudentID", "FirstName", "LastName", "Gender", "CourseStudy", "Age", "Address", "Profile", "Exam"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            if (id==cursor.getInt(0)){
                age = cursor.getInt(5);
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return age;
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