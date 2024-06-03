package fpt.hieudmph47182.bookstoreapplication.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpt.hieudmph47182.bookstoreapplication.database.DbHelper;
import fpt.hieudmph47182.bookstoreapplication.model.Sach;
import fpt.hieudmph47182.bookstoreapplication.model.ThanhVien;

public class SachDAO {
    private final DbHelper dbHelper;
    private static SQLiteDatabase db = null;
    private static final String TABLE_NAME = "Sach";

    public SachDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public Sach getTVbyMaSach(int maSach) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE MaSach = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(maSach)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            Sach sach = new Sach(cursor.getString(1),
                    cursor.getInt(2));
            sach.setMaSach(cursor.getInt(0));
            sach.setMaLoai(cursor.getInt(3));
            return sach;
        }
        return null;
    }

    public ArrayList<Sach> getSach() {
        ArrayList<Sach> saches = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            Sach sach = new Sach(cursor.getString(1),
                    cursor.getInt(2));
            sach.setMaSach(cursor.getInt(0));
            sach.setMaLoai(cursor.getInt(3));
            saches.add(sach);
        }
        return saches;
    }

    public static boolean insertSach(Sach sach) {
        ContentValues values = new ContentValues();
        values.put("TenSach", sach.getTenSach());
        values.put("GiaThue", sach.getGiaThue());
        values.put("MaLoai", sach.getMaLoai());
        return db.insert(TABLE_NAME, null, values) > 0;
    }

    public boolean updateSach(Sach sach) {
        ContentValues values = new ContentValues();
        values.put("TenSach", sach.getTenSach());
        values.put("GiaThue", sach.getGiaThue());
        values.put("MaLoai", sach.getMaLoai());
        return db.update(TABLE_NAME, values, "MaSach = ?",
                new String[]{String.valueOf(sach.getMaSach())}) > 0;
    }

    public boolean deleteSach(Sach sach) {
        return db.delete(TABLE_NAME, "MaSach = ?",
                new String[]{String.valueOf(sach.getMaSach())}) > 0;
    }
}
