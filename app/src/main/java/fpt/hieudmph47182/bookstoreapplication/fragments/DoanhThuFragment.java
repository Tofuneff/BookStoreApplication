package fpt.hieudmph47182.bookstoreapplication.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;

import fpt.hieudmph47182.bookstoreapplication.dao.ThongKeDAO;
import fpt.hieudmph47182.bookstoreapplication.databinding.FragmentDoanhThuBinding;

@SuppressLint({"SimpleDateFormat", "SetTextI18n"})
public class DoanhThuFragment extends Fragment {
    private FragmentDoanhThuBinding binding;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private int mYear, mMonth, mDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDoanhThuBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatePickerDialog.OnDateSetListener mSinceDate = (view1, year, month, dayOfMonth) -> {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar calendar = new GregorianCalendar(mYear, mMonth, mDay);
            binding.edtTuNgay.setText(sdf.format(calendar.getTime()));
        };

        DatePickerDialog.OnDateSetListener mToDate = (view2, year, month, dayOfMonth) -> {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar calendar = new GregorianCalendar(mYear, mMonth, mDay);
            binding.edtDenNgay.setText(sdf.format(calendar.getTime()));
        };

        binding.btnTuNgay.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(
                    requireActivity(),
                    0,
                    mSinceDate,
                    mYear,
                    mMonth,
                    mDay
            );
            dialog.show();
        });

        binding.btnDenNgay.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(
                    requireActivity(),
                    0,
                    mToDate,
                    mYear,
                    mMonth,
                    mDay
            );
            dialog.show();
        });

        binding.btnDoanhThu.setOnClickListener(v -> {
            try {
                String tuNgay = binding.edtTuNgay.getText().toString();
                String denNgay = binding.edtDenNgay.getText().toString();
                ThongKeDAO thongKeDAO = new ThongKeDAO(requireActivity());
                binding.tvDoanhThu.setText(thongKeDAO.getDoanhThu(tuNgay, denNgay) + " VNĐ");
            } catch (Exception e) {
                binding.tvDoanhThu.setText("0 VNĐ");
            }
        });
    }
}