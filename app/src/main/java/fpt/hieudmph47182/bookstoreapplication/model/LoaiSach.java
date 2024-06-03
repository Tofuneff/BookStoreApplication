package fpt.hieudmph47182.bookstoreapplication.model;

public class LoaiSach {
    private int maLoai;
    private String tenLoaiSach;

    public LoaiSach() {
    }

    public LoaiSach(int maLoai, String tenLoaiSach) {
        this.maLoai = maLoai;
        this.tenLoaiSach = tenLoaiSach;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoaiSach() {
        return tenLoaiSach;
    }

    public void setTenLoaiSach(String tenLoaiSach) {
        this.tenLoaiSach = tenLoaiSach;
    }
}
