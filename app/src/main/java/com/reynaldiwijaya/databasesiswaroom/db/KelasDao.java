package com.reynaldiwijaya.databasesiswaroom.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.reynaldiwijaya.databasesiswaroom.model.KelasModel;
import com.reynaldiwijaya.databasesiswaroom.model.SiswaModel;

import java.util.List;

@Dao
public interface KelasDao {

    @Query("SELECT * FROM kelas ORDER BY nama_kelas DESC")
    List<KelasModel> select();

    // Memasukan Data
    @Insert
    void insert(KelasModel kelasModel);

    // Menghapus Data
    @Delete
    void delete(KelasModel kelasModel);

    // Mengupdate Data
    @Update
    void update(KelasModel kelasModel);

    // Mengambil data Siswa
    @Query("SELECT * FROM tb_siswa WHERE id_kelas = :id_kelas ORDER BY nama_siswa ASC")
    List<SiswaModel> selectSiswa(int id_kelas);

    // Memasukan Data Siswa
    @Insert
    void insertSiswa(SiswaModel siswaModel);

    // Menghapus Data Siswa
    @Delete
    void deleteSiswa(SiswaModel siswaModel);

    // MengUpdate Data Siswa
    @Update
    void updateSiswa(SiswaModel siswaModel);
}
