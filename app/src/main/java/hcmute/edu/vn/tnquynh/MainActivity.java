package hcmute.edu.vn.tnquynh;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.Manifest;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private EditText etPhoneNumber;

    private StringBuilder phoneNumber = new StringBuilder();

    private BottomNavigationView bottomNavigationView;

    private ImageButton btnConfirm, btnCall;

    private static final int REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        btnConfirm = findViewById(R.id.btnCall); // Ánh xạ btnConfirm
        // Đặt icon mặc định là "Dial" khi mở ứng dụng
        bottomNavigationView.setSelectedItemId(R.id.nav_dial);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_sms) {
                startActivity(new Intent(MainActivity.this, SMSActivity.class));
                return true;
            } else if (item.getItemId() == R.id.nav_history) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            }
            return true;
        });


        setButtonListeners();
    }

    private void setButtonListeners() {
        int[] buttonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btn10, R.id.btn11
        };

        for (int id : buttonIds) {
            MaterialButton button = findViewById(id);
            button.setOnClickListener(v -> {
                String text = button.getText().toString();
                addNumber(text);
            });
        }

        // Xử lý nút gọi (giả lập cuộc gọi)
        btnConfirm.setOnClickListener(v -> makeCall());
    }

    private void addNumber(String number) {
        if (phoneNumber.length() < 15) { // Giới hạn 15 ký tự như số điện thoại
            phoneNumber.append(number);
            etPhoneNumber.setText(phoneNumber.toString());
        }
    }

    private void makeCall() {
        String phone = etPhoneNumber.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
        } else {
            startActivity(callIntent);
        }
    }

    // Xử lý kết quả khi người dùng chọn "Cho phép" hoặc "Từ chối" quyền gọi điện
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall();  // Gọi lại hàm gọi điện sau khi có quyền
            } else {
                Toast.makeText(this, "Quyền gọi điện bị từ chối!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Đặt lại icon khi quay về MainActivity
        bottomNavigationView.setSelectedItemId(R.id.nav_dial);
    }
}


