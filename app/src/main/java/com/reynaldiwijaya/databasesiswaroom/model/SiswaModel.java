package com.reynaldiwijaya.databasesiswaroom.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.reynaldiwijaya.databasesiswaroom.db.Constant;

@Entity(tableName = Constant.NAMA_TABLE_SISWA)
public class SiswaModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constant.ID_SISWA)
    private int id_siswa;

    @ColumnInfo(name = Constant.ID_KELAS)
    private int id_kelas;

    @ColumnInfo(name = Constant.NAMA_SISWA)
    private String nama_siswa;

    @ColumnInfo(name = Constant.UMUR)
    private String umur;

    @ColumnInfo(name = Constant.JENIS_KELAMIN)
    private String jenis_kelamin;

    @ColumnInfo(name = Constant.ASAL)
    private String asal;

    @ColumnInfo(name = Constant.EMAIL)
    private String email;

    public int getId_siswa() {
        return id_siswa;
    }

    public void setId_siswa(int id_siswa) {
        this.id_siswa = id_siswa;
    }

    public int getId_kelas() {
        return id_kelas;
    }

    public void setId_kelas(int id_kelas) {
        this.id_kelas = id_kelas;
    }

    public String getNama_siswa() {
        return nama_siswa;
    }

    public void setNama_siswa(String nama_siswa) {
        this.nama_siswa = nama_siswa;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getAsal() {
        return asal;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
