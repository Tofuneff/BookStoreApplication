package fpt.hieudmph47182.bookstoreapplication.adapter;

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

import java.util.List;
import java.util.Objects;

import fpt.hieudmph47182.bookstoreapplication.R;
import fpt.hieudmph47182.bookstoreapplication.dao.ThuThuDAO;
import fpt.hieudmph47182.bookstoreapplication.model.ThuThu;

public class ThuThuAdapter extends BaseAdapter {
    private final Context context;
    private final List<ThuThu> mThuThu;
    private ThuThuDAO thuThuDAO;

    public ThuThuAdapter(Context context, List<ThuThu> mThuThu) {
        this.context = context;
        this.mThuThu = mThuThu;
    }

    @Override
    public int getCount() {
        if (mThuThu != null) {
            return mThuThu.size();
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
        ThuThu thuThu = mThuThu.get(i);
        thuThuDAO = new ThuThuDAO(context);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.thu_thu_item, parent, false);
        }
        if (thuThu != null) {
            TextView tv_maThuThu = view.findViewById(R.id.tv_maThuThu);
            tv_maThuThu.setText(thuThu.getMaTT());
            TextView tv_tenThuThu = view.findViewById(R.id.tv_tenThuThu);
            tv_tenThuThu.setText(thuThu.getHoTen());
            TextView tv_passThuThu = view.findViewById(R.id.tv_passThuThu);
            tv_passThuThu.setText(thuThu.getMatKhau());
            ImageButton imgEdit_tt = view.findViewById(R.id.imgEdit_tt);
            ImageButton imgDelete_tt = view.findViewById(R.id.imgDelete_tt);
            ImageView img_tt = view.findViewById(R.id.img_tt);

            boolean isAdmin = thuThu.getMaTT().equals("admin");
            if (!isAdmin) {
                img_tt.setImageResource(R.drawable.avatar4);
            }

            imgDelete_tt.setOnClickListener(v -> {
                deleteDialog(thuThu);
            });
            imgEdit_tt.setOnClickListener(v -> {
                editDialog(thuThu);
            });
        }
        return view;
    }

    public void deleteDialog(ThuThu thuThu) {
        new AlertDialog.Builder(context)
                .setTitle("Thông báo")
                .setMessage("Bạn có thật sự muốn xóa không?")
                .setNegativeButton("Huỷ", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Đồng ý", (dialog, which) -> {
                    boolean delete = thuThuDAO.deleteThuThu(thuThu);
                    if (delete) {
                        mThuThu.remove(thuThu);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
    }

    public void editDialog(ThuThu thuThu) {
        Dialog dialog = new Dialog(context);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.thu_thu_dialog_update);
        ImageView ivThuThu = dialog.findViewById(R.id.ivThuThu);
        EditText edMaTT = dialog.findViewById(R.id.edMaTT);
        EditText edTenTT = dialog.findViewById(R.id.edTenTT);
        EditText edMatKhau = dialog.findViewById(R.id.edMatKhau);

        AppCompatButton btnSaveTT = dialog.findViewById(R.id.btnSaveTT);
        AppCompatButton btnCancelTT = dialog.findViewById(R.id.btnCancelTT);

        boolean isAdmin = thuThu.getMaTT().equals("admin");
        if (!isAdmin) {
            ivThuThu.setImageResource(R.drawable.avatar4);
        }

        edMaTT.setText(thuThu.getMaTT());
        edTenTT.setText(thuThu.getHoTen());
        edMatKhau.setText(thuThu.getMatKhau());

        edMatKhau.setEnabled(false);
        edMaTT.setEnabled(false);

        btnCancelTT.setOnClickListener(v -> dialog.dismiss());

        btnSaveTT.setOnClickListener(v -> {
            String tenTT = edTenTT.getText().toString();

            if (tenTT.isEmpty()) {
                Toast.makeText(context, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                thuThu.setHoTen(tenTT);
                boolean update = thuThuDAO.updateThuThu(thuThu);
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
