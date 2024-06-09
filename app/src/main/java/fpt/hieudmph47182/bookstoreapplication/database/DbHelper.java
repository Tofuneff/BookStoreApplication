package fpt.hieudmph47182.bookstoreapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String NAME_DB = "bookstore.db";
    private static final int VERSION_DB = 4;
    private static final String TABLE_THUTHU = "ThuThu";
    private static final String TABLE_THANHVIEN = "ThanhVien";
    private static final String TABLE_SACH = "Sach";
    private static final String TABLE_LOAISACH = "LoaiSach";
    private static final String TABLE_PHIEUMUON = "PhieuMuon";
    private static final String ACCOUNT_ADMIN =
            "INSERT INTO " + TABLE_THUTHU + " VALUES ('admin', 'admin', 'admin')," +
                    "('hieudm', 'thuthu', '123456')";
    private static final String createTableThuThu =
            "CREATE TABLE " + TABLE_THUTHU +
            "(MaTT TEXT PRIMARY KEY," +
            "HoTen TEXT NOT NULL," +
            "MatKhau TEXT NOT NULL)";
    private static final String dropTableThuThu = "DROP TABLE IF EXISTS " + TABLE_THUTHU;
    private static final String createTableThanhVien =
            "CREATE TABLE " + TABLE_THANHVIEN +
            "(MaTV INTEGER PRIMARY KEY AUTOINCREMENT," +
            "HoTen TEXT NOT NULL," +
            "NamSinh TEXT NOT NULL)";
    private static final String dropTableThanhVien = "DROP TABLE IF EXISTS " + TABLE_THANHVIEN;
    private static final String createTableSach =
            "CREATE TABLE " + TABLE_SACH +
            "(MaSach INTEGER PRIMARY KEY AUTOINCREMENT," +
            "TenSach TEXT NOT NULL," +
            "GiaThue INTEGER NOT NULL," +
            "MaLoai INTEGER REFERENCES LoaiSach(MaLoai))";
    private static final String dropTableSach = "DROP TABLE IF EXISTS " + TABLE_SACH;
    private static final String createTableLoaiSach =
            "CREATE TABLE " + TABLE_LOAISACH +
            "(MaLoai INTEGER PRIMARY KEY AUTOINCREMENT," +
            "HoTen TEXT NOT NULL)";
    private static final String dropTableLoaiSach = "DROP TABLE IF EXISTS " + TABLE_LOAISACH;
    private static final String createTablePhieuMuon =
            "CREATE TABLE " + TABLE_PHIEUMUON +
            "(MaPM INTEGER PRIMARY KEY AUTOINCREMENT," +
            "MaTV INTEGER REFERENCES ThanhVien(MaTV)," +
            "MaTT TEXT REFERENCES ThuThu(MaTT)," +
            "MaSach INTEGER REFERENCES Sach(MaSach)," +
            "Ngay DATE NOT NULL," +
            "TienThue INTEGER NOT NULL," +
            "TraSach INTEGER NOT NULL)";
    private static final String dropTablePhieuMuon = "DROP TABLE IF EXISTS " + TABLE_PHIEUMUON;

    public DbHelper(@Nullable Context context) {
        super(context, NAME_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableThuThu);
        db.execSQL(createTableThanhVien);
        db.execSQL(createTableSach);
        db.execSQL(createTableLoaiSach);
        db.execSQL(createTablePhieuMuon);
        db.execSQL(ACCOUNT_ADMIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropTableThuThu);
        db.execSQL(dropTableThanhVien);
        db.execSQL(dropTableSach);
        db.execSQL(dropTableLoaiSach);
        db.execSQL(dropTablePhieuMuon);
        onCreate(db);
    }
}
