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

import com.example.tts_progmob_72170109.Admin.Adapter.MatkulAdapter;
import com.example.tts_progmob_72170109.Admin.Model.Matakuliah;
import com.example.tts_progmob_72170109.R;

import java.util.ArrayList;

public class RecyclerViewDaftarMatkul extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MatkulAdapter matkulAdapter;
    private ArrayList<Matakuliah> mkList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucreate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu1){
            Intent intent = new Intent(RecyclerViewDaftarMatkul.this,CreateMatkulActivity.class);
            startActivity(intent);
        }
        return  true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_daftar_matkul);
        this.setTitle("SI - HelloAdmin");

        tambahData();

        recyclerView = findViewById(R.id.rvMatkul);
        matkulAdapter = new MatkulAdapter(mkList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecyclerViewDaftarMatkul.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(matkulAdapter);
    }

    public void tambahData(){
        mkList = new ArrayList<>();
        mkList.add(new Matakuliah("SI001","Dasar-Dasar Pemrograman","Senin","3","1"));
        mkList.add(new Matakuliah("SI002","Bahasa Indo","Senin","3","2"));
        mkList.add(new Matakuliah("SI003","Seni Musik","Senin","3","3"));
    }
}
