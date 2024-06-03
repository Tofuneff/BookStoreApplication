package fpt.hieudmph47182.bookstoreapplication.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpt.hieudmph47182.bookstoreapplication.database.DbHelper;
import fpt.hieudmph47182.bookstoreapplication.model.LoaiSach;

public class LoaiSachDAO {
    private final DbHelper dbHelper;
    private final SQLiteDatabase db;
    private static final String TABLE_NAME = "LoaiSach";

    public LoaiSachDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public LoaiSach getLoaiSachByMaLoai(int maLoai) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE MaLoai = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(maLoai)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return new LoaiSach(cursor.getInt(0), cursor.getString(1));
        }

        return null;
    }

    public ArrayList<LoaiSach> getLoaiSach() {
        ArrayList<LoaiSach> loaiSaches = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            LoaiSach loaiSach = new LoaiSach(cursor.getInt(0), cursor.getString(1));
            loaiSaches.add(loaiSach);
        }
        return loaiSaches;
    }

    public boolean insertLoaiSach(LoaiSach loaiSach) {
        ContentValues values = new ContentValues();
        values.put("HoTen", loaiSach.getTenLoaiSach());
        return db.insert(TABLE_NAME, null, values) > 0;
    }

    public boolean updateLoaiSach(LoaiSach loaiSach) {
        ContentValues values = new ContentValues();
        values.put("HoTen", loaiSach.getTenLoaiSach());
        return db.update(TABLE_NAME, values, "MaLoai = ?",
                new String[]{String.valueOf(loaiSach.getMaLoai())}) > 0;
    }

    public boolean deleteLoaiSach(LoaiSach loaiSach) {
        return db.delete(TABLE_NAME, "MaLoai = ?",
                new String[]{String.valueOf(loaiSach.getMaLoai())}) > 0;
    }
}
