package com.example.tts_progmob_72170109.Mahasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tts_progmob_72170109.R;

public class DataDiriMhsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_diri_mhs);
        this.setTitle("SI - Hello Mahasiswa");

        Button btnDaftarKrs = (Button)findViewById(R.id.btnSimpanDosen);
        btnDaftarKrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataDiriMhsActivity.this, HomeDosen.class);
                startActivity(intent);
            }
        });
    }
}
