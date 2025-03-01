package hcmute.edu.vn.tnquynh;
// để trong package utils
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class BatteryReceiver extends BroadcastReceiver {
    private BatteryListener listener;

    // Interface để callback mức pin
    public interface BatteryListener {
        void onBatteryLevelChanged(int batteryPct);
    }

    public BatteryReceiver(BatteryListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int batteryPct = (int) ((level / (float) scale) * 100);

            // Gọi callback để thông báo mức pin mới
            if (listener != null) {
                listener.onBatteryLevelChanged(batteryPct);
            }
        }
    }

    // Hàm hỗ trợ lấy IntentFilter cho BatteryReceiver
    public static IntentFilter getBatteryIntentFilter() {
        return new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    }
}
