package com.example.tts_progmob_72170109.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.tts_progmob_72170109.Admin.Adapter.KrsAdapter;
import com.example.tts_progmob_72170109.Admin.Model.Krs;
import com.example.tts_progmob_72170109.R;

import java.util.ArrayList;

public class RecyclerViewDaftarKrs extends AppCompatActivity {

    private RecyclerView recyclerView;
    private KrsAdapter krsAdapter;
    private ArrayList<Krs> krsArrayList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucreate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu1){
            Intent intent = new Intent(RecyclerViewDaftarKrs.this,CreateKrsActivity.class);
            startActivity(intent);
        }
        return  true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_daftar_krs);
        this.setTitle("SI - Hello Admin");

        tambahData();

        recyclerView = findViewById(R.id.rvKrs);
        krsAdapter = new KrsAdapter(krsArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecyclerViewDaftarKrs.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(krsAdapter);

    }

    public void tambahData(){
        krsArrayList = new ArrayList<>();
        krsArrayList.add(new Krs("SI001","Sistem Informasi Layanan Kesehatan","Senin","3","1","Budi","60"));
        krsArrayList.add(new Krs("SI002","Dasar-Dasar Manajemen","Selasa","3","1","Budi","60"));
        krsArrayList.add(new Krs("SI003","Bisnis Cerdas Layanan Kesehatan","Rabu","3","1","Yetli","60"));
        krsArrayList.add(new Krs("SI004","Bahasa Indonesia","Jumat","3","1","Umi","60"));
    }
}
