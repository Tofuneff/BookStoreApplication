package fpt.hieudmph47182.bookstoreapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import fpt.hieudmph47182.bookstoreapplication.dao.ThuThuDAO;
import fpt.hieudmph47182.bookstoreapplication.databinding.ActivityLoginBinding;

public class ActivityLogin extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private ThuThuDAO thuThuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        thuThuDAO = new ThuThuDAO(ActivityLogin.this);

        SharedPreferences pref = getSharedPreferences("ACCOUNT_FILE", MODE_PRIVATE);

        binding.edtUserName.setText(pref.getString("USERNAME", ""));
        binding.edtPassword.setText(pref.getString("PASSWORD", ""));
        binding.chkRememberPass.setChecked(pref.getBoolean("REMEMBER", false));

        binding.btnCancle.setOnClickListener(v -> {
            binding.edtUserName.setText("");
            binding.edtPassword.setText("");
        });

        binding.btnLogin.setOnClickListener(v -> checkLogin());
    }

    public void checkLogin() {
        String strUserName = Objects.requireNonNull(binding.edtUserName.getText()).toString();
        String strPassword = Objects.requireNonNull(binding.edtPassword.getText()).toString();
        if (strUserName.isEmpty() || strPassword.isEmpty()) {
            Toast.makeText(this, "Tên đăng nhập và mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
        } else {
            if (thuThuDAO.checkLogin(strUserName, strPassword)) {
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                rememberAccount(strUserName, strPassword, binding.chkRememberPass.isChecked());
                Intent i = new Intent(ActivityLogin.this, MainActivity.class);
                i.putExtra("USERNAME", strUserName);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void rememberAccount(String user, String pass, boolean status) {
        SharedPreferences pref = getSharedPreferences("ACCOUNT_FILE", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
        if (!status) {
            // Xóa dữ liệu trong SharedPreferences
            editor.clear();
        } else {
            // Lưu dữ liệu vào SharedPreferences
            editor.putString("USERNAME", user);
            editor.putString("PASSWORD", pass);
            editor.putBoolean("REMEMBER", true);
        }
        // Lưu lại toàn bộ dữ liệu
        editor.apply();
    }
}

