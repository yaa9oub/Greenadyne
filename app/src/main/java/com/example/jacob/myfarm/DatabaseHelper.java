package com.example.jacob.myfarm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DB_NAME = "FARM";
    private static final String TABLE_NAME = "TAB1";
    private static final String TABLE2_NAME = "TAB2";
    private static final String COL1_2 = "STATUT";
    private static final String COL1 = "USER";
    private static final String COL2 = "MDP";
    private static final String COL3 = "STATUT";
    private static final String COL4 = "TEMPER";
    private static final String COL5 = "HUMID";
    private static final String COL6 = "SALINI";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ( "+COL1+" VARCHAR(10) PRIMARY KEY , "+COL2+" VARCHAR(10),"+COL3+" INTEGER , "+COL4+" INTEGER ,"+COL5+" INTEGER ,"+COL6+" INTEGER)";
        String createTable2 = "CREATE TABLE "+TABLE2_NAME+" ( id  INTEGER PRIMARY KEY AUTOINCREMENT , "+COL1_2+" VARCHAR(10)  )";
        db.execSQL(createTable);
        db.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String user , String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        Random rand = new Random();
        int x = rand.nextInt(40)+20, y=rand.nextInt(70)+30 ,z=rand.nextInt(5)+1 ;
        String query = "INSERT INTO "+TABLE_NAME+" ("+COL1+","+COL2+","+COL3+","+COL4+","+COL5+","+COL6+") VALUES ('"+user+"','"+pass+"',0,"+x+","+y+","+z+")" ;
        Cursor result = db.rawQuery(query,null);

        if (result.getCount() == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(String user, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+COL3+" FROM TAB1 WHERE "+COL1+" = '"+user+"' AND "+COL2+"= '"+pass+"' " ;
        Cursor data = db.rawQuery(query,null);
        return data ;
    }

    public Cursor getsensors(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+COL4+","+COL5+","+COL6+" FROM TAB1 WHERE "+COL1+" = '"+user+"' " ;
        Cursor data = db.rawQuery(query,null);
        return data ;
    }

    public Cursor isConnected(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO "+TABLE2_NAME+" ("+COL1_2+") VALUES ('') " ;
        db.rawQuery(query,null);
        query = "SELECT ("+COL1_2+") FROM TAB2 ";
        Cursor data = db.rawQuery(query,null);
        return data ;
    }

    public boolean connect(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO "+TABLE2_NAME+" ("+COL1_2+") VALUES ('"+user+"') " ;
        db.rawQuery(query,null);
        Cursor result = db.rawQuery(query,null);
        if (result.getCount() == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean disconnect(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "+TABLE2_NAME+" SET "+COL1_2+" = '' " ;
        db.rawQuery(query,null);
        Cursor result = db.rawQuery(query,null);
        if (result.getCount() == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateSensor (String user ,int temp ,int hum ,int sal){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE TAB1 SET "+COL4+" = "+temp+" , "+COL5+" = "+hum+" , "+COL6+" = "+sal+" WHERE "+COL1+" = '"+user+"'";
        Cursor data = db.rawQuery(query, null);
        if (data.getCount() == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean changeData(int id ,String oldname ,String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE TAB1 SET "+COL2+" = '"+name+"' WHERE "+COL1+" = '"+id+"' AND "+COL2+" = '"+oldname+"' ";
        Cursor data = db.rawQuery(query, null);
        if (data.getCount() == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+TABLE_NAME+" WHERE "+COL1+" = '"+id+"' ";
        Cursor data = db.rawQuery(query, null);
        if (data.getCount() == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void clearData(){
        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "DELETE FROM "+TABLE_NAME;
        //Cursor data = db.rawQuery(query, null);
        //String query = "DELETE FROM "+TABLE2_NAME;
        //Cursor data = db.rawQuery(query, null);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        onCreate(db);
    }

}
