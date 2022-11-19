package android.example.mytask;

import static android.media.MediaRecorder.OutputFormat.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    public static String fileName = "recorded.3gp";
    Button button1,button2;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    TextView progressText;

  private static int MICROPHONE_PER_CODE = 200;

    String file = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        progressText = findViewById(R.id.progress);


        if (isMicroPhone()) {
            getMicroPhonePermission();
        }
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(file);

        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    }
    public void startRecording(View view) {
        boolean isPlaying = true;

        try {
            if(isPlaying) {

                mediaRecorder.prepare();
                mediaRecorder.start();
                Toast.makeText(this, "Recording is started", Toast.LENGTH_SHORT).show();
                button1.setText("Stop Recording");
                progressText.setText("Recording is started.........");
                isPlaying = false;
            }else{
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                Toast.makeText(this, "Recording is stopped", Toast.LENGTH_SHORT).show();
                button2.setText("Start Recording");
                progressText.setText("Recording is stopped");

                isPlaying = true;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Some Error Occurred", Toast.LENGTH_SHORT).show();

        }

    }

    public void stopRecording(View view) {
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(file);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();

            }
            progressText.setText("Playing...............");
        }


    private boolean isMicroPhone(){
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return true;
        }
        else{
            return false;
        }
    }
    private void getMicroPhonePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MICROPHONE_PER_CODE);

        }

    }
}