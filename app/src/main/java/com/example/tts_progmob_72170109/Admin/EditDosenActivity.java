package com.example.tts_progmob_72170109.Admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tts_progmob_72170109.Network.DefaultResult;
import com.example.tts_progmob_72170109.Network.GetDataService;
import com.example.tts_progmob_72170109.Network.RetrofitClientInstance;
import com.example.tts_progmob_72170109.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditDosenActivity extends AppCompatActivity {

    EditText edtId, edtNama, edtNidn, edtAlamat, edtEmail, edtGelar;
    Intent mIntent = getIntent();
    GetDataService service;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dosen);
        this.setTitle("SI KRS - Hai Admin");

        Button btnEdit = (Button)findViewById(R.id.btnEditDosen);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditDosenActivity.this);

                builder.setMessage("Apakah anda yakin untuk menyimpan?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(EditDosenActivity.this, "Batal Update", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                requestUpdateDosen();
                            }
                        });

                AlertDialog dialog = builder.create(); dialog.show();
            }
        });

    }

    private void requestUpdateDosen(){
        edtId = (EditText)findViewById(R.id.edtIdDosen);
        edtNama = (EditText)findViewById(R.id.edtNamaMhs);
        edtNidn = (EditText)findViewById(R.id.edtNidn);
        edtAlamat = (EditText)findViewById(R.id.edtAlamatMhs);
        edtEmail = (EditText)findViewById(R.id.edtEmailDsn);
        edtGelar = (EditText)findViewById(R.id.edtGelar);

        edtId.setText(mIntent.getStringExtra("id"));
        edtId.setTag(edtId.getKeyListener());
        edtId.setKeyListener(null);
        edtNama.setText(mIntent.getStringExtra("nama"));
        edtNidn.setText(mIntent.getStringExtra("nidn"));
        edtAlamat.setText(mIntent.getStringExtra("alamat"));
        edtEmail.setText(mIntent.getStringExtra("email"));
        edtGelar.setText(mIntent.getStringExtra("gelar"));

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        progressDialog =  ProgressDialog.show(this, null, "Harap Tunggu...", true, false);

        Call<DefaultResult> call =  service.update_dosen(edtId.getText().toString(),edtNama.getText().toString(),edtNidn.getText().toString(),
                edtAlamat.getText().toString(),edtEmail.getText().toString(),edtGelar.getText().toString(),"https://picsum.photos/200",
                "72170090");
        call.enqueue(new Callback<DefaultResult>() {
            @Override
            public void onResponse(Call<DefaultResult> call, Response<DefaultResult> response) {
                progressDialog.dismiss();
                Toast.makeText(EditDosenActivity.this,"Berhasil Update",Toast.LENGTH_LONG).show();
                Intent refresh = new Intent(EditDosenActivity.this, RecyclerViewDaftarDosen.class);
                startActivity(refresh);
                finish();

            }

            @Override
            public void onFailure(Call<DefaultResult> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditDosenActivity.this,"Error",Toast.LENGTH_SHORT);
            }
        });
    }
}
