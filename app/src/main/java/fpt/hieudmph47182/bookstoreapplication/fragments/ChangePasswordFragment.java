package fpt.hieudmph47182.bookstoreapplication.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Objects;

import fpt.hieudmph47182.bookstoreapplication.dao.ThuThuDAO;
import fpt.hieudmph47182.bookstoreapplication.databinding.FragmentChangePasswordBinding;
import fpt.hieudmph47182.bookstoreapplication.model.ThuThu;

public class ChangePasswordFragment extends Fragment {
    private FragmentChangePasswordBinding binding;
    private ThuThuDAO thuThuDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        thuThuDAO = new ThuThuDAO(requireActivity());

        binding.btnCancel.setOnClickListener(v -> {
            binding.edtCurrentPassword.setText("");
            binding.edtNewPassword.setText("");
            binding.edtConfirmPassword.setText("");
        });

        binding.btnSave.setOnClickListener(v -> {
            SharedPreferences pref = requireActivity().getSharedPreferences("ACCOUNT_FILE", Context.MODE_PRIVATE);
            String user = pref.getString("USERNAME", "");
            if (validate() > 0) {
                ThuThu thuThu = thuThuDAO.getAllThuThu().get(0);
                thuThu.setMatKhau(Objects.requireNonNull(binding.edtNewPassword.getText()).toString());
                thuThuDAO.updateThuThu(thuThu);
                if (thuThuDAO.updateThuThu(thuThu)) {
                    Toast.makeText(requireActivity(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    binding.edtCurrentPassword.setText("");
                    binding.edtNewPassword.setText("");
                    binding.edtConfirmPassword.setText("");
                } else {
                    Toast.makeText(requireActivity(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public int validate() {
        int check = 1;
        if (Objects.requireNonNull(binding.edtCurrentPassword.getText()).length() == 0 ||
                Objects.requireNonNull(binding.edtNewPassword.getText()).length() == 0 ||
                Objects.requireNonNull(binding.edtConfirmPassword.getText()).length() == 0) {
            Toast.makeText(requireActivity(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            SharedPreferences pref = requireActivity().getSharedPreferences("ACCOUNT_FILE", Context.MODE_PRIVATE);
            String currentPass = pref.getString("PASSWORD", "");
            String newPass = binding.edtNewPassword.getText().toString();
            String confirmPass = binding.edtConfirmPassword.getText().toString();
            if (!currentPass.equals(binding.edtCurrentPassword.getText().toString())) {
                Toast.makeText(requireActivity(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                check = -1;
            } else if (!newPass.equals(confirmPass)) {
                Toast.makeText(requireActivity(), "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}