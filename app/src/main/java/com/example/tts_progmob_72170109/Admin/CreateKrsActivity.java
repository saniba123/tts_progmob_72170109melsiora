package com.example.tts_progmob_72170109.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.tts_progmob_72170109.R;

public class CreateKrsActivity extends AppCompatActivity {
    private Spinner spinner1;
    private static final String[] dosen = {"Jong Jek Siang","Argo Wibowo","Budi Sutedjo","Umi Proboyekti","Yetli Oslan"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_krs);
        this.setTitle("SI - Hello Admin");

        spinner1 = (Spinner)findViewById(R.id.spinnerDosen);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateKrsActivity.this,android.R.layout.simple_spinner_item,dosen);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        Button btnSimpanKrs = (Button)findViewById(R.id.btnSimpanKrs);
        btnSimpanKrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateKrsActivity.this, HomeAdmin.class);
                startActivity(intent);
            }
        });
    }
}
