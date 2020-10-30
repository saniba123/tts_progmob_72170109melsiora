package com.example.tts_progmob_72170109.Mahasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tts_progmob_72170109.Admin.RecyclerViewDaftarKrs;
import com.example.tts_progmob_72170109.MainActivity;
import com.example.tts_progmob_72170109.R;

public class HomeDosen extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.openBrowser:
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeDosen.this);
                builder.setMessage("Apakah anda yakin untuk logout ?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(HomeDosen.this, "Batal logout",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences prefs = HomeDosen.this.getSharedPreferences("prefs_file", MODE_PRIVATE);
                                String statusLogin = prefs.getString("isLogin", null);
                                SharedPreferences.Editor edit = prefs.edit();

                                edit.putString("isLogin", null);
                                edit.commit();

                                Intent intent = new Intent(HomeDosen.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();


                return true;

        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dosen);
        this.setTitle("SI - Hello Mahasiswa");

        ImageButton btnDaftarKelas = (ImageButton)findViewById(R.id.btnLihatKelas);
        btnDaftarKelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeDosen.this, RecyclerViewKelas.class);
                startActivity(intent);
            }
        });

        ImageButton btnDaftarKrs = (ImageButton)findViewById(R.id.btnDaftarKrs);
        btnDaftarKrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeDosen.this, RecyclerViewDaftarKrs.class);
                startActivity(intent);
            }
        });

        ImageButton btnDataDiri = (ImageButton)findViewById(R.id.btnDataDiriDosen);
        btnDataDiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeDosen.this, DataDiriMhsActivity.class);
                startActivity(intent);
            }
        });
    }
}
