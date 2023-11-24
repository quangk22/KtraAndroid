package com.example.trinhngocquang_qlbaihat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterBaiHat extends BaseAdapter {
    Context context;
    ArrayList<BaiHat> list;
    SQLiteDatabase database;

    String DATABASE_NAME = "Quang_QLBH.db";

    public AdapterBaiHat(Context context, ArrayList<BaiHat> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_baihat,null);
        TextView txtID = (TextView) row.findViewById(R.id.textViewID);
        TextView txtTenBH = (TextView) row.findViewById(R.id.textViewTenBH);
        TextView txtTenCS = (TextView) row.findViewById(R.id.textViewTenCS);
        ImageView imgAnh = (ImageView) row.findViewById(R.id.imageViewAnh);
        Button btnSua = (Button) row.findViewById(R.id.buttonSua);
        Button btnXoa = (Button) row.findViewById(R.id.buttonXoa);
        BaiHat baihat = list.get(position);
        txtID.setText(baihat.idBH + "");
        txtTenBH.setText(baihat.TenBH);
        txtTenCS.setText(baihat.TenCS);
        Bitmap bitmap = BitmapFactory.decodeByteArray(baihat.Anh,0,baihat.Anh.length);
        imgAnh.setImageBitmap(bitmap);

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , UpdateActivity.class);
                intent.putExtra("ID",baihat.idBH);
                context.startActivity(intent);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có muốn xóa không");
                builder.setPositiveButton("ýes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(baihat.idBH);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        return row;
    }
    private void delete(int idsong){
        database = Database.initDatabase((Activity) context, DATABASE_NAME);
        database.delete("TrinhNgocQuang_BaiHat"," ID = ?", new String[] {idsong+""});

        Cursor cursor = database.rawQuery("Select * from TrinhNgocQuang_BaiHat", null);
        list.clear();
        while (cursor.moveToNext()){
            int idbh = cursor.getInt(0);
            String tenbh = cursor.getString(1);
            String tencs = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);

            list.add(new BaiHat(idbh, tenbh, tencs, anh));
        }
        notifyDataSetChanged();
    }
}
