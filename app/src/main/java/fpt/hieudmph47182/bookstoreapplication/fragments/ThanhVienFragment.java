package fpt.hieudmph47182.bookstoreapplication.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import fpt.hieudmph47182.bookstoreapplication.R;
import fpt.hieudmph47182.bookstoreapplication.adapter.ThanhVienAdapter;
import fpt.hieudmph47182.bookstoreapplication.dao.ThanhVienDAO;
import fpt.hieudmph47182.bookstoreapplication.databinding.FragmentThanhVienBinding;
import fpt.hieudmph47182.bookstoreapplication.model.ThanhVien;

public class ThanhVienFragment extends Fragment {
    private FragmentThanhVienBinding binding;
    private ThanhVienDAO thanhVienDAO;
    private ThanhVienAdapter thanhVienAdapter;
    private ThanhVien thanhVien;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThanhVienBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        thanhVienDAO = new ThanhVienDAO(requireActivity());
        refreshActivity();
        binding.fabAddThanhVien.setOnClickListener(v -> addDialog());
    }

    private void addDialog() {
        Dialog dialog = new Dialog(requireActivity());
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        dialog.setContentView(R.layout.thanh_vien_dialog_add);
        EditText edMaTV = dialog.findViewById(R.id.edMaTV);
        EditText edTenTV = dialog.findViewById(R.id.edTenTV);
        EditText edNamSinh = dialog.findViewById(R.id.edNamSinh);

        AppCompatButton btnSaveTV = dialog.findViewById(R.id.btnSaveTV);
        AppCompatButton btnCancelTV = dialog.findViewById(R.id.btnCancelTV);

        edMaTV.setEnabled(false);

        btnCancelTV.setOnClickListener(v -> dialog.dismiss());

        btnSaveTV.setOnClickListener(v -> {
            String tenTV = edTenTV.getText().toString();
            String namSinh = edNamSinh.getText().toString();

            if (tenTV.isEmpty() || namSinh.isEmpty()) {
                Toast.makeText(requireActivity(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                thanhVien = new ThanhVien(tenTV, namSinh);
                boolean insert = thanhVienDAO.insertThanhVien(thanhVien);
                if (insert) {
                    Toast.makeText(requireActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    refreshActivity();
                    thanhVienAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(requireActivity(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void refreshActivity() {
        ArrayList<ThanhVien> mThanhVien = thanhVienDAO.getThanhVien();
        thanhVienAdapter = new ThanhVienAdapter(requireActivity(), mThanhVien);
        binding.lvThanhVien.setAdapter(thanhVienAdapter);
    }
}