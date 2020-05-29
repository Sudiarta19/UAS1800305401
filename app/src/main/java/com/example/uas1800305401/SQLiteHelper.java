package com.example.uas1800305401;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

//import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    //INSERT
    public void insertData(String id_barang, String nama, String jumlah , String lokasi_gedung, String lokasi_ruang, byte[] image) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO BARANG VALUES (NULL, ?, ?, ?, ?, ?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, id_barang );
        statement.bindString(2, nama);
        statement.bindString(3, jumlah);
        statement.bindString(4, lokasi_gedung);
        statement.bindString(5, lokasi_ruang);
        statement.bindBlob(6, image);

        statement.executeInsert();
    }

    //UPDATE
    public void updateData(String id_barang, String nama, String jumlah , String lokasi_gedung, String lokasi_ruang, byte[] image, int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE BARANG SET id_barang = ?, nama = ?, jumlah = ?, lokasi_gedung = ?, lokasi_ruang = ?, image=? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, id_barang );
        statement.bindString(2, nama);
        statement.bindString(3, jumlah);
        statement.bindString(4, lokasi_gedung);
        statement.bindString(5, lokasi_ruang);
        statement.bindBlob(6, image);
        statement.bindDouble(7, (double) id);


        statement.execute();
        database.close();
    }


    //DELETE
    public void deleteData(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM BARANG WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
