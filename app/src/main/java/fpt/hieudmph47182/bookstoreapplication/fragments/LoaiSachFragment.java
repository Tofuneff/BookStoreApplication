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

import fpt.hieudmph47182.bookstoreapplication.R;
import fpt.hieudmph47182.bookstoreapplication.adapter.LoaiSachAdapter;
import fpt.hieudmph47182.bookstoreapplication.dao.LoaiSachDAO;
import fpt.hieudmph47182.bookstoreapplication.databinding.FragmentLoaiSachBinding;
import fpt.hieudmph47182.bookstoreapplication.model.LoaiSach;

public class LoaiSachFragment extends Fragment {
    private FragmentLoaiSachBinding binding;
    private LoaiSachDAO loaiSachDAO;
    private LoaiSachAdapter loaiSachAdapter;
    private LoaiSach loaiSach;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoaiSachBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loaiSachDAO = new LoaiSachDAO(requireActivity());
        refreshActivity();
        binding.fabAddLoaiSach.setOnClickListener(v -> addDialog());
    }

    private void addDialog() {
        Dialog dialog = new Dialog(requireActivity());
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        dialog.setContentView(R.layout.loai_sach_dialog_add);
        EditText edMaLS = dialog.findViewById(R.id.edMaLS);
        EditText edTenLS = dialog.findViewById(R.id.edTenLS);

        AppCompatButton btnSaveLS = dialog.findViewById(R.id.btnSaveLS);
        AppCompatButton btnCancelLS = dialog.findViewById(R.id.btnCancelLS);

        edMaLS.setEnabled(false);

        btnCancelLS.setOnClickListener(v -> dialog.dismiss());
        btnSaveLS.setOnClickListener(v -> {
            String tenLS = edTenLS.getText().toString();

            if (tenLS.isEmpty()) {
                Toast.makeText(requireActivity(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                loaiSach = new LoaiSach(0, tenLS);
                boolean insert = loaiSachDAO.insertLoaiSach(loaiSach);
                if (insert) {
                    Toast.makeText(requireActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    refreshActivity();
                    loaiSachAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(requireActivity(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void refreshActivity() {
        ArrayList<LoaiSach> mLoaiSach = loaiSachDAO.getLoaiSach();
        loaiSachAdapter = new LoaiSachAdapter(requireActivity(), mLoaiSach);
        binding.lvLoaiSach.setAdapter(loaiSachAdapter);
    }
}