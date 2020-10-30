package com.example.tts_progmob_72170109.Admin;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tts_progmob_72170109.Admin.Adapter.MahasiswaAdapter;
import com.example.tts_progmob_72170109.Admin.Model.Mahasiswa;
import com.example.tts_progmob_72170109.Network.DefaultResult;
import com.example.tts_progmob_72170109.Network.GetDataService;
import com.example.tts_progmob_72170109.Network.RetrofitClientInstance;
import com.example.tts_progmob_72170109.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewDaftarMhs extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MahasiswaAdapter mahasiswaAdapter;
    private ArrayList<Mahasiswa> mahasiswaList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_daftar_mhs);
        this.setTitle("SI - Hello Admin");
        //tambahData();

        recyclerView = findViewById(R.id.rvMhs);
        //mhsAdapter = new MahasiswaAdapter(mhsList);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ArrayList<Mahasiswa>> call = service.getMahasiswaAll("72170109");
        call.enqueue(new Callback<ArrayList<Mahasiswa>>() {
            @Override
            public void onResponse(Call<ArrayList<Mahasiswa>> call, Response<ArrayList<Mahasiswa>> response) {
                progressDialog.dismiss();

                mahasiswaList = response.body();
                mahasiswaAdapter = new MahasiswaAdapter(response.body());

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecyclerViewDaftarMhs.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mahasiswaAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Mahasiswa>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RecyclerViewDaftarMhs.this,"Error",Toast.LENGTH_SHORT);
            }
        });

        registerForContextMenu(recyclerView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucreate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu1){
            Intent intent = new Intent(RecyclerViewDaftarMhs.this,CreateMhsActivity.class);
            startActivity(intent);
        }
        return  true;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Mahasiswa mahasiswa = mahasiswaList.get(item.getGroupId());
        if (item.getTitle()== "Ubah Data Mhs"){
            Intent intent = new Intent(RecyclerViewDaftarMhs.this, CreateDosenActivity.class);
            intent.putExtra("id_mahasiswa",mahasiswa.getId()); //(key, value) -> ketika manggil Mhs harus sama
            intent.putExtra("nama",mahasiswa.getNama());
            intent.putExtra("nim",mahasiswa.getNim());
            intent.putExtra("alamat",mahasiswa.getAlamatMhs());
            intent.putExtra("email",mahasiswa.getEmailMhs());
            intent.putExtra("foto",mahasiswa.getFoto());
            intent.putExtra("is_update",true);
            startActivity(intent);

        }else if(item.getTitle() == "Hapus Data Mahasiswa"){
            //Toast.makeText(RecyclerViewDaftarDosen.this,"Hapus",Toast.LENGTH_LONG).show();

            progressDialog = new ProgressDialog(RecyclerViewDaftarMhs.this);
            progressDialog.show();

            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<DefaultResult> call = service.delete_dosen(
                    mahasiswa.getId(), "72170109");
            call.enqueue(new Callback<DefaultResult>() {
                @Override
                public void onResponse(Call<DefaultResult> call, Response<DefaultResult> response) {
                    progressDialog.dismiss();
                    Toast.makeText(RecyclerViewDaftarMhs.this,"Berhasil Menghapus",Toast.LENGTH_SHORT).show();
                    recreate();
                }

                @Override
                public void onFailure(Call<DefaultResult> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(RecyclerViewDaftarMhs.this,"Gagal Hapus",Toast.LENGTH_SHORT).show();
                }
            });
        }

        return super.onContextItemSelected(item);
    }

//    private RecyclerView recyclerView;
//    private MahasiswaAdapter mhsAdapter;
//    private ArrayList<Mahasiswa> mhsList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recycler_view_daftar_mhs);
//        this.setTitle("SI- Hello Admin");
//        tambahData();
//
//        recyclerView = findViewById(R.id.rvMhs);
//        mhsAdapter = new MahasiswaAdapter(mhsList);
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecyclerViewDaftarMhs.this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(mhsAdapter);
//    }

//    private void tambahData(){
//        mhsList = new ArrayList<>();
//        mhsList.add(new Mahasiswa("72170107","Eka","melsiora.saniba@si.ukdw.ac.id","Jl.Wonosari",R.drawable.eka));
//        mhsList.add(new Mahasiswa("72170108","Mel","melsiora.saniba@si.ukdw.ac.id","Jl.Wonosari",R.drawable.mel));
//        mhsList.add(new Mahasiswa("72170109","Nono","melsiora.saniba@si.ukdw.ac.id","Jl.Wonosari",R.drawable.nono));
    }

