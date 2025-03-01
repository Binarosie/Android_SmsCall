package hcmute.edu.vn.tnquynh;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BatteryReceiver.BatteryListener {
    private BatteryReceiver batteryReceiver;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences("BatteryPrefs", MODE_PRIVATE);

        // Đăng ký BatteryReceiver (Lắng nghe sự kiện pin)
        batteryReceiver = new BatteryReceiver(this);
        registerReceiver(batteryReceiver, BatteryReceiver.getBatteryIntentFilter());


        // Ánh xạ BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        // Lắng nghe sự kiện khi chọn menu
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_calls) {
                startActivity(new Intent(MainActivity.this, CallsActivity.class));
                return true;
            } else if (itemId == R.id.nav_dial) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                return true;
            } else if (itemId == R.id.nav_sms) {
                startActivity(new Intent(MainActivity.this, MessageList.class));
                return true;
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            }
            return false;
        });
    }

    // Khi pin thay đổi, tự động được gọi qua Listener
    @Override
    public void onBatteryLevelChanged(int batteryPct) {
        // Chỉ hiển thị cảnh báo nếu pin <= 20% và chưa hiển thị trong phiên này
        if (batteryPct <= 20 && !hasShownBatteryDialog()) {
            showBatterySaverDialog();
            setBatteryDialogShown(true);
        }
    }

    private void showBatterySaverDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Chế độ tiết kiệm pin")
                .setMessage("Pin của bạn đang dưới 20%. Bạn có muốn bật chế độ tiết kiệm pin không?")
                .setPositiveButton("Bật ngay", (dialog, which) -> openBatterySaverSettings())
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void openBatterySaverSettings() {
        Intent intent = new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS);
        startActivity(intent);
    }

    private boolean hasShownBatteryDialog() {
        return sharedPreferences.getBoolean("BatteryDialogShown", false);
    }

    private void setBatteryDialogShown(boolean shown) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("BatteryDialogShown", shown);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryReceiver);
    }
}


