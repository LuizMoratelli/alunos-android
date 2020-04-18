package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String TABLE = "Students";
    private static final String DATABASE = "students";
    private static final String[] COLUMNS = {
            "id",
            "name",
            "phone",
            "address",
            "website",
            "picture",
            "grade",
    };

    public StudentDAO(@Nullable Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE + "("
                + "id INTEGER PRIMARY KEY,"
                + "name TEXT UNIQUE NOT NULL,"
                + "phone TEXT,"
                + "address TEXT,"
                + "website TEXT,"
                + "picture TEXT,"
                + "grade REAL);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE;
        db.execSQL(sql);
        onCreate(db);
    }

    public void onInsertStudent(Student student) {
        ContentValues values = new ContentValues();
        values.put("name", student.getName());
        values.put("phone", student.getPhone());
        values.put("address", student.getAddress());
        values.put("website", student.getWebsite());
        values.put("picture", student.getPicture());
        values.put("grade", student.getGrade());

        getWritableDatabase().insert(TABLE, null, values);
    }

    public List<Student> onListStudents() {
        List<Student> students = new ArrayList<>();

        Cursor c = getWritableDatabase().query(
                TABLE,
                COLUMNS,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (c.moveToNext()) {
            Student student = new Student();
            student.setId(c.getLong(0));
            student.setName(c.getString(1));
            student.setPhone(c.getString(2));
            student.setAddress(c.getString(3));
            student.setWebsite(c.getString(4));
            student.setPicture(c.getString(5));
            student.setGrade(c.getDouble(6));

            students.add(student);
        }

        c.close();

        return students;
    }

    public void onDeleteStudent(Student student) {
        String[] where = {student.getId().toString()};

        getWritableDatabase().delete(TABLE, "id=?", where);
    }

    public void onUpdateStudent(Student student) {
        String[] where = {student.getId().toString()};

        ContentValues values = new ContentValues();
        values.put("name", student.getName());
        values.put("phone", student.getPhone());
        values.put("address", student.getAddress());
        values.put("website", student.getWebsite());
        values.put("picture", student.getPicture());
        values.put("grade", student.getGrade());

        getWritableDatabase().update(TABLE, values, "id=?", where);
    }

    public boolean isStudent(String id) {
        Cursor c = getWritableDatabase().rawQuery(
            "select id from " + TABLE + " where id = ?",
                new String[] {id}
        );

        int total = c.getCount();
        c.close();

        return total > 0;
    }
}

