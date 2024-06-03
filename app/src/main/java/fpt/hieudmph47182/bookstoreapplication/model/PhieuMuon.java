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

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
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

    public void setMaSach(int maSach) {
        this.maSach = maSach;
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

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }
}
