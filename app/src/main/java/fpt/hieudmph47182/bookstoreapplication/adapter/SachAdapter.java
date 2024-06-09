package fpt.hieudmph47182.bookstoreapplication.adapter;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.Objects;

import fpt.hieudmph47182.bookstoreapplication.R;
import fpt.hieudmph47182.bookstoreapplication.dao.LoaiSachDAO;
import fpt.hieudmph47182.bookstoreapplication.dao.PhieuMuonDAO;
import fpt.hieudmph47182.bookstoreapplication.dao.SachDAO;
import fpt.hieudmph47182.bookstoreapplication.model.LoaiSach;
import fpt.hieudmph47182.bookstoreapplication.model.Sach;

public class SachAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Sach> mSach;
    private ArrayList<LoaiSach> mLoaiSach;
    private SachDAO sachDAO;
    private LoaiSachDAO loaiSachDAO;
    private PhieuMuonDAO phieuMuonDAO;
    private int maLoaiSach, spinner_position;

    public SachAdapter(Context context, ArrayList<Sach> mSach) {
        this.context = context;
        this.mSach = mSach;
    }

    @Override
    public int getCount() {
        if (mSach != null) {
            return mSach.size();
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

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        sachDAO = new SachDAO(context);
        loaiSachDAO = new LoaiSachDAO(context);
        phieuMuonDAO = new PhieuMuonDAO(context);

        Sach sach = mSach.get(i);

        view = LayoutInflater.from(context).inflate(R.layout.sach_item, viewGroup, false);
        ImageView imgSach = view.findViewById(R.id.imgSach);
        TextView tvMaSach = view.findViewById(R.id.tvMaSach);
        TextView tvTenSach = view.findViewById(R.id.tvTenSach);
        TextView tvGiaThue = view.findViewById(R.id.tvGiaThue);
        TextView tvLoaiSach = view.findViewById(R.id.tvLoaiSach);
        ImageButton imgEditSach = view.findViewById(R.id.imgEditSach);
        ImageButton imgDeleteSach = view.findViewById(R.id.imgDeleteSach);

        imgSach.setImageResource(R.drawable.img_book);
        tvMaSach.setText(String.valueOf(sach.getMaSach()));
        tvTenSach.setText(sach.getTenSach());
        tvGiaThue.setText(String.valueOf(sach.getGiaThue()));
        tvLoaiSach.setText(String.valueOf(sach.getMaLoai()));

        imgEditSach.setOnClickListener(v -> editDialog(sach));
        imgDeleteSach.setOnClickListener(v -> deleteDialog(sach));

        return view;
    }

    public void deleteDialog(Sach sach) {
        new AlertDialog.Builder(context)
                .setTitle("Thông báo")
                .setMessage("Bạn có thật sự muốn xóa không?")
                .setNegativeButton("Huỷ", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Đồng ý", (dialog, which) -> {
                    if (!phieuMuonDAO.getPMbyMaSach(sach.getMaSach()).isEmpty()) {
                        Toast.makeText(context, "Bạn không thể xoá sách khi nó vẫn đang trong phiếu mượn", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    boolean delete = sachDAO.deleteSach(sach);
                    if (delete) {
                        mSach.remove(sach);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
    }

    public void editDialog(Sach sach) {
        Dialog dialog = new Dialog(context);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.sach_dialog_update);
        EditText edMaSach = dialog.findViewById(R.id.edMaSach);
        EditText edTenSach = dialog.findViewById(R.id.edTenSach);
        EditText edGiaThue = dialog.findViewById(R.id.edGiaThue);
        Spinner spSach = dialog.findViewById(R.id.spSach);
        mLoaiSach = loaiSachDAO.getLoaiSach();
        LoaiSachSpinnerAdapter spinnerAdapter = new LoaiSachSpinnerAdapter(context, mLoaiSach);
        spSach.setAdapter(spinnerAdapter);

        AppCompatButton btnSaveSach = dialog.findViewById(R.id.btnSaveSach);
        AppCompatButton btnCancelSach = dialog.findViewById(R.id.btnCancelSach);

        edMaSach.setEnabled(false);

        edMaSach.setText(String.valueOf(sach.getMaSach()));
        edTenSach.setText(sach.getTenSach());
        edGiaThue.setText(String.valueOf(sach.getGiaThue()));

        for (int j = 0; j < mLoaiSach.size(); j++) {
            if (mLoaiSach.get(j).getMaLoai() == sach.getMaLoai()) {
                spinner_position = j;
            }
        }
        spSach.setSelection(spinner_position);

        spSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                maLoaiSach = mLoaiSach.get(i).getMaLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCancelSach.setOnClickListener(v -> dialog.dismiss());

        btnSaveSach.setOnClickListener(v -> {
            String tenSach = edTenSach.getText().toString();
            String giaThue = edGiaThue.getText().toString();

            if (tenSach.isEmpty() || giaThue.isEmpty()) {
                Toast.makeText(context, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                sach.setMaLoai(maLoaiSach);
                sach.setTenSach(tenSach);
                sach.setGiaThue(Integer.parseInt(giaThue));
                boolean update = sachDAO.updateSach(sach);
                if (update) {
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Sửa không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
}
