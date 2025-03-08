package hcmute.edu.vn.tnquynh;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class CallsActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private TelephonyManager telephonyManager;
    private PhoneStateListener phoneStateListener;
    private boolean ongoingCall = false;
    private int resumePosition = 0; // Lưu vị trí nhạc bị tạm dừng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        // Khởi tạo MediaPlayer
//        mediaPlayer = MediaPlayer.create(this, R.raw.sample_audio); // Đổi `sample_audio` thành file nhạc trong `res/raw/`

        Button playButton = findViewById(R.id.btnPlay);
        Button pauseButton = findViewById(R.id.btnPause);

        // Nút phát nhạc
        playButton.setOnClickListener(v -> playMedia());

        // Nút dừng nhạc
        pauseButton.setOnClickListener(v -> pauseMedia());

        // Kiểm tra quyền & đăng ký lắng nghe cuộc gọi
        checkPhonePermission();
    }

    private void checkPhonePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            } else {
                registerCallListener(); // Nếu đã có quyền, đăng ký lắng nghe cuộc gọi
            }
        } else {
            registerCallListener();
        }
    }

    private void registerCallListener() {
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                            pauseMedia();
                            ongoingCall = true;
                        }
                        break;

                    case TelephonyManager.CALL_STATE_IDLE:
                        if (mediaPlayer != null && ongoingCall) {
                            ongoingCall = false;
                            resumeMedia();
                        }
                        break;
                }
            }
        };

        // Đăng ký lắng nghe cuộc gọi
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private void playMedia() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(resumePosition);
            mediaPlayer.start();
        }
    }

    private void pauseMedia() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            resumePosition = mediaPlayer.getCurrentPosition();
        }
    }

    private void resumeMedia() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(resumePosition);
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (telephonyManager != null) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            registerCallListener(); // Gọi lại hàm lắng nghe cuộc gọi nếu được cấp quyền
        }
    }
}
