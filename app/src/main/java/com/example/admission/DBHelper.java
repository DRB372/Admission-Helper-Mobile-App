package com.example.admission;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {


    public static final String name = "AdmissionHelper";
    public static final int version = 1;
    public static final String Department = "Department";
    public static final String Program = "Program";
    public static final String University = "University";
    public static final String Relation = "Relation";

    public static final String DEPT_TABLE =
            "CREATE TABLE " + Department + "(Dept_ID INTEGER PRIMARY KEY AUTOINCREMENT,Dept_Name TEXT)";

    public static final String PROG_TABLE =
            "CREATE TABLE " + Program + "(Prog_ID INTEGER PRIMARY KEY AUTOINCREMENT,Prog_Name TEXT)";

    public static final String UNI_TABLE =
            "CREATE TABLE " + University + "(Uni_ID INTEGER PRIMARY KEY AUTOINCREMENT,Uni_Name TEXT,Campus TEXT,City " +
                    "TEXT,Admission_Date TEXT, Website TEXT)";

    public static final String RELA_TABLE =
            "CREATE TABLE " + Relation + "(Dept_ID INTEGER,Prog_ID INTEGER,Uni_ID INTEGER," +
                    " FOREIGN KEY (Dept_ID) REFERENCES Department(Dept_ID)," +
                    "FOREIGN KEY (Prog_ID) REFERENCES Program(Prog_ID)," +
                    "FOREIGN KEY (Uni_ID) REFERENCES University(Uni_ID))";

    public DBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DEPT_TABLE);
        db.execSQL(PROG_TABLE);
        db.execSQL(UNI_TABLE);
        db.execSQL(RELA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Department);
        db.execSQL("DROP TABLE IF EXISTS " + Program);
        db.execSQL("DROP TABLE IF EXISTS " + University);
        db.execSQL("DROP TABLE IF EXISTS " + Relation);

    }

    public Cursor get_data(String sql) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, null);
    }


}


