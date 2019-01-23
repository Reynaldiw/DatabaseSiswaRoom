package com.reynaldiwijaya.databasesiswaroom.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

public class TambahKelasActivity extends AppCompatActivity {

    @BindView(R.id.edt_namaKelas)
    EditText edtNamaKelas;
    @BindView(R.id.edt_namaWali)
    EditText edtNamaWali;
    @BindView(R.id.btn_simpan)
    Button btnSimpan;

    // TODO 1 Membuat Wariable yang dibutuhkan
    private SiswaDatabase siswaDatabase;

    private String namaWali, namaKelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kelas);
        ButterKnife.bind(this);

        setTitle("Add Class");

        //TODO 2 Membuat Object database
        siswaDatabase = SiswaDatabase.createDatabase(this);
    }

    @OnClick(R.id.btn_simpan)
    public void onViewClicked() {
        // TODO 3 Mengambil data dari input user
            getData();

            // TODO 5 Mengclear EditText
            clearData();

//        Snackbar snackbar = Snackbar.make(MainActivity.class, "Succes To Add", Snackbar.LENGTH_SHORT);
//        snackbar.show();
        }

        private void clearData () {
            edtNamaKelas.setText("");
            edtNamaWali.setText("");
        }

        private void saveData() {

//            // Membuat Variable List kelasModels
//            List<KelasModel> kelasModels = new ArrayList<>();

            // Membuat Objek kelasModels untuk menampung data
            KelasModel kelasModel = new KelasModel();

            // Memasukan data ke dalam kelas model
            kelasModel.setNama_kelas(namaKelas);
            kelasModel.setNama_wali(namaWali);

//            // Memasukan data yang sudah terkumpul
//            kelasModels.add(kelasModel);

            siswaDatabase.kelasDao().insert(kelasModel);
        }

        private void getData() {
            namaWali = edtNamaWali.getText().toString();
            namaKelas = edtNamaKelas.getText().toString();

            if (TextUtils.isEmpty(namaKelas)) {
                edtNamaKelas.setError("Data Tidak Boleh Kosong");
                edtNamaKelas.requestFocus();
            } else if (TextUtils.isEmpty(namaWali)) {
                edtNamaWali.setError("Data Tidak Boleh Kosong");
                edtNamaWali.requestFocus();
            } else {
                // TODO 4 Proses Menyimpan data ke SQLite
                saveData();
                // TODO 6 Menampilkan Pemberitahuan

                finish();
            }
        }
    }
