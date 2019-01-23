package com.reynaldiwijaya.databasesiswaroom.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.reynaldiwijaya.databasesiswaroom.R;
import com.reynaldiwijaya.databasesiswaroom.db.Constant;
import com.reynaldiwijaya.databasesiswaroom.db.SiswaDatabase;
import com.reynaldiwijaya.databasesiswaroom.model.SiswaModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TambahSiswaActivity extends AppCompatActivity {

    @BindView(R.id.edt_namaSiswa)
    EditText edtNamaSiswa;
    @BindView(R.id.rb_man)
    RadioButton rbMan;
    @BindView(R.id.rb_woman)
    RadioButton rbWoman;
    @BindView(R.id.rg_jenisKelamin)
    RadioGroup rgJenisKelamin;
    @BindView(R.id.edt_asalSiswa)
    EditText edtAsalSiswa;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.edt_umur)
    EditText edtUmur;

    /**
     * Membuat Variable yang dibutuhkan
     */

    private SiswaDatabase siswaDatabase;
    private int id_kelas;
    private String namaSiswa, asal, umur, jenisKelamin, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_siswa);
        ButterKnife.bind(this);

        setTitle("Add Student");

        // Mensetting data menangkap data dari activity
        id_kelas = getIntent().getIntExtra(Constant.ID_KELAS, 0);

        // Kita buat object Database
        siswaDatabase = SiswaDatabase.createDatabase(this);
    }

    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        // Memastikan semuanya terisi
        cekData();

    }

    private void clearData() {
        edtAsalSiswa.setText("");
        edtNamaSiswa.setText("");
        edtEmail.setText("");
        edtUmur.setText("");
        rgJenisKelamin.clearCheck();
    }

    private void saveData() {
        // Membuat penampung dengan membuat objek siswaModel
        SiswaModel siswaModel = new SiswaModel();

        siswaModel.setNama_siswa(namaSiswa);
        siswaModel.setAsal(asal);
        siswaModel.setEmail(email);
        siswaModel.setId_kelas(id_kelas);
        siswaModel.setJenis_kelamin(jenisKelamin);
        siswaModel.setUmur(umur);

        // Kita lakukan operasi insert
        siswaDatabase.kelasDao().insertSiswa(siswaModel);
    }

    private void cekData() {
        namaSiswa = edtNamaSiswa.getText().toString();
        asal = edtAsalSiswa.getText().toString();
        umur = edtUmur.getText().toString();
        email = edtEmail.getText().toString();

        if (TextUtils.isEmpty(namaSiswa)) {
            edtNamaSiswa.setError(getString(R.string.error_message));
            edtNamaSiswa.requestFocus();
        } else if (TextUtils.isEmpty(asal)) {
            edtAsalSiswa.setError(getString(R.string.error_message));
            edtAsalSiswa.requestFocus();
        } else if (TextUtils.isEmpty(umur)) {
            edtUmur.setError(getString(R.string.error_message));
            edtAsalSiswa.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            edtEmail.setError(getString(R.string.error_message));
            edtEmail.requestFocus();
        } else if (TextUtils.isEmpty(jenisKelamin)) {
            Toast.makeText(this, "Jenis Kelamin harus diisi", Toast.LENGTH_SHORT).show();
        } else {
            saveData();
            clearData();

            Toast.makeText(this, "Succes to Add", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @OnClick({R.id.rb_man, R.id.rb_woman})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_man:
                jenisKelamin = rbMan.getText().toString();
                break;
            case R.id.rb_woman:
                jenisKelamin = rbWoman.getText().toString();
                break;
        }
    }
}
