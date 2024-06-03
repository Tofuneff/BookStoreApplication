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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.Objects;

import fpt.hieudmph47182.bookstoreapplication.R;
import fpt.hieudmph47182.bookstoreapplication.dao.LoaiSachDAO;
import fpt.hieudmph47182.bookstoreapplication.model.LoaiSach;

public class LoaiSachAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<LoaiSach> mLoaiSach;
    private LoaiSachDAO loaiSachDAO;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> mLoaiSach) {
        this.context = context;
        this.mLoaiSach = mLoaiSach;
    }

    @Override
    public int getCount() {
        if (mLoaiSach != null) {
            return mLoaiSach.size();
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
        loaiSachDAO = new LoaiSachDAO(context);
        LoaiSach loaiSach = mLoaiSach.get(i);

        view = LayoutInflater.from(context).inflate(R.layout.loai_sach_item, viewGroup, false);
        TextView tvMaLoaiSach = view.findViewById(R.id.tvMaLoaiSach);
        TextView tvTenLoaiSach = view.findViewById(R.id.tvTenLoaiSach);
        ImageButton imgEdit_ls = view.findViewById(R.id.imgEdit_ls);
        ImageButton imgDelete_ls = view.findViewById(R.id.imgDelete_ls);

        tvMaLoaiSach.setText(String.valueOf(loaiSach.getMaLoai()));
        tvTenLoaiSach.setText(loaiSach.getTenLoaiSach());

        imgDelete_ls.setOnClickListener(v -> deleteDialog(loaiSach));
        imgEdit_ls.setOnClickListener(v -> editDialog(loaiSach));

        return view;
    }

    public void deleteDialog(LoaiSach loaiSach) {
        new AlertDialog.Builder(context)
                .setTitle("Thông báo")
                .setMessage("Bạn có thật sự muốn xóa không?")
                .setNegativeButton("Huỷ", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Đồng ý", (dialog, which) -> {
                    boolean delete = loaiSachDAO.deleteLoaiSach(loaiSach);
                    if (delete) {
                        mLoaiSach.remove(loaiSach);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
    }

    public void editDialog(LoaiSach loaiSach) {
        Dialog dialog = new Dialog(context);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        dialog.setContentView(R.layout.loai_sach_dialog_update);
        EditText edMaLS = dialog.findViewById(R.id.edMaLS);
        EditText edTenLS = dialog.findViewById(R.id.edTenLS);

        AppCompatButton btnSaveLS = dialog.findViewById(R.id.btnSaveLS);
        AppCompatButton btnCancelLS = dialog.findViewById(R.id.btnCancelLS);

        edMaLS.setEnabled(false);

        edMaLS.setText(String.valueOf(loaiSach.getMaLoai()));
        edTenLS.setText(loaiSach.getTenLoaiSach());

        btnCancelLS.setOnClickListener(v -> dialog.dismiss());

        btnSaveLS.setOnClickListener(v -> {
            String tenLS = edTenLS.getText().toString();

            if (tenLS.isEmpty()) {
                Toast.makeText(context, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                loaiSach.setMaLoai(loaiSach.getMaLoai());
                loaiSach.setTenLoaiSach(tenLS);
                boolean update = loaiSachDAO.updateLoaiSach(loaiSach);
                if (update) {
                    notifyDataSetChanged();
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Sửa không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
}
