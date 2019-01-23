package com.reynaldiwijaya.databasesiswaroom.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.reynaldiwijaya.databasesiswaroom.R;
import com.reynaldiwijaya.databasesiswaroom.db.Constant;
import com.reynaldiwijaya.databasesiswaroom.db.SiswaDatabase;
import com.reynaldiwijaya.databasesiswaroom.model.KelasModel;
import com.reynaldiwijaya.databasesiswaroom.ui.MainSiswaActivity;
import com.reynaldiwijaya.databasesiswaroom.ui.UpdateKelasActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KelasAdapter extends RecyclerView.Adapter<KelasAdapter.ViewHolder> {

    // Membuat varible untuk menampung context
    private Context context;
    // Membuat Variable list dengan cetakan model
    private final List<KelasModel> kelasModelList;
    // Membuat Variable Bundle untuk mengrim data
    private Bundle bundle;
    // Membuat variable Database
    private SiswaDatabase siswaDatabase;

    public KelasAdapter(Context context, List<KelasModel> kelasModelList) {
        this.context = context;
        this.kelasModelList = kelasModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_kelas, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        // Memidahkan data di dalam list dengan index position
        final KelasModel kelasModel = kelasModelList.get(i);
        // Menampilkan data ke layar
        viewHolder.tvNamaKelas.setText(kelasModel.getNama_kelas());
        viewHolder.tvNamaWali.setText(kelasModel.getNama_wali());

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();

        // Mensetting color backGround CardView
        viewHolder.cvKelas.setCardBackgroundColor(color);

        // Membuat onClick overflow
        viewHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat Object Database
                siswaDatabase = SiswaDatabase.createDatabase(context);

                // Membuat Popup Menu
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                // Inflate menu ke dalam popup menu
                popupMenu.inflate(R.menu.popup_menu);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                // Membuat AlerDialog
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                alertDialogBuilder.setMessage("Are you sure to delete " + kelasModel.getNama_kelas() + " ?");
                                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Melakukan Operasi Delete
                                        siswaDatabase.kelasDao().delete(kelasModel);


                                        // Menghapus data yang telah di hapus pada list
                                        kelasModelList.remove(i);

                                        // Memberitahu bahwa data sudah hilang
                                        notifyItemRemoved(i);
                                        notifyItemRangeChanged(0, kelasModelList.size());
                                        Toast.makeText(context, "Success to Delete", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                break;

                            case R.id.edit:
                                // Membuat objek Bundle
                                bundle = new Bundle();

                                // Mengisi Bundle dengan data
                                bundle.putInt(Constant.ID_KELAS, kelasModel.getId_kelas());
                                bundle.putString(Constant.NAMA_KELAS, kelasModel.getNama_kelas());
                                bundle.putString(Constant.NAMA_WALI, kelasModel.getNama_wali());
                                // Berpindah halaman dengan membawa data
                                context.startActivity(new Intent(context, UpdateKelasActivity.class).putExtras(bundle));

                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putInt(Constant.ID_KELAS, kelasModel.getId_kelas());
                bundle.putString(Constant.NAMA_KELAS, kelasModel.getNama_kelas());
                context.startActivity(new Intent(context, MainSiswaActivity.class).putExtras(bundle));
            }
        });

    }

    @Override
    public int getItemCount() {
        return kelasModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_nama_kelas)
        TextView tvNamaKelas;
        @BindView(R.id.tv_nama_wali)
        TextView tvNamaWali;
        @BindView(R.id.cv_kelas)
        CardView cvKelas;
        @BindView(R.id.overflow)
        ImageButton overflow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
