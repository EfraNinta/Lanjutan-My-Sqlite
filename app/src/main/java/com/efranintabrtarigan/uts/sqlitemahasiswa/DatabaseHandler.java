package com.efranintabrtarigan.uts.sqlitemahasiswa;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mahasiswa.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE tb_mahasiswa (nama TEXT, nim TEXT, prodi TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS tb_mahasiswa";
        db.execSQL(sql);
        onCreate(db);
    }

    // Method to save data to the tb_mahasiswa table
    public boolean simpan(String nama, String nim, String prodi) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = String.format("INSERT INTO tb_mahasiswa (nama, nim, prodi) VALUES ('%s', '%s', '%s')", nama, nim, prodi);
        db.execSQL(sql);
        return true;
    }

    // Method to retrieve all data from the tb_mahasiswa table
    public List<String> tampilSemua() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM tb_mahasiswa";
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            String data = String.format("%s - %s - %s", cursor.getString(0), cursor.getString(1), cursor.getString(2));
            list.add(data);
        }
        cursor.close();
        return list;
    }

    // Method to update data in the tb_mahasiswa table
    public void update(String oldName, String newName, String newNim, String newProdi) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = String.format("UPDATE tb_mahasiswa SET nama = '%s', nim = '%s', prodi = '%s' WHERE nama = '%s'", newName, newNim, newProdi, oldName);
        db.execSQL(sql);
    }

    // Method to delete data from the tb_mahasiswa table
    public void delete(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = String.format("DELETE FROM tb_mahasiswa WHERE nama = '%s'", name);
        db.execSQL(sql);
    }
}