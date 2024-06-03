package fpt.hieudmph47182.bookstoreapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpt.hieudmph47182.bookstoreapplication.R;
import fpt.hieudmph47182.bookstoreapplication.model.ThanhVien;

public class ThanhVienSpinnerAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<ThanhVien> mThanhVien;

    public ThanhVienSpinnerAdapter(Context context, ArrayList<ThanhVien> mThanhVien) {
        this.context = context;
        this.mThanhVien = mThanhVien;
    }

    @Override
    public int getCount() {
        if (mThanhVien != null)
            return mThanhVien.size();
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.thanh_vien_spinner_item, viewGroup, false);
            TextView title = view.findViewById(R.id.tv_thanhVien_title);
            TextView id = view.findViewById(R.id.tv_thanhVien_id);
            id.setText(mThanhVien.get(i).getMaTV() + ". ");
            title.setText(mThanhVien.get(i).getHoTen());
        }
        return view;
    }
}
