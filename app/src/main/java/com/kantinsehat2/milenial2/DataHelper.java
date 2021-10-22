package com.kantinsehat2.milenial2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="pesanan.db";
    public static final String TABLE_NAME="kantin";
    public static final String COL_1="ID";
    public static final String COL_2="name";
    public static final String COL_3="jumlah";
    public static final String COL_4="notes";
    public static final String COL_5="total_harga";
    public static final String COL_6="gambar";
    public static final String COL_7="totals";
    public static final String COL_8="id_keranjang";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name text, jumlah double,notes text,total_harga double,gambar text,totals double,id_keranjang INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(int ID, String name, int jumlah, String notes, int total_harga, String gambar, int totals){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_8,ID);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,jumlah);
        contentValues.put(COL_4,notes);
        contentValues.put(COL_5,total_harga);
        contentValues.put(COL_6,gambar);
        contentValues.put(COL_7,totals);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1){
            return false;
        }else
        {
            return true;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        return true;
    }

    public boolean deleteById(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        db.delete(TABLE_NAME,"ID = ?",new String[]{id});
        return true;
    }
}
