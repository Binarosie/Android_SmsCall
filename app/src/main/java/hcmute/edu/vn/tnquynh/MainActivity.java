package hcmute.edu.vn.tnquynh;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private ImageButton btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

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
        btnConfirm.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, CallsActivity.class);
                    startActivity(intent);
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Đặt lại icon khi quay về MainActivity
        bottomNavigationView.setSelectedItemId(R.id.nav_dial);
    }
}