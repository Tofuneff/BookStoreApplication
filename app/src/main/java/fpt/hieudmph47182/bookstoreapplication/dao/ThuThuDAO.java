package fpt.hieudmph47182.bookstoreapplication.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpt.hieudmph47182.bookstoreapplication.database.DbHelper;
import fpt.hieudmph47182.bookstoreapplication.model.ThuThu;

@SuppressLint("Recycle")
public class ThuThuDAO {
    public static ThuThu ACCOUNT = null;
    private final DbHelper dbHelper;
    private final SQLiteDatabase db;
    private static final String TABLE_NAME = "ThuThu";

    public ThuThuDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }



    public boolean checkLogin(String username, String password) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE MaTT = ? AND MatKhau = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            ACCOUNT = new ThuThu(cursor.getString(0),
                    cursor.getString(1), cursor.getString(2));
            return true;
        }
        return false;
    }

    public ThuThu getTTByMaTT(String maTT) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE MaTT = ?";
        Cursor cursor = db.rawQuery(query, new String[]{maTT});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            // con trỏ đi từ hàng đầu tiên
            return new ThuThu(cursor.getString(0),
                    cursor.getString(1), cursor.getString(2));
        }
        return null; // trả ra null nếu không tìm thấy
    }

    public ArrayList<ThuThu> getAllThuThu() {
        ArrayList<ThuThu> thuThus = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            thuThus.add(new ThuThu(cursor.getString(0),
                    cursor.getString(1), cursor.getString(2)));
        }
        return thuThus;
    }

    public boolean insertThuThu(ThuThu thuThu) {
        ContentValues values = new ContentValues();
        values.put("MaTT", thuThu.getMaTT());
        values.put("HoTen", thuThu.getHoTen());
        values.put("MatKhau", thuThu.getMatKhau());
        return db.insert(TABLE_NAME, null, values) > 0;
    }

    public boolean updateThuThu(ThuThu thuThu) {
        ContentValues values = new ContentValues();
        values.put("MaTT", thuThu.getMaTT());
        values.put("HoTen", thuThu.getHoTen());
        values.put("MatKhau", thuThu.getMatKhau());
        return db.update(TABLE_NAME, values, "MaTT = ?",
                new String[]{thuThu.getMaTT()}) > 0;
    }

    public boolean deleteThuThu(ThuThu thuThu) {
        return db.delete(TABLE_NAME, "MaTT = ?",
                new String[]{thuThu.getMaTT()}) > 0;
    }
}
