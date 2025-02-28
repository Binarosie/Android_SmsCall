package hcmute.edu.vn.tnquynh;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SMSActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<String> messages;
    private EditText etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        recyclerView = findViewById(R.id.recyclerViewMessages);
        etMessage = findViewById(R.id.etMessage);
        ImageView btnSend = findViewById(R.id.btnSend);

        messages = new ArrayList<>();
        adapter = new MessageAdapter(messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnSend.setOnClickListener(v -> {
            String message = etMessage.getText().toString().trim();
            if (!message.isEmpty()) {
                messages.add(message);
                adapter.notifyItemInserted(messages.size() - 1);
                etMessage.setText("");
            }
        });
    }
}
