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
import fpt.hieudmph47182.bookstoreapplication.model.Sach;

public class SachSpinnerAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Sach> mSach;

    public SachSpinnerAdapter(Context context, ArrayList<Sach> mSach) {
        this.context = context;
        this.mSach = mSach;
    }

    @Override
    public int getCount() {
        if (mSach != null)
            return mSach.size();
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
        Sach sach = mSach.get(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.sach_spinner_item, viewGroup, false);
            TextView title = view.findViewById(R.id.tv_sach_title);
            TextView id = view.findViewById(R.id.tv_sach_id);
            if (sach != null) {
                id.setText(sach.getMaSach() + ". ");
                title.setText(sach.getTenSach());
            }
        }
        return view;
    }
}
