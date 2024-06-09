package fpt.hieudmph47182.bookstoreapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import fpt.hieudmph47182.bookstoreapplication.dao.ThuThuDAO;
import fpt.hieudmph47182.bookstoreapplication.databinding.FragmentThemNguoiDungBinding;
import fpt.hieudmph47182.bookstoreapplication.model.ThuThu;

public class ThemNguoiDungFragment extends Fragment {
    private FragmentThemNguoiDungBinding binding;
    private ThuThuDAO dao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThemNguoiDungBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dao = new ThuThuDAO(requireActivity());

        binding.btnHuy.setOnClickListener(v -> {
            binding.edtUserName.setText("");
            binding.edtHoVaTen.setText("");
            binding.edtPassword.setText("");
            binding.edtConfirmPassword.setText("");
        });

        binding.btnLuu.setOnClickListener(v -> {
            createAccount();
        });
    }

    public void createAccount() {
        String userName = Objects.requireNonNull(binding.edtUserName.getText()).toString();
        String hoVaTen = Objects.requireNonNull(binding.edtHoVaTen.getText()).toString();
        String matKhau = Objects.requireNonNull(binding.edtPassword.getText()).toString();
        String confirmPass = Objects.requireNonNull(binding.edtConfirmPassword.getText()).toString();
        if (userName.isEmpty() || hoVaTen.isEmpty() || matKhau.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(requireActivity(), "Bạn phải nhập đẩy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else if (!matKhau.equals(confirmPass)) {
            Toast.makeText(requireActivity(), "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
        } else {
            ThuThu thuThu = new ThuThu(userName, hoVaTen, matKhau);
            boolean account = dao.insertThuThu(thuThu);
            if (account) {
                Toast.makeText(requireActivity(), "Thêm người dùng thành công", Toast.LENGTH_SHORT).show();
                binding.edtUserName.setText("");
                binding.edtHoVaTen.setText("");
                binding.edtPassword.setText("");
                binding.edtConfirmPassword.setText("");
            } else {
                Toast.makeText(requireActivity(), "Thêm người dùng thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }
}