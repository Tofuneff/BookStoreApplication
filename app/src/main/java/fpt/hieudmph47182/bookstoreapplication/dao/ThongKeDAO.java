package fpt.hieudmph47182.bookstoreapplication.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpt.hieudmph47182.bookstoreapplication.database.DbHelper;
import fpt.hieudmph47182.bookstoreapplication.model.Sach;
import fpt.hieudmph47182.bookstoreapplication.model.Top;

@SuppressLint({"Range", "Recycle", "SimpleDateFormat"})
public class ThongKeDAO {
    private DbHelper dbHelper;
    private final SQLiteDatabase db;
    private final Context context;

    public ThongKeDAO(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Top> getTop() {
        String queryTop = "SELECT MaSach, count(MaSach) as soLuong FROM PhieuMuon GROUP BY MaSach ORDER BY soLuong DESC LIMIT 10";
        ArrayList<Top> listTop = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(context);
        Cursor cursor = db.rawQuery(queryTop, null);
        while (cursor.moveToNext()) {
            Sach sach = sachDAO.getSachbyMaSach(cursor.getInt(0));
            Top top = new Top(sach.getTenSach(), cursor.getInt(1));
            top.setMaSach(sach.getMaSach());
            listTop.add(top);
        }
        return listTop;
    }

    public int getDoanhThu(String tuNgay, String denNgay) {
        ArrayList<Integer> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT SUM(TienThue) as DoanhThu FROM PhieuMuon WHERE Ngay BETWEEN ? AND ?", new String[]{tuNgay, denNgay});
        while (cursor.moveToNext()) {
            try {
                list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("DoanhThu"))));
            } catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }
}
