package hcmute.edu.vn.tnquynh;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CallsActivity extends AppCompatActivity {
    private TextView tvCaller;
    private ImageView btnEndCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calls);

        // Ánh xạ view
        btnEndCall = findViewById(R.id.btnEndCall);

        // Xử lý khi kết thúc cuộc gọi
        btnEndCall.setOnClickListener(v -> finish());
    }

}