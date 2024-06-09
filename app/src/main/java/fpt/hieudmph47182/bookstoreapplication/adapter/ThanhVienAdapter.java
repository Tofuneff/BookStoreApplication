package fpt.hieudmph47182.bookstoreapplication.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import fpt.hieudmph47182.bookstoreapplication.R;
import fpt.hieudmph47182.bookstoreapplication.dao.PhieuMuonDAO;
import fpt.hieudmph47182.bookstoreapplication.dao.ThanhVienDAO;
import fpt.hieudmph47182.bookstoreapplication.model.ThanhVien;

public class ThanhVienAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<ThanhVien> mThanhVien;
    private ThanhVienDAO thanhVienDAO;
    private PhieuMuonDAO phieuMuonDAO;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> mThanhVien) {
        this.context = context;
        this.mThanhVien = mThanhVien;
    }

    @Override
    public int getCount() {
        if (mThanhVien != null) {
            return mThanhVien.size();
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
        thanhVienDAO = new ThanhVienDAO(context);
        phieuMuonDAO = new PhieuMuonDAO(context);
        ThanhVien thanhVien = mThanhVien.get(i);

        view = LayoutInflater.from(context).inflate(R.layout.thanh_vien_item, viewGroup, false);
        ImageView imgTV = view.findViewById(R.id.imgTV);
        TextView tvMaTV = view.findViewById(R.id.tvMaTV);
        TextView tvTenThanhVien = view.findViewById(R.id.tvTenThanhVien);
        TextView tvNamSinh = view.findViewById(R.id.tvNamSinh);
        ImageButton imgDeleteTV = view.findViewById(R.id.imgDeleteTV);
        ImageButton imgEditTV = view.findViewById(R.id.imgEditTV);

        Random randomImages = new Random();
        int rndInt = randomImages.nextInt(4) + 1;
        String imgName = "avatar" + rndInt;
        int resourceId = context.getResources().getIdentifier(imgName, "drawable", context.getPackageName());
        imgTV.setImageResource(resourceId);

        tvMaTV.setText(String.valueOf(thanhVien.getMaTV()));
        tvTenThanhVien.setText(thanhVien.getHoTen());
        tvNamSinh.setText(thanhVien.getNamSinh());

        imgDeleteTV.setOnClickListener(v -> deleteDialog(thanhVien));

        imgEditTV.setOnClickListener(v -> editDialog(thanhVien));

        return view;
    }

    public void deleteDialog(ThanhVien thanhVien) {
        new AlertDialog.Builder(context)
                .setTitle("Thông báo")
                .setMessage("Bạn có thật sự muốn xóa không?")
                .setNegativeButton("Huỷ", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Đồng ý", (dialog, which) -> {
                    if (!phieuMuonDAO.getPMbyMaTV(thanhVien.getMaTV()).isEmpty()) {
                        Toast.makeText(context, "Bạn không thể xoá thành viên khi họ vẫn đang trong phiếu mượn", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    boolean delete = thanhVienDAO.deleteThanhVien(thanhVien);
                    if (delete) {
                        mThanhVien.remove(thanhVien);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
    }

    public void editDialog(ThanhVien thanhVien) {
        Dialog dialog = new Dialog(context);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        dialog.setContentView(R.layout.thanh_vien_dialog_update);
        EditText edMaTV = dialog.findViewById(R.id.edMaTV1);
        EditText edTenTV = dialog.findViewById(R.id.edTenTV1);
        EditText edNamSinh = dialog.findViewById(R.id.edNamSinh1);

        AppCompatButton btnSaveTV = dialog.findViewById(R.id.btnSaveTV);
        AppCompatButton btnCancelTV = dialog.findViewById(R.id.btnCancelTV);

        edMaTV.setEnabled(false);

        edMaTV.setText(String.valueOf(thanhVien.getMaTV()));
        edTenTV.setText(thanhVien.getHoTen());
        edNamSinh.setText(thanhVien.getNamSinh());

        btnCancelTV.setOnClickListener(v -> dialog.dismiss());

        btnSaveTV.setOnClickListener(v -> {
            String tenTV = edTenTV.getText().toString();
            String namSinh = edNamSinh.getText().toString();

            if (tenTV.isEmpty() || namSinh.isEmpty()) {
                Toast.makeText(context, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                thanhVien.setHoTen(tenTV);
                thanhVien.setNamSinh(namSinh);
                thanhVien.setMaTV(thanhVien.getMaTV());
                boolean update = thanhVienDAO.updateThanhVien(thanhVien);
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
