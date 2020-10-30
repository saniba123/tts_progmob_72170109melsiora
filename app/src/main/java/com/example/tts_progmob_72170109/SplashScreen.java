package com.example.tts_progmob_72170109;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.tts_progmob_72170109.Admin.HomeAdmin;
import com.example.tts_progmob_72170109.Mahasiswa.HomeDosen;

public class SplashScreen extends AppCompatActivity {

    TextView tvSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);

        tvSplash = (TextView) findViewById(R.id.textView2);

        SharedPreferences prefs = SplashScreen.this.getSharedPreferences("prefs_file", MODE_PRIVATE);
        String statusLogin = prefs.getString("isLogin", null);
        if (statusLogin != null) {
            if (statusLogin.equals("Mahasiswa")) {
                Intent intent = new Intent(SplashScreen.this, HomeDosen.class);
                startActivity(intent);
            } else if (statusLogin.equals("Dosen")) {

                Intent intent = new Intent(SplashScreen.this, HomeAdmin.class);
                startActivity(intent);

            }
        }
          else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }, 900L);
        }
    }

    ;
}
