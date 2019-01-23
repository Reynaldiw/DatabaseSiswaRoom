package com.reynaldiwijaya.databasesiswaroom.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class UpdateSiswaActivity extends AppCompatActivity {

    @BindView(R.id.edt_namaSiswa)
    EditText edtNamaSiswa;
    @BindView(R.id.edt_umur)
    EditText edtUmur;
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
    @BindView(R.id.btn_updateSiswa)
    Button btnUpdate;

    private SiswaDatabase siswaDatabase;
    private Bundle bundle;
    private String nama_siswa, umur, jenis_kelamin, asal, email;
    private int id_siswa, id_kelas;
    private boolean empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_siswa);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();

        siswaDatabase = SiswaDatabase.createDatabase(this);

        if (bundle != null) {
            setTitle("Update " + bundle.getString(Constant.NAMA_SISWA));
            id_siswa = bundle.getInt(Constant.ID_SISWA);
            id_kelas = bundle.getInt(Constant.ID_KELAS);
            showData();
        }
    }

    private void getData() {
        nama_siswa = edtNamaSiswa.getText().toString();
        umur = edtUmur.getText().toString();
        asal = edtAsalSiswa.getText().toString();
        email = edtEmail.getText().toString();

        empty = nama_siswa.isEmpty() || umur.isEmpty() || jenis_kelamin.isEmpty() || asal.isEmpty() || email.isEmpty();

        if (!empty) {
            updateData();
            clearData();

            Toast.makeText(this, "Succes to Update", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Field tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateData() {
        SiswaModel siswaModel = new SiswaModel();
        siswaModel.setNama_siswa(nama_siswa);
        siswaModel.setUmur(umur);
        siswaModel.setJenis_kelamin(jenis_kelamin);
        siswaModel.setAsal(asal);
        siswaModel.setEmail(email);
        siswaModel.setId_siswa(id_siswa);
        siswaModel.setId_kelas(id_kelas);

        siswaDatabase.kelasDao().updateSiswa(siswaModel);
    }

    private void clearData() {
        edtNamaSiswa.setText("");
        edtEmail.setText("");
        edtAsalSiswa.setText("");
        edtUmur.setText("");
        rgJenisKelamin.clearCheck();
    }

    private void showData() {
        nama_siswa = bundle.getString(Constant.NAMA_SISWA);
        umur = bundle.getString(Constant.UMUR);
        jenis_kelamin = bundle.getString(Constant.JENIS_KELAMIN);
        asal = bundle.getString(Constant.ASAL);
        email = bundle.getString(Constant.EMAIL);

        if (jenis_kelamin.equals(rbMan.getText().toString())) {
            rgJenisKelamin.check(rbMan.getId());
        }

        if (jenis_kelamin.equals(rbWoman.getText().toString())) {
            rgJenisKelamin.check(rbWoman.getId());
        }

        edtNamaSiswa.setText(nama_siswa);
        edtUmur.setText(umur);
        edtAsalSiswa.setText(asal);
        edtEmail.setText(email);

    }

    @OnClick(R.id.btn_updateSiswa)
    public void onViewClicked() {
        getData();

    }

    @OnClick({R.id.rb_man, R.id.rb_woman})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_man:
                jenis_kelamin = rbMan.getText().toString();
                break;
            case R.id.rb_woman:
                jenis_kelamin = rbWoman.getText().toString();
                break;
        }
    }
}
