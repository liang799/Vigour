package com.sp.vigour;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Addhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StepsList.db";
    private static final int SCHEMA_VERSION = 2;

    public Addhelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Steps_table( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "usersteps TEXT, userdate TEXT, usertime TEXT, usercrypto REAL)");
        // REAL is a floating value
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Steps_table");
        onCreate(db);
    }

    public void insert(String usersteps, String userdate) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("usersteps",usersteps);
        cv.put("userdate",userdate);

        db.insert("Steps_table", "usersteps", cv);
        Log.d("Actlife","db inserted");

    }

    public Cursor getdata() {
        String query = "SELECT * " + " FROM Steps_table";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }

        return cursor;
    }

    public Boolean delete(String historyID) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Steps_table WHERE _id = ?", new String[]{historyID});
        if (cursor.getCount() > 0) {
            long result = db.delete("Steps_table", "_id=?", new String[]{historyID});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public void updateSteps(String steps, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("usersteps", steps);
        db.update("Steps_table", cv, "userdate=?", new String[]{date});
        db.close();
    }

    public boolean checkForTables(){
        boolean hasTables = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( "SELECT *  FROM Steps_table", null);

        if(cursor != null && cursor.getCount() > 0){
            hasTables=true;
            cursor.close();
        }
        db.close();

        return hasTables;
    }

    public int getSteps(Cursor c) {
        return (Integer.parseInt(c.getString(1)));
    }
    public String getDate(Cursor c) {
        return (String.valueOf(c.getFloat(2)));
    }

    public String getCoin(Cursor c) {
        Float amt = c.getFloat(4);
        if (amt == null)
            return "0";
        return String.valueOf(amt);
    }

    public String coinInsight() {
        String result = "Start walking now to earn VGR";
        if (checkForTables()) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT MAX(usercrypto) FROM Steps_table", null);

            if (cursor != null && cursor.getCount() > 0)
                result = "Your highest earning was " + getCoin(cursor) + "Vigour Coin on "
                        + getDate(cursor);
            db.close();
        }
        return result;
    }

    public String stepsInsight() {
        String result = "No data. Did you enable Vigour to access you physical activity?";

        if (checkForTables()) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery( "SELECT *  FROM Steps_table", null);

            if (cursor.getCount() == 1){
                result = "Thank you for downloading our humble app, please keep up the good work";
            } else {
                Cursor model = getdata();
                model.moveToLast();
                int today = getSteps(model);
                model.moveToPrevious();
                int yest = getSteps(model);
                int stepsMath = today - yest;
                if (stepsMath < 0)
                    result = "You walked more yesterday than today by "
                            + String.valueOf(Math.abs(stepsMath)) + "steps";
                else
                    result = "You walked more today than yesterday by "
                            + String.valueOf(stepsMath) + "steps";
            }
            db.close();
        }
        return result;
    }

    public String getVGR() {
        String amount = "0";
        if (checkForTables()) {
            Cursor cursor = getdata();
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToLast();
                amount = getCoin(cursor);
            }
        }
        return amount;
    }
}
