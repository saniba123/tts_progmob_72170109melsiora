package com.example.tts_progmob_72170109.Admin;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.tts_progmob_72170109.Network.DefaultResult;
import com.example.tts_progmob_72170109.Network.GetDataService;
import com.example.tts_progmob_72170109.Network.RetrofitClientInstance;
import com.example.tts_progmob_72170109.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class CreateDosenActivity extends AppCompatActivity {

    EditText edtNama, edtNidn,edtAlamat,edtEmail, edtGelar , edtFoto;
    Button btnSave, btnBrowse;
    ImageView imgFoto;
    private Boolean isUpdate = false;
    String idDosen ="", Img;
    GetDataService service;
    ProgressDialog progressDialog;
    Bitmap bitmap;
    static final int IMG_REQ = 777;
    static final int FILE_ACCESS_REQUEST_CODE = 777;
    byte[] imagByte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dosen);
        this.setTitle("SI- Hello Admin");

        if (ActivityCompat.checkSelfPermission(CreateDosenActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CreateDosenActivity.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, FILE_ACCESS_REQUEST_CODE);
        }


        edtNama = (EditText)findViewById(R.id.edtNamaDsn);
        edtNidn = (EditText)findViewById(R.id.edtNidn);
        edtAlamat = (EditText)findViewById(R.id.edtAlamatDsn);
        edtEmail = (EditText)findViewById(R.id.edtEmailDsn);
        edtGelar = (EditText)findViewById(R.id.edtGelar);
        edtFoto = (EditText)findViewById(R.id.edtFoto );
        imgFoto = (ImageView)findViewById(R.id.imgFotoDosenPre);
        btnBrowse = (Button)findViewById(R.id.btnBrowseFotoDosen);

        checkUpdate();
//        Button btnDaftarKrs = (Button)findViewById(R.id.btnSimpanDosen);
//        btnDaftarKrs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CreateDosenActivity.this, HomeAdmin.class);
//                startActivity(intent);
//            }
//        });

        btnSave = (Button)findViewById(R.id.btnSimpanDosen);
        if(isUpdate){
            btnSave.setText("Update");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isValid = true;

                //Validation

                if(edtNama.getText().toString().matches("")){
                    edtNama.setError("Silahkan mengisi Nama Dosen");
                    isValid = false;
                }

                if(edtNidn.getText().toString().matches("")){
                    edtNidn.setError("Silahkan mengisi NIM Dosen");
                    isValid = false;
                }

                if(edtAlamat.getText().toString().matches("")){
                    edtAlamat.setError("Silahkan mengisi Alamat Dosen");
                    isValid = false;
                }

                if(edtEmail.getText().toString().matches("")){
                    edtEmail.setError("Silahkan mengisi Email Dosen");
                    isValid = false;
                }

                if(edtGelar.getText().toString().matches("")){
                    edtGelar.setError("Silahkan mengisi Gelar Dosen");
                }

                if(!isUpdate){
                    if(isValid){
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateDosenActivity.this);

                        builder.setMessage("Apakah anda yakin untuk menyimpan?")
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(CreateDosenActivity.this, "Batal Simpan", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestInsertDosen();
                                    }
                                });

                        AlertDialog dialog = builder.create(); dialog.show();
                    } else {
                        Toast.makeText(CreateDosenActivity.this,"Silahkan Isi Data",Toast.LENGTH_LONG).show();
                    }
                }else if(isUpdate){
                    if (isValid) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateDosenActivity.this);

                        builder.setMessage("Apakah anda yakin untuk menyimpan?")
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(CreateDosenActivity.this, "Batal Simpan", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestUpdateDosen();

                                    }
                                });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }else {
                        Toast.makeText(CreateDosenActivity.this,"Silahkan Isi Data",Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void requestInsertDosen(){
        String gambar = imageToString();
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        progressDialog =  ProgressDialog.show(this, null, "Harap Tunggu...", true, false);

        Call<DefaultResult> call =  service.insert_dosen_foto(edtNama.getText().toString(),edtNidn.getText().toString(),
                edtAlamat.getText().toString(),edtEmail.getText().toString(),edtGelar.getText().toString(),gambar,
                "72170090");
        call.enqueue(new Callback<DefaultResult>() {
            @Override
            public void onResponse(Call<DefaultResult> call, Response<DefaultResult> response) {
                progressDialog.dismiss();
                Toast.makeText(CreateDosenActivity.this,"Berhasil Insert",Toast.LENGTH_LONG).show();
                Intent refresh = new Intent(CreateDosenActivity.this, RecyclerViewDaftarDosen.class);
                startActivity(refresh);
                finish();

            }

            @Override
            public void onFailure(Call<DefaultResult> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CreateDosenActivity.this,"Error",Toast.LENGTH_SHORT);
            }
        });
    }

    private void requestUpdateDosen(){
        String gambar = imageToString();
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        progressDialog = ProgressDialog.show(CreateDosenActivity.this, null, "Harap Tunggu...", true, false);

        Call<DefaultResult> call = service.update_dosen(idDosen, edtNama.getText().toString(), edtNidn.getText().toString(),
                edtAlamat.getText().toString(), edtEmail.getText().toString(), edtGelar.getText().toString(), gambar,
                "72170109");
        call.enqueue(new Callback<DefaultResult>() {
            @Override
            public void onResponse(Call<DefaultResult> call, Response<DefaultResult> response) {
                progressDialog.dismiss();
                Toast.makeText(CreateDosenActivity.this, "Berhasil Update", Toast.LENGTH_LONG).show();
                Intent refresh = new Intent(CreateDosenActivity.this, RecyclerViewDaftarDosen.class);
                startActivity(refresh);
                finish();

            }

            @Override
            public void onFailure(Call<DefaultResult> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CreateDosenActivity.this, "Error cuy", Toast.LENGTH_SHORT);
            }
        });
    }

    void checkUpdate(){
        Bundle extras = getIntent().getExtras();
        if (extras == null){
            return;
        }

        isUpdate = extras.getBoolean("is_update");
        idDosen = extras.getString("id_dosen");
        edtNama.setText(extras.getString("nama"));
        edtNidn.setText(extras.getString("nidn"));
        edtAlamat.setText(extras.getString("alamat"));
        edtEmail.setText(extras.getString("email"));
        edtGelar.setText(extras.getString("gelar"));
        //edtFoto.setText(extras.getString("foto"));

        /*imagByte = Base64.decode(extras.getString("foto"), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imagByte, 0, imagByte.length);
        imgFoto.setImageBitmap(decodedImage);*/
        Picasso.with(CreateDosenActivity.this)
                .load("https://kpsi.fti.ukdw.ac.id/progmob/" + extras.getString("foto"))
                .into(imgFoto);


    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        //Setimage jpeg
        String[] mimeTypes = {"image/jpeg"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQ && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imgFoto.setImageBitmap(bitmap);
                imgFoto.setVisibility(View.VISIBLE);
                btnBrowse.setEnabled(true);
                btnSave.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imagByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imagByte, Base64.DEFAULT);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case FILE_ACCESS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED){
                    //
                }
                break;
        }
    }
}
