package fpt.hieudmph47182.bookstoreapplication.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpt.hieudmph47182.bookstoreapplication.database.DbHelper;
import fpt.hieudmph47182.bookstoreapplication.model.ThanhVien;
@SuppressLint("Recycle")
public class ThanhVienDAO {
    private final DbHelper dbHelper;
    private final SQLiteDatabase db;
    private static final String TABLE_NAME = "ThanhVien";

    public ThanhVienDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ThanhVien getTVbyMaTV(int maTV) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE MaTV = ?";
         Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(maTV)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            ThanhVien thanhVien = new ThanhVien(cursor.getString(1),
                    cursor.getString(2));
            thanhVien.setMaTV(cursor.getInt(0));
            return thanhVien;
        }
        return null;
    }

    public ArrayList<ThanhVien> getThanhVien() {
        ArrayList<ThanhVien> thanhViens = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            ThanhVien thanhVien = new ThanhVien(cursor.getString(1),
                    cursor.getString(2));
            thanhVien.setMaTV(cursor.getInt(0));
            thanhViens.add(thanhVien);
        }
        return thanhViens;
    }

    public boolean insertThanhVien(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put("HoTen", thanhVien.getHoTen());
        values.put("NamSinh", thanhVien.getNamSinh());
        long check = db.insert(TABLE_NAME, null, values);
        return check > 0;
    }

    public boolean updateThanhVien(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put("HoTen", thanhVien.getHoTen());
        values.put("NamSinh", thanhVien.getNamSinh());
        long check = db.update(TABLE_NAME, values, "MaTV = ?",
                new String[]{String.valueOf(thanhVien.getMaTV())});
        return check > 0;
    }

    public boolean deleteThanhVien(ThanhVien thanhVien) {
        return db.delete(TABLE_NAME, "MaTV = ?",
                new String[]{String.valueOf(thanhVien.getMaTV())}) > 0;
    }
}
