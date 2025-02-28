package hcmute.edu.vn.tnquynh;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NewMessageActivity extends AppCompatActivity {
    private EditText etInput;
    private TextView tvBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mess);

        etInput = findViewById(R.id.etInput);
        tvBack = findViewById(R.id.tvBack);

        // Tự động mở bàn phím khi vào màn hình
        etInput.requestFocus();
        showKeyboard(etInput);

        tvBack.setOnClickListener(v -> {
            finish(); // Đóng Activity hiện tại và quay lại màn hình trước đó
        });
    }

    // Hàm hiển thị bàn phím
    private void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }



}