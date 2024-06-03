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
import java.util.Date;
import java.util.Objects;

import fpt.hieudmph47182.bookstoreapplication.R;
import fpt.hieudmph47182.bookstoreapplication.adapter.PhieuMuonAdapter;
import fpt.hieudmph47182.bookstoreapplication.adapter.SachSpinnerAdapter;
import fpt.hieudmph47182.bookstoreapplication.adapter.ThanhVienSpinnerAdapter;
import fpt.hieudmph47182.bookstoreapplication.dao.PhieuMuonDAO;
import fpt.hieudmph47182.bookstoreapplication.dao.SachDAO;
import fpt.hieudmph47182.bookstoreapplication.dao.ThanhVienDAO;
import fpt.hieudmph47182.bookstoreapplication.databinding.FragmentPhieuMuonBinding;
import fpt.hieudmph47182.bookstoreapplication.model.PhieuMuon;
import fpt.hieudmph47182.bookstoreapplication.model.Sach;
import fpt.hieudmph47182.bookstoreapplication.model.ThanhVien;

public class PhieuMuonFragment extends Fragment {
    private FragmentPhieuMuonBinding binding;
    private PhieuMuonDAO phieuMuonDAO;
    private ThanhVienDAO thanhVienDAO;
    private SachDAO sachDAO;
    private ArrayList<ThanhVien> mThanhVien;
    private ArrayList<Sach> mSach;
    private final PhieuMuon phieuMuon = new PhieuMuon();
    private PhieuMuonAdapter phieuMuonAdapter;
    private int maThanhVien;
    private int maSach;
    private int tienThue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhieuMuonBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        phieuMuonDAO = new PhieuMuonDAO(requireActivity());
        sachDAO = new SachDAO(requireActivity());
        thanhVienDAO = new ThanhVienDAO(requireActivity());
        refreshActivity();
        binding.fabAddPhieuMuon.setOnClickListener(v -> showDialog());
    }

    private void showDialog() {
        Dialog dialog = new Dialog(requireActivity());
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        dialog.setContentView(R.layout.phieu_muon_dialog);
        EditText edMaPM = dialog.findViewById(R.id.edMaPM);
        Spinner spTV = dialog.findViewById(R.id.spTV);
        Spinner spSach = dialog.findViewById(R.id.spnSach);

        AppCompatButton btnSavePM = dialog.findViewById(R.id.btnSavePM);
        AppCompatButton btnCancelPM = dialog.findViewById(R.id.btnCancelPM);

        edMaPM.setEnabled(false);

        mThanhVien = thanhVienDAO.getThanhVien();
        ThanhVienSpinnerAdapter thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(requireActivity(), mThanhVien);
        spTV.setAdapter(thanhVienSpinnerAdapter);

        mSach = sachDAO.getSach();
        SachSpinnerAdapter sachSpinnerAdapter = new SachSpinnerAdapter(requireActivity(), mSach);
        spSach.setAdapter(sachSpinnerAdapter);

        spTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                maThanhVien = mThanhVien.get(i).getMaTV();
//                Toast.makeText(requireActivity(), "Chọn " + mThanhVien.get(i).getHoTen(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                maSach = mSach.get(i).getMaSach();
                tienThue = mSach.get(i).getGiaThue();
//                Toast.makeText(requireActivity(), "Chọn " + mSach.get(i).getTenSach(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCancelPM.setOnClickListener(v -> dialog.dismiss());

        btnSavePM.setOnClickListener(v -> {
            phieuMuon.setMaPM(Integer.parseInt(String.valueOf(phieuMuon.getMaPM())));
            phieuMuon.setMaSach(maSach);
            phieuMuon.setMaTV(maThanhVien);
            phieuMuon.setTienThue(tienThue);
            phieuMuon.setNgay(new Date());
            boolean insert = phieuMuonDAO.insertPhieuMuon(phieuMuon);
            if (insert) {
                Toast.makeText(requireActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                refreshActivity();
                phieuMuonAdapter.notifyDataSetChanged();
                dialog.dismiss();
            } else {
                Toast.makeText(requireActivity(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private void refreshActivity() {
        ArrayList<PhieuMuon> mPhieuMuon = phieuMuonDAO.getAllPhieuMuon();
        phieuMuonAdapter = new PhieuMuonAdapter(requireActivity(), mPhieuMuon);
        binding.lvPhieuMuon.setAdapter(phieuMuonAdapter);
    }
}