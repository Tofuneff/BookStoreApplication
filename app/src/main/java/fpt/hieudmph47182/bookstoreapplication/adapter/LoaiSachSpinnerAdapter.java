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
import fpt.hieudmph47182.bookstoreapplication.model.LoaiSach;

public class LoaiSachSpinnerAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<LoaiSach> mLoaiSach;

    public LoaiSachSpinnerAdapter(Context context, ArrayList<LoaiSach> mLoaiSach) {
        this.context = context;
        this.mLoaiSach = mLoaiSach;
    }

    @Override
    public int getCount() {
        if (mLoaiSach != null)
            return mLoaiSach.size();
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

    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LoaiSach loaiSach = mLoaiSach.get(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.loai_sach_spinner_item, viewGroup, false);
            TextView title = view.findViewById(R.id.tv_ls_title);
            TextView id = view.findViewById(R.id.tv_ls_id);
            if (loaiSach != null) {
                id.setText(loaiSach.getMaLoai() + ". ");
                title.setText(loaiSach.getTenLoaiSach());
            }
        }
        return view;
    }
}
