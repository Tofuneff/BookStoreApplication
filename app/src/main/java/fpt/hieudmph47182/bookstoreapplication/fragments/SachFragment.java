package fpt.hieudmph47182.bookstoreapplication.fragments;

import android.app.Dialog;
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
    private Sach sach;
    private LoaiSachSpinnerAdapter loaiSachSpinnerAdapter;
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
        loaiSachSpinnerAdapter = new LoaiSachSpinnerAdapter(requireActivity(), mLoaiSach);
        spSach.setAdapter(loaiSachSpinnerAdapter);

        AppCompatButton btnSaveSach = dialog.findViewById(R.id.btnSaveSach);
        AppCompatButton btnCancelSach = dialog.findViewById(R.id.btnCancelSach);

        edMaSach.setEnabled(false);

        spSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                maLoaiSach = mLoaiSach.get(i).getMaLoai();
//                Toast.makeText(requireActivity(), "Chọn " + mLoaiSach.get(i).getTenLoaiSach(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCancelSach.setOnClickListener(v -> dialog.dismiss());

        btnSaveSach.setOnClickListener(v -> {
            String tenSach = edTenSach.getText().toString();
            String giaThue = edGiaThue.getText().toString();

            if (tenSach.isEmpty() || giaThue.isEmpty()) {
                Toast.makeText(requireActivity(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                sach = new Sach(tenSach, Integer.parseInt(giaThue));
                sach.setMaLoai(maLoaiSach);
                spSach.setSelection(loaiSachSpinnerAdapter.getCount());
                boolean insert = SachDAO.insertSach(sach);
                if (insert) {
                    Toast.makeText(requireActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    refreshActivity();
                    sachAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(requireActivity(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
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