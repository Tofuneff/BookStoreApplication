package fpt.hieudmph47182.bookstoreapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import fpt.hieudmph47182.bookstoreapplication.R;
import fpt.hieudmph47182.bookstoreapplication.adapter.TopAdapter;
import fpt.hieudmph47182.bookstoreapplication.dao.ThongKeDAO;
import fpt.hieudmph47182.bookstoreapplication.model.Top;

public class ThongKeFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thong_ke, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ThongKeDAO thongKeDAO = new ThongKeDAO(requireActivity());
        List<Top> topList = thongKeDAO.getTop();
        TopAdapter topAdapter = new TopAdapter(requireActivity(), topList);
        ListView listView = view.findViewById(R.id.lv_thongKe);
        listView.setAdapter(topAdapter);
    }
}