package com.reynaldiwijaya.databasesiswaroom.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.reynaldiwijaya.databasesiswaroom.R;
import com.reynaldiwijaya.databasesiswaroom.db.Constant;
import com.reynaldiwijaya.databasesiswaroom.db.SiswaDatabase;
import com.reynaldiwijaya.databasesiswaroom.model.KelasModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateKelasActivity extends AppCompatActivity {

    @BindView(R.id.edt_namaKelas_update)
    EditText edtNamaKelasUpdate;
    @BindView(R.id.edt_namaWali_update)
    EditText edtNamaWaliUpdate;
    @BindView(R.id.btn_update)
    Button btnUpdate;

    // Membuat variable bundle
    private Bundle bundle;

    // Membuat Variable penampung data
    private String nama_kelas, nama_wali;
    private int id_kelas;

    // Membuat variable Database
    SiswaDatabase siswaDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_kelas);
        ButterKnife.bind(this);

        setTitle("Update Data Kelas");

        // Mengambil / menangkap data dari Intent
        bundle = getIntent().getExtras();

        // Buat Object Database
        siswaDatabase = SiswaDatabase.createDatabase(this);

        // Mengecek Bundle dan show Data
        if (bundle != null) {
            showData();
        }
    }

    private void showData() {
        // Mengambil data di dalam Bundle
        nama_kelas = bundle.getString(Constant.NAMA_KELAS);
        nama_wali = bundle.getString(Constant.NAMA_WALI);
        id_kelas = bundle.getInt(Constant.ID_KELAS);

        // Menampilkan data ke layar
        edtNamaKelasUpdate.setText(nama_kelas);
        edtNamaWaliUpdate.setText(nama_wali);

    }

    @OnClick(R.id.btn_update)
    public void onViewClicked() {

        getData();
    }

    private void getData() {
        nama_kelas = edtNamaKelasUpdate.getText().toString();
        nama_wali = edtNamaWaliUpdate.getText().toString();

        if (TextUtils.isEmpty(nama_kelas)) {
            edtNamaKelasUpdate.setError("Data Tidak Boleh Kosong !!");
            edtNamaKelasUpdate.requestFocus();
        } else if (TextUtils.isEmpty(nama_wali)) {
            edtNamaWaliUpdate.setError("Data Tidak Boleh Kosong !!");
            edtNamaWaliUpdate.requestFocus();
        } else {
            // Mengesave Data
            saveData();
            // Menclear Edit Text
            clearData();
            Toast.makeText(this, "Succes to Update", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void clearData() {
        edtNamaWaliUpdate.setText("");
        edtNamaKelasUpdate.setText("");
    }

    private void saveData() {
        KelasModel kelasModel = new KelasModel();
        kelasModel.setNama_wali(nama_wali);
        kelasModel.setNama_kelas(nama_kelas);
        kelasModel.setId_kelas(id_kelas);
        // Melakukan operasi Update
        siswaDatabase.kelasDao().update(kelasModel);
    }
}
