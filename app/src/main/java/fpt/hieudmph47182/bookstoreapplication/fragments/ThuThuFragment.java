package fpt.hieudmph47182.bookstoreapplication.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Objects;

import fpt.hieudmph47182.bookstoreapplication.adapter.ThuThuAdapter;
import fpt.hieudmph47182.bookstoreapplication.dao.ThuThuDAO;
import fpt.hieudmph47182.bookstoreapplication.databinding.FragmentThuThuBinding;
import fpt.hieudmph47182.bookstoreapplication.model.ThuThu;


public class ThuThuFragment extends Fragment {
    private FragmentThuThuBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThuThuBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshActivity();
    }

    private void refreshActivity() {
        ThuThuDAO thuThuDAO = new ThuThuDAO(requireActivity());
        ArrayList<ThuThu> mThuThu = thuThuDAO.getAllThuThu();
        ThuThuAdapter thuThuAdapter = new ThuThuAdapter(requireActivity(), mThuThu);
        binding.lvThuThu.setAdapter(thuThuAdapter);
    }
}