package hcmute.edu.vn.tnquynh;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;

public class NewMessageActivity extends AppCompatActivity {
    private EditText etReceiver, etMess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mess);

        TextView tvBack = findViewById(R.id.tvBack);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng activity hiện tại và quay lại SMSActivity
            }
        });
        TextView tvSend = findViewById(R.id.tvSend);
        etReceiver = findViewById(R.id.etRecipient);
        etMess = findViewById(R.id.etInput);

        // Xử lý sự kiện gửi tin nhắn
        tvSend.setOnClickListener(v -> sendSMS());
    }

    private void sendSMS() {
        String phoneNumber = etReceiver.getText().toString().trim();
        String message = etMess.getText().toString().trim();

        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Vui lòng nhập nội dung tin nhắn!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Intent mở ứng dụng SMS với số và nội dung
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse("smsto:" + phoneNumber)); // Chỉ định số điện thoại
        smsIntent.putExtra("sms_body", message); // Thêm nội dung tin nhắn

        try {
            startActivity(smsIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Không thể mở ứng dụng tin nhắn!", Toast.LENGTH_SHORT).show();
        }
    }

}