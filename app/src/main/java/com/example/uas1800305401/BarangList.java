package com.example.uas1800305401;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class BarangList extends AppCompatActivity {

    GridView gridView;
    ArrayList<Barang> list;
    BarangListAdapter adapter = null;
    ImageView imageViewBarang;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barang_list_activity);

        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new BarangListAdapter(this, R.layout.barang_item_activity, list);
        gridView.setAdapter(adapter);

        //INI BUAT NGAMBIL DATANYA YAA :)
        Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM BARANG");
        list.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String id_barang = cursor.getString(1);
            String nama = cursor.getString(2);
            String jumlah = cursor.getString(3);
            String lokasi_gedung = cursor.getString(4);
            String lokasi_ruang = cursor.getString(5);
            byte[] image = cursor.getBlob(6);

            list.add(new Barang(id, id_barang, nama, jumlah, lokasi_gedung, lokasi_ruang, image));
        }
        adapter.notifyDataSetChanged();

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(BarangList.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            Cursor c = MainActivity.sqLiteHelper.getData("SELECT id FROM BARANG");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                           showDialogUpdate(BarangList.this, arrID.get(position));

                        } else {
                            // delete
                            Cursor c = MainActivity.sqLiteHelper.getData("SELECT id FROM BARANG");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
    }



    private void showDialogUpdate(Activity activity, final int position) {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_barang_activity);
        dialog.setTitle("Update");

        imageViewBarang = (ImageView) dialog.findViewById(R.id.imageViewBarang);
        final EditText edtId_barang = (EditText) dialog.findViewById(R.id.edtId_barang);
        final EditText edtNama = (EditText) dialog.findViewById(R.id.edtNama);
        final EditText edtJumlah = (EditText) dialog.findViewById(R.id.edtJumlah);
        final EditText edtLokasi_gedung = (EditText) dialog.findViewById(R.id.edtLokasi_gedung);
        final EditText edtLokasi_ruang = (EditText) dialog.findViewById(R.id.edtLokasi_ruang);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);
        Button btnChoose = (Button) dialog.findViewById(R.id.btnChoose);

        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 1);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 1);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        imageViewBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request photo library
                ActivityCompat.requestPermissions(
                        BarangList.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MainActivity.sqLiteHelper.updateData(
                            edtId_barang.getText().toString().trim(),
                            edtNama.getText().toString().trim(),
                            edtJumlah.getText().toString().trim(),
                            edtLokasi_gedung.getText().toString().trim(),
                            edtLokasi_ruang.getText().toString().trim(),
                            MainActivity.imageViewToByte(imageViewBarang),
                            position

                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update Berhasil!!!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }
                updateBarngList();
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void  onClick(View view){
                ActivityCompat.requestPermissions(
                        BarangList.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });

    }

    private void showDialogDelete(final int idFood){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(BarangList.this);

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    MainActivity.sqLiteHelper.deleteData(idFood);
                    Toast.makeText(getApplicationContext(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateBarngList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }


    private void updateBarngList(){
        // get all data from sqlite
        Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM BARANG");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String id_barang = cursor.getString(1);
            String nama = cursor.getString(2);
            String jumlah = cursor.getString(3);
            String lokasi_gedung = cursor.getString(4);
            String lokasi_ruang = cursor.getString(5);
            byte[] image = cursor.getBlob(6);

            list.add(new Barang(id, id_barang, nama, jumlah, lokasi_gedung, lokasi_ruang, image));
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewBarang.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
