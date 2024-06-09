package fpt.hieudmph47182.bookstoreapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fpt.hieudmph47182.bookstoreapplication.R;
import fpt.hieudmph47182.bookstoreapplication.model.Top;

public class TopAdapter extends BaseAdapter {
    private final Context context;
    private final List<Top> topList;

    public TopAdapter(Context context, List<Top> topList) {
        this.context = context;
        this.topList = topList;
    }

    @Override
    public int getCount() {
        if (topList != null) {
            return topList.size();
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
        Top top = topList.get(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.thong_ke_item, parent, false);
        }
        if (top != null) {
            ImageView img_sach = view.findViewById(R.id.img_sach);
            img_sach.setImageResource(R.drawable.img_book);
            TextView tv_maSach = view.findViewById(R.id.tv_maSach);
            tv_maSach.setText(String.valueOf(top.getMaSach()));
            TextView tv_tenSach_tk = view.findViewById(R.id.tv_tenSach_tk);
            tv_tenSach_tk.setText(top.getTenSach());
            TextView tv_soLuong = view.findViewById(R.id.tv_soLuong);
            tv_soLuong.setText(String.valueOf(top.getSoLuong()));
        }
        return view;
    }
}
