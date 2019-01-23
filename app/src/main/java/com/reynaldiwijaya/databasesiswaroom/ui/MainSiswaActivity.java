package com.reynaldiwijaya.databasesiswaroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.reynaldiwijaya.databasesiswaroom.R;
import com.reynaldiwijaya.databasesiswaroom.adapter.SiswaAdapter;
import com.reynaldiwijaya.databasesiswaroom.db.Constant;
import com.reynaldiwijaya.databasesiswaroom.db.SiswaDatabase;
import com.reynaldiwijaya.databasesiswaroom.model.SiswaModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainSiswaActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rvMainSiswa)
    RecyclerView rvMainSiswa;

    /**
     * Membuat Variable yang kita butuhkan
     */

    private SiswaDatabase siswaDatabase;
    private List<SiswaModel> siswaModelList;
    private int id_kelas;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_siswa);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            id_kelas = bundle.getInt(Constant.ID_KELAS);

            setTitle(bundle.getString(Constant.NAMA_KELAS));
        }

        // Membuat Database Objek
        siswaDatabase = SiswaDatabase.createDatabase(this);

        // Membuat Objek List
        siswaModelList = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Menghapis isi data dalam list
        siswaModelList.clear();

        // Mengambil data
        getData();

        // Mensetting adapter untuk menampilkan datanya
        // Mensetting garis bawah
        rvMainSiswa.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvMainSiswa.setLayoutManager(new LinearLayoutManager(this));
        rvMainSiswa.setAdapter(new SiswaAdapter(this, siswaModelList));
    }

    private void getData() {
        // Operasi mengambil data yang ada di dalam SQLite menggunakan select
        siswaModelList = siswaDatabase.kelasDao().selectSiswa(id_kelas);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        startActivity(new Intent(MainSiswaActivity.this, TambahSiswaActivity.class).putExtra(Constant.ID_KELAS, id_kelas));

    }
}
