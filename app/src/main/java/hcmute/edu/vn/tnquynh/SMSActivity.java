package hcmute.edu.vn.tnquynh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.tnquynh.model.Adapter.MessageAdapter;
import hcmute.edu.vn.tnquynh.model.MessageModel;
import hcmute.edu.vn.tnquynh.service.MySmsReceiver;

public class SMSActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<MessageModel> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        recyclerView = findViewById(R.id.recyclerViewChat);
        messageList = new ArrayList<>();
        adapter = new MessageAdapter(messageList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Đăng ký BroadcastReceiver để nhận tin nhắn
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(smsReceiver, new IntentFilter(MySmsReceiver.SMS_BROADCAST));

        // Ánh xạ Toolbar từ layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Bật nút quay về (Back button)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Xử lý sự kiện khi bấm vào nút quay về
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng Activity và quay về MainActivity
            }
        });
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SMSActivity.this, NewMessageActivity.class);
                startActivity(intent);
            }
        });
    }
    // BroadcastReceiver để nhận tin nhắn từ MySmsReceiver
    private final BroadcastReceiver smsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String sender = intent.getStringExtra("sender");
            String body = intent.getStringExtra("body");

            if (sender != null && body != null) {
                MessageModel newMessage = new MessageModel(sender, body);
                adapter.addMessage(newMessage);
                recyclerView.scrollToPosition(0); // Cuộn lên đầu danh sách
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver);
    }
}
