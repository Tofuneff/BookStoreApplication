package fpt.hieudmph47182.bookstoreapplication.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fpt.hieudmph47182.bookstoreapplication.database.DbHelper;
import fpt.hieudmph47182.bookstoreapplication.model.PhieuMuon;
@SuppressLint({"SimpleDateFormat", "Recycle"})
public class PhieuMuonDAO {
    private final DbHelper dbHelper;
    private final SQLiteDatabase db;
    private static final String TABLE_NAME = "PhieuMuon";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public PhieuMuonDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<PhieuMuon> getPMbyMaSach(int maSach) {
        ArrayList<PhieuMuon> phieuMuons = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE MaSach=?",
                new String[]{String.valueOf(maSach)});
        while (cursor.moveToNext()) {
            PhieuMuon phieuMuon = new PhieuMuon(cursor.getInt(5), cursor.getInt(6));
            phieuMuon.setMaPM(cursor.getInt(0));
            phieuMuon.setMaTV(cursor.getInt(1));
            phieuMuon.setMaTT(cursor.getString(2));
            phieuMuon.setMaSach(cursor.getInt(3));
            try {
                phieuMuon.setNgay(sdf.parse(cursor.getString(4)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            phieuMuon.setTienThue(cursor.getInt(5));
            phieuMuon.setTraSach(cursor.getInt(6));
            phieuMuons.add(phieuMuon);
        }
        return phieuMuons;
    }

    public ArrayList<PhieuMuon> getPMbyMaTV(int maTV) {
        ArrayList<PhieuMuon> phieuMuons = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE MaTV=?",
                new String[]{String.valueOf(maTV)});
        while (cursor.moveToNext()) {
            PhieuMuon phieuMuon = new PhieuMuon(cursor.getInt(5), cursor.getInt(6));
            phieuMuon.setMaPM(cursor.getInt(0));
            phieuMuon.setMaTV(cursor.getInt(1));
            phieuMuon.setMaTT(cursor.getString(2));
            phieuMuon.setMaSach(cursor.getInt(3));
            try {
                phieuMuon.setNgay(sdf.parse(cursor.getString(4)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            phieuMuon.setTienThue(cursor.getInt(5));
            phieuMuon.setTraSach(cursor.getInt(6));
            phieuMuons.add(phieuMuon);
        }
        return phieuMuons;
    }

    public ArrayList<PhieuMuon> getAllPhieuMuon() {
        ArrayList<PhieuMuon> phieuMuons = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            PhieuMuon phieuMuon = new PhieuMuon(cursor.getInt(5), cursor.getInt(6));
            phieuMuon.setMaPM(cursor.getInt(0));
            phieuMuon.setMaTV(cursor.getInt(1));
            phieuMuon.setMaTT(cursor.getString(2));
            phieuMuon.setMaSach(cursor.getInt(3));
            try {
                phieuMuon.setNgay(sdf.parse(cursor.getString(4)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            phieuMuon.setTienThue(cursor.getInt(5));
            phieuMuon.setTraSach(cursor.getInt(6));
            phieuMuons.add(phieuMuon);
        }
        return phieuMuons;
    }

    public boolean insertPhieuMuon(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put("MaTV", phieuMuon.getMaTV());
        values.put("MaSach", phieuMuon.getMaSach());
        values.put("Ngay", sdf.format(phieuMuon.getNgay()));
        values.put("TienThue", phieuMuon.getTienThue());
        values.put("TraSach", phieuMuon.getTraSach());
        return db.insert(TABLE_NAME, null, values) > 0;
    }

    public boolean updatePhieuMuon(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put("MaTV", phieuMuon.getMaTV());
        values.put("MaSach", phieuMuon.getMaSach());
        values.put("Ngay", sdf.format(phieuMuon.getNgay()));
        values.put("TienThue", phieuMuon.getTienThue());
        values.put("TraSach", phieuMuon.getTraSach());
        return db.update(TABLE_NAME, values, "MaPM = ?",
                new String[]{String.valueOf(phieuMuon.getMaPM())}) > 0;
    }

    public boolean deletePhieuMuon(PhieuMuon phieuMuon) {
        return db.delete(TABLE_NAME, "MaPM = ?",
                new String[]{String.valueOf(phieuMuon.getMaPM())}) > 0;
    }
}
