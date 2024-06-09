package fpt.hieudmph47182.bookstoreapplication.fragments;

import android.app.Dialog;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Objects;

import fpt.hieudmph47182.bookstoreapplication.R;
import fpt.hieudmph47182.bookstoreapplication.adapter.LoaiSachSpinnerAdapter;
import fpt.hieudmph47182.bookstoreapplication.adapter.SachAdapter;
import fpt.hieudmph47182.bookstoreapplication.dao.LoaiSachDAO;
import fpt.hieudmph47182.bookstoreapplication.dao.SachDAO;
import fpt.hieudmph47182.bookstoreapplication.databinding.FragmentSachBinding;
import fpt.hieudmph47182.bookstoreapplication.model.LoaiSach;
import fpt.hieudmph47182.bookstoreapplication.model.Sach;

public class SachFragment extends Fragment {
    private FragmentSachBinding binding;
    private LoaiSachDAO loaiSachDAO;
    private SachDAO sachDAO;
    private SachAdapter sachAdapter;
    private ArrayList<LoaiSach> mLoaiSach;
    private int maLoaiSach;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSachBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sachDAO = new SachDAO(requireActivity());
        loaiSachDAO = new LoaiSachDAO(requireActivity());
        refreshActivity();
        binding.fabAddSach.setOnClickListener(v -> addDialog());
    }

    private void addDialog() {
        Dialog dialog = new Dialog(requireActivity());
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        dialog.setContentView(R.layout.sach_dialog_add);
        EditText edMaSach = dialog.findViewById(R.id.edMaSach);
        EditText edTenSach = dialog.findViewById(R.id.edTenSach);
        EditText edGiaThue = dialog.findViewById(R.id.edGiaThue);
        Spinner spSach = dialog.findViewById(R.id.spSach);
        mLoaiSach = loaiSachDAO.getLoaiSach();
        LoaiSachSpinnerAdapter loaiSachSpinnerAdapter = new LoaiSachSpinnerAdapter(requireActivity(), mLoaiSach);
        spSach.setAdapter(loaiSachSpinnerAdapter);

        AppCompatButton btnSaveSach = dialog.findViewById(R.id.btnSaveSach);
        AppCompatButton btnCancelSach = dialog.findViewById(R.id.btnCancelSach);

        edMaSach.setEnabled(false);

        btnCancelSach.setOnClickListener(v -> dialog.dismiss());

        btnSaveSach.setOnClickListener(v -> {
            String tenSach = edTenSach.getText().toString();
            String giaThue = edGiaThue.getText().toString();
            int position = spSach.getSelectedItemPosition();
            if (tenSach.isEmpty() || giaThue.isEmpty()) {
                Toast.makeText(requireActivity(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    int idLoaiSach = mLoaiSach.get(position).getMaLoai();
                    Sach sach = new Sach(0, tenSach, Integer.parseInt(giaThue), idLoaiSach);
                    boolean insert = SachDAO.insertSach(sach);
                    if (insert) {
                        Toast.makeText(requireActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        refreshActivity();
                        sachAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(requireActivity(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(requireActivity(), "Chưa có loại sách nào được tạo, bạn cần phải tạo loại sách trước", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void refreshActivity() {
        ArrayList<Sach> mSach = sachDAO.getSach();
        sachAdapter = new SachAdapter(requireActivity(), mSach);
        binding.lvSach.setAdapter(sachAdapter);
    }
}