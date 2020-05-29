package com.example.uas1800305401;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;



public class BarangListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Barang> barangList;


    public BarangListAdapter(Context context, int layout, ArrayList<Barang> barangList) {
        this.context = context;
        this.layout = layout;
        this.barangList = barangList;
    }



    @Override
    public int getCount() {
        return barangList.size();
    }

    @Override
    public Object getItem(int position) {
        return barangList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
            ImageView imageView;
            TextView txtid_barang, txtnama, txtjumlah, txtlokasigedung, txtlokasiruang;
}
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtid_barang = (TextView) row.findViewById(R.id.txtid_barang);
            holder.txtnama = (TextView) row.findViewById(R.id.txtnama);
            holder.txtjumlah = (TextView) row.findViewById(R.id.txtjumlah);
            holder.txtlokasigedung = (TextView) row.findViewById(R.id.txtlokasigedung);
            holder.txtlokasiruang = (TextView) row.findViewById(R.id.txtlokasiruang);
            holder.imageView = (ImageView) row.findViewById(R.id.imgbarang);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Barang barang = barangList.get(position);

        holder.txtid_barang.setText(barang.getId_barang());
        holder.txtnama.setText(barang.getNama());
        holder.txtjumlah.setText(barang.getJumlah());
        holder.txtlokasigedung.setText(barang.getLokasi_gedung());
        holder.txtlokasiruang.setText(barang.getLokasi_ruang());
        byte[] barangImage = barang.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(barangImage, 0, barangImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
