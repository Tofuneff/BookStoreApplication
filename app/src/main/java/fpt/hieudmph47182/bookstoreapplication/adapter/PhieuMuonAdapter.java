package fpt.hieudmph47182.bookstoreapplication.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import fpt.hieudmph47182.bookstoreapplication.R;
import fpt.hieudmph47182.bookstoreapplication.dao.PhieuMuonDAO;
import fpt.hieudmph47182.bookstoreapplication.dao.SachDAO;
import fpt.hieudmph47182.bookstoreapplication.dao.ThanhVienDAO;
import fpt.hieudmph47182.bookstoreapplication.model.PhieuMuon;
import fpt.hieudmph47182.bookstoreapplication.model.Sach;
import fpt.hieudmph47182.bookstoreapplication.model.ThanhVien;

@SuppressLint({"SimpleDateFormat", "ViewHolder", "SetTextI18n"})
public class PhieuMuonAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<PhieuMuon> mPhieuMuon;
    private PhieuMuon phieuMuon;
    private ArrayList<ThanhVien> mThanhVien;
    private ArrayList<Sach> mSach;
    private SachDAO sachDAO;
    private ThanhVienDAO thanhVienDAO;
    private PhieuMuonDAO phieuMuonDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("yyy/MM/dd");
    private int maThanhVien;
    private int maSach;
    private int tienThue;
    private int positionTV;
    private int positionSach;
    private TextView tvMaPM, tvTenTV, tvTenSach_pm, tvTienThue, tvStatus, tvNgayPM;
    private ImageButton imgEditPM, imgDeletePM;

    public PhieuMuonAdapter(Context context, ArrayList<PhieuMuon> mPhieuMuon) {
        this.context = context;
        this.mPhieuMuon = mPhieuMuon;
    }

    @Override
    public int getCount() {
        if (mPhieuMuon != null) {
            return mPhieuMuon.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        sachDAO = new SachDAO(context);
        thanhVienDAO = new ThanhVienDAO(context);
        phieuMuonDAO = new PhieuMuonDAO(context);
        phieuMuon = mPhieuMuon.get(i);

        Sach sach = sachDAO.getSachbyMaSach(phieuMuon.getMaSach());
        ThanhVien thanhVien = thanhVienDAO.getTVbyMaTV(phieuMuon.getMaTV());

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.phieu_muon_item, parent, false);
            tvMaPM = view.findViewById(R.id.tvMaPM);
            tvTenTV = view.findViewById(R.id.tvTenTV);
            tvTenSach_pm = view.findViewById(R.id.tvTenSach_pm);
            tvTienThue = view.findViewById(R.id.tvTienThue);
            tvStatus = view.findViewById(R.id.tvStatus);
            tvNgayPM = view.findViewById(R.id.tvNgayPM);
            imgEditPM = view.findViewById(R.id.imgEditPM);
            imgDeletePM = view.findViewById(R.id.imgDeletePM);
        }

        tvMaPM.setText(String.valueOf(phieuMuon.getMaPM()));
        tvTenTV.setText(thanhVien.getHoTen());
        tvTenSach_pm.setText(sach.getTenSach());
        tvTienThue.setText(String.valueOf(phieuMuon.getTienThue()));
        tvNgayPM.setText(sdf.format(phieuMuon.getNgay()));

        if (phieuMuon.getTraSach() == 1) {
            tvStatus.setTextColor(Color.BLUE);
            tvStatus.setText("Đã trả sách");
        } else {
            tvStatus.setTextColor(Color.RED);
            tvStatus.setText("Chưa trả sách");
        }

        imgDeletePM.setOnClickListener(v -> deleteDialog(phieuMuon));
        imgEditPM.setOnClickListener(v -> editDialog(phieuMuon));

        return view;
    }

    public void deleteDialog(PhieuMuon phieuMuon) {
        new AlertDialog.Builder(context)
                .setTitle("Thông báo")
                .setMessage("Bạn có thật sự muốn xóa không?")
                .setNegativeButton("Huỷ",
                        (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Đồng ý",
                        (dialog, which) -> {
                            boolean delete = phieuMuonDAO.deletePhieuMuon(phieuMuon);
                            if (delete) {
                                mPhieuMuon.remove(phieuMuon);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }).create().show();
    }

    public void editDialog(@NonNull PhieuMuon phieuMuon) {
        Dialog dialog = new Dialog(context);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.phieu_muon_dialog_update);
        EditText edMaPM = dialog.findViewById(R.id.edMaPM);
        TextView tvNgay = dialog.findViewById(R.id.tvNgay);
        TextView tv_tienThue = dialog.findViewById(R.id.tv_tienThue);
        CheckBox ckbTraSach = dialog.findViewById(R.id.ckbTraSach);
        Spinner spTV = dialog.findViewById(R.id.spTV);
        Spinner spSach = dialog.findViewById(R.id.spnSach);

        mThanhVien = thanhVienDAO.getThanhVien();
        ThanhVienSpinnerAdapter thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(context, mThanhVien);
        spTV.setAdapter(thanhVienSpinnerAdapter);

        mSach = sachDAO.getSach();
        SachSpinnerAdapter sachSpinnerAdapter = new SachSpinnerAdapter(context, mSach);
        spSach.setAdapter(sachSpinnerAdapter);

        AppCompatButton btnSavePM = dialog.findViewById(R.id.btnSavePM);
        AppCompatButton btnCancelPM = dialog.findViewById(R.id.btnCancelPM);

        edMaPM.setEnabled(false);

        edMaPM.setText(String.valueOf(phieuMuon.getMaPM()));
        tvNgay.setText(sdf.format(new Date()));
        tv_tienThue.setText(String.valueOf(phieuMuon.getTienThue()));

        for (int j = 0; j < mThanhVien.size(); j++) {
            if (mThanhVien.get(j).getMaTV() == phieuMuon.getMaTV()) {
                positionTV = j;
            }
        }
        spTV.setSelection(positionTV);

        for (int j = 0; j < mSach.size(); j++) {
            if (mSach.get(j).getMaSach() == phieuMuon.getMaSach()) {
                positionSach = j;
            }
        }
        spSach.setSelection(positionSach);

        ckbTraSach.setChecked(phieuMuon.getTraSach() == 1);

        spTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                maThanhVien = mThanhVien.get(i).getMaTV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                maSach = mSach.get(i).getMaSach();
                tienThue = mSach.get(i).getGiaThue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCancelPM.setOnClickListener(v -> dialog.dismiss());

        btnSavePM.setOnClickListener(v -> {
            phieuMuon.setMaSach(maSach);
            phieuMuon.setMaTV(maThanhVien);
            phieuMuon.setNgay(new Date());
            phieuMuon.setTienThue(tienThue);
            phieuMuon.setTraSach(ckbTraSach.isChecked() ? 1 : 0);
            boolean update = phieuMuonDAO.updatePhieuMuon(phieuMuon);
            if (update) {
                notifyDataSetChanged();
                Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(context, "Sửa không thành công", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}
