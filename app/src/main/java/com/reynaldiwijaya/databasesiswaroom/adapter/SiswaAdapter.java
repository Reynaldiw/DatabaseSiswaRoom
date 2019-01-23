package com.reynaldiwijaya.databasesiswaroom.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.reynaldiwijaya.databasesiswaroom.R;
import com.reynaldiwijaya.databasesiswaroom.db.Constant;
import com.reynaldiwijaya.databasesiswaroom.db.SiswaDatabase;
import com.reynaldiwijaya.databasesiswaroom.model.SiswaModel;
import com.reynaldiwijaya.databasesiswaroom.ui.UpdateSiswaActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SiswaAdapter extends RecyclerView.Adapter<SiswaAdapter.ViewHolder> {

    private final Context context;
    private final List<SiswaModel> siswaModelList;

    private SiswaDatabase siswaDatabase;
    private Bundle bundle;

    public SiswaAdapter(Context context, List<SiswaModel> siswaModelList) {
        this.context = context;
        this.siswaModelList = siswaModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_siswa, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        // Kita memindahkan data yang dipilih ke dalam list
        final SiswaModel siswaModel = siswaModelList.get(i);

        viewHolder.tvNamaSiswa.setText(siswaModel.getNama_siswa());

        // Mengambil huruf pertama
        String nama = siswaModel.getNama_siswa().substring(0, 1);

        // Membuat Color Generator untuk mendapatkan Color Meterial
        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        int color = colorGenerator.getRandomColor();

        // Mensetting TextDrawable nya untuk membuat lingkaran
        TextDrawable drawable = TextDrawable.builder().buildRound(nama, color);

        // Tampilkan gambar lingkaran ke layar
        viewHolder.imgSiswa.setImageDrawable(drawable);

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat Objek Siswa Database
                siswaDatabase = SiswaDatabase.createDatabase(context);

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure to delete " + siswaModel.getNama_siswa() + " ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        siswaDatabase.kelasDao().deleteSiswa(siswaModel);

                        siswaModelList.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(0, siswaModelList.size());

                        Toast.makeText(context, "Succes to Delete", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putString(Constant.NAMA_SISWA, siswaModel.getNama_siswa());
                bundle.putString(Constant.UMUR, siswaModel.getUmur());
                bundle.putString(Constant.JENIS_KELAMIN, siswaModel.getJenis_kelamin());
                bundle.putString(Constant.ASAL, siswaModel.getAsal());
                bundle.putInt(Constant.ID_SISWA, siswaModel.getId_siswa());
                bundle.putString(Constant.EMAIL, siswaModel.getEmail());
                bundle.putInt(Constant.ID_KELAS, siswaModel.getId_kelas());

                context.startActivity(new Intent(context, UpdateSiswaActivity.class).putExtras(bundle));
            }
        });

    }

    @Override
    public int getItemCount() {
        return siswaModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_siswa)
        ImageView imgSiswa;
        @BindView(R.id.tv_nama_siswa)
        TextView tvNamaSiswa;
        @BindView(R.id.btn_delete)
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
