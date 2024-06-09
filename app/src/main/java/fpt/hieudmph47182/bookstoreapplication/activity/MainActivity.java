package fpt.hieudmph47182.bookstoreapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import fpt.hieudmph47182.bookstoreapplication.R;
import fpt.hieudmph47182.bookstoreapplication.dao.ThuThuDAO;
import fpt.hieudmph47182.bookstoreapplication.databinding.ActivityMainBinding;
import fpt.hieudmph47182.bookstoreapplication.fragments.ChangePasswordFragment;
import fpt.hieudmph47182.bookstoreapplication.fragments.DoanhThuFragment;
import fpt.hieudmph47182.bookstoreapplication.fragments.LoaiSachFragment;
import fpt.hieudmph47182.bookstoreapplication.fragments.PhieuMuonFragment;
import fpt.hieudmph47182.bookstoreapplication.fragments.SachFragment;
import fpt.hieudmph47182.bookstoreapplication.fragments.ThanhVienFragment;
import fpt.hieudmph47182.bookstoreapplication.fragments.ThemNguoiDungFragment;
import fpt.hieudmph47182.bookstoreapplication.fragments.ThongKeFragment;
import fpt.hieudmph47182.bookstoreapplication.fragments.ThuThuFragment;
import fpt.hieudmph47182.bookstoreapplication.model.ThuThu;
@SuppressLint({"InflateParams", "WrongViewCast", "SetTextI18n"})
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        replaceFragment(new PhieuMuonFragment());
        binding.tvTitle.setText("Quản lý Phiếu mượn");

        View headerView = binding.navigationView.getHeaderView(0);
        TextView tvUsername = headerView.findViewById(R.id.tvUser);
        ImageView ivAvatar = headerView.findViewById(R.id.ivAvatar);

        Intent i = getIntent();
        String username = i.getStringExtra("USERNAME");
        ThuThuDAO thuThuDAO = new ThuThuDAO(this);
        ThuThu thuThu = thuThuDAO.getTTByMaTT(username);
        if (thuThu != null) {
            // nếu thủ thư không null set tên vào TextView header drawer navigation
            tvUsername.setText("Welcome " + thuThu.getHoTen());
        }

        assert username != null;
        if (username.equalsIgnoreCase("admin")) {
            binding.navigationView.getMenu().findItem(R.id.them_nguoi_dung).setVisible(true);
            binding.navigationView.getMenu().findItem(R.id.quan_ly_thu_thu).setVisible(true);
        } else {
            ivAvatar.setImageResource(R.drawable.avatar4);
        }
        binding.navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.quan_ly_phieu_muon) {
            replaceFragment(new PhieuMuonFragment());
            binding.tvTitle.setText("Quản lý Phiếu mượn");
        } else if (id == R.id.quan_ly_loai_sach) {
            replaceFragment(new LoaiSachFragment());
            binding.tvTitle.setText("Quản lý Loại sách");
        } else if (id == R.id.quan_ly_sach) {
            replaceFragment(new SachFragment());
            binding.tvTitle.setText("Quản lý Sách");
        } else if (id == R.id.quan_ly_thanh_vien) {
            replaceFragment(new ThanhVienFragment());
            binding.tvTitle.setText("Quản lý Thành viên");
        } else if (id == R.id.top_10_sach_muon_nhieu_nhat) {
            replaceFragment(new ThongKeFragment());
            binding.tvTitle.setText("Top 10 Sách Mượn Nhiều Nhất");
        } else if (id == R.id.doanh_thu) {
            replaceFragment(new DoanhThuFragment());
            binding.tvTitle.setText("Thống kê Doanh thu");
        } else if (id == R.id.them_nguoi_dung) {
            replaceFragment(new ThemNguoiDungFragment());
            binding.tvTitle.setText("Thêm người dùng");
        } else if (id == R.id.quan_ly_thu_thu) {
            replaceFragment(new ThuThuFragment());
            binding.tvTitle.setText("Quản lý Thủ thư");
        } else if (id == R.id.doi_mat_khau) {
            replaceFragment(new ChangePasswordFragment());
            binding.tvTitle.setText("Thay đổi mật khẩu");
        } else if (id == R.id.dang_xuat) {
            startActivity(new Intent(this, ActivityLogin.class));
            finish();
        }

        binding.drawerLayout.closeDrawers();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            binding.drawerLayout.openDrawer(binding.navigationView);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }
}