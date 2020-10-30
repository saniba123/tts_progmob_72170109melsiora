package com.example.tts_progmob_72170109;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tts_progmob_72170109.Admin.HomeAdmin;
import com.example.tts_progmob_72170109.Mahasiswa.HomeDosen;

public class MainActivity extends AppCompatActivity {

    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();


//        Button btnSignIn = (Button) findViewById(R.id.btnSignIn);
//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, HomeAdmin.class);
//                startActivity(intent);
//            }
//        });

        Button btnSignIn =findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(myBtnLoginClick);

//        Button btnSignInMhs = (Button) findViewById(R.id.btnSignInMhs);
//        btnSignInMhs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, HomeDosen.class);
//                startActivity(intent);
//            }
//        });
    }

    private View.OnClickListener myBtnLoginClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences prefs = MainActivity.this.getSharedPreferences("prefs_file", MODE_PRIVATE);
            String statusLogin = prefs.getString("isLogin", null);
            SharedPreferences.Editor edit = prefs.edit();

            TextView textView = findViewById(R.id.editText3);
            if (textView.getText().toString().contains("@staff.ukdw.ac.id")) {
                edit.putString("isLogin", "Dosen");
                edit.commit();

                Intent intent = new Intent(MainActivity.this, HomeAdmin.class);
                startActivity(intent);

            } else if (textView.getText().toString().contains("@si.ukdw.ac.id")) {
                edit.putString("isLogin", "Mahasiswa");
                edit.commit();

                Intent intent = new Intent(MainActivity.this, HomeDosen.class);
                startActivity(intent);}

//            } else {
//                Toast toast = Toast.makeText(getApplicationContext(), "Bukan Email milik UKDW", Toast.LENGTH_LONG);
//
//            }
        }

    };
}
