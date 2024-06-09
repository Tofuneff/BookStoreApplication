package fpt.hieudmph47182.bookstoreapplication.model;

import java.util.Date;

public class PhieuMuon {
    private int maPM;
    private int maTV;
    private String maTT;
    private int maSach;
    private Date ngay;
    private int tienThue;
    private int traSach;

    public PhieuMuon() {
    }

    public PhieuMuon(int tienThue, int traSach) {
        this.tienThue = tienThue;
        this.traSach = traSach;
    }

    public PhieuMuon(int maTV, int maSach, int tienThue, Date ngay) {
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.ngay = ngay;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public int getMaTV() {
        return maTV;
    }

    public int setMaTV(int maTV) {
        this.maTV = maTV;
        return maTV;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public int getMaSach() {
        return maSach;
    }

    public int setMaSach(int maSach) {
        this.maSach = maSach;
        return maSach;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public int getTienThue() {
        return tienThue;
    }

    public int setTienThue(int tienThue) {
        this.tienThue = tienThue;
        return tienThue;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }
}
