package com.example.tts_progmob_72170109.Mahasiswa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.tts_progmob_72170109.Mahasiswa.Adapter.KelasAdapter;
import com.example.tts_progmob_72170109.Mahasiswa.Model.Kelas;
import com.example.tts_progmob_72170109.R;

import java.util.ArrayList;

public class RecyclerViewKelas extends AppCompatActivity {

    private RecyclerView recyclerView;
    private KelasAdapter kelasAdapter;
    private ArrayList<Kelas> kelasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_kelas);

        this.setTitle("SI - Hello Mahasiswa");

        tambahData();

        recyclerView = findViewById(R.id.rvKelas);
        kelasAdapter = new KelasAdapter(kelasList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecyclerViewKelas.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(kelasAdapter);
    }

    public void tambahData(){
        kelasList = new ArrayList<>();
        kelasList.add(new Kelas("SI001","Dasar-Dasar","3","Senin","Katon","55"));
        kelasList.add(new Kelas("SI002","Pem-Dasar","3","Selasa","Katon","55"));
        kelasList.add(new Kelas("SI003","LK-Dasar","3","Rabu","Katon","55"));
        kelasList.add(new Kelas("SI004","UI-Dasar","3","Kamis","Katon","55"));
    }
}
