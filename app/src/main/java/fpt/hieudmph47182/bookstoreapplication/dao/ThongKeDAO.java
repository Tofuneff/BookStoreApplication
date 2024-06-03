package fpt.hieudmph47182.bookstoreapplication.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fpt.hieudmph47182.bookstoreapplication.database.DbHelper;
import fpt.hieudmph47182.bookstoreapplication.model.Sach;
import fpt.hieudmph47182.bookstoreapplication.model.Top;

public class ThongKeDAO {
    private DbHelper dbHelper;
    private final SQLiteDatabase db;
    private final Context context;
    private static final String sqlTop =
            "SELECT MaSach, count(MaSach) as soLuong FROM PhieuMuon GROUP BY MaSach ORDER BY soLuong DESC LIMIT 10";
    private static final String sqlDoanhThu =
            "SELECT SUM(TienThue) as doanhThu FROM PhieuMuon WHERE Ngay BETWEEN ? AND ?";

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ThongKeDAO(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<Top> getTop() {
        ArrayList<Top> listTop = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(context);
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sqlTop, null);
        while (cursor.moveToNext()) {
            Top top = new Top();
            Sach sach = sachDAO.getTVbyMaSach(cursor.getInt(0));
            top.setTenSach(sach.getTenSach());
            top.setSoLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soLuong"))));
            listTop.add(top);
        }
        return listTop;
    }

    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay) {
        ArrayList<Integer> list = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sqlDoanhThu, new String[]{tuNgay, denNgay});
        while (cursor.moveToNext()) {
            try {
                list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("doanhThu"))));
            } catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }
}
