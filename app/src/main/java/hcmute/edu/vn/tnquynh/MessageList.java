package hcmute.edu.vn.tnquynh;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MessageList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_message_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.list), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(MessageList.this, NewMessageActivity.class));
        });


        // Ánh xạ BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        // Lắng nghe sự kiện khi chọn menu
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_calls) {
                startActivity(new Intent(MessageList.this, CallsActivity.class));
                return true;
            } else if (itemId == R.id.nav_dial) {
                startActivity(new Intent(MessageList.this, MainActivity.class));
                return true;
            } else if (itemId == R.id.nav_sms) {
                startActivity(new Intent(MessageList.this, MessageList.class));
                return true;
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(MessageList.this, SettingsActivity.class));
                return true;
            }
            return false;
        });
    }
}