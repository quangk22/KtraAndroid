package com.example.trinhngocquang_qlbaihat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String DATABASE_NAME = "Quang_QLBH.db";
    SQLiteDatabase database;
    ListView lstDSBH;
    EditText txtSearch;
    Button btnThemBH, btnSearch;
    ArrayList<BaiHat> list;
    AdapterBaiHat adapterBaiHat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnThemBH = (Button) findViewById(R.id.buttonThemBH);
        btnSearch = (Button) findViewById(R.id.buttonSearch);
        txtSearch = (EditText) findViewById(R.id.editTextSearch);

        addEvent();
        lstDSBH = (ListView) findViewById(R.id.listViewDSBH);
        list = new ArrayList<>();
        adapterBaiHat = new AdapterBaiHat(MainActivity.this, list);
        lstDSBH.setAdapter(adapterBaiHat);

        database = Database.initDatabase(MainActivity.this, DATABASE_NAME);

        Cursor cursor = database.rawQuery("Select * from TrinhNgocQuang_BaiHat", null);
        list.clear();
        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int idbh = cursor.getInt(0);
            String tenbh = cursor.getString(1);
            String tencs = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);
            list.add(new BaiHat(idbh, tenbh, tencs, anh));
        }
        adapterBaiHat.notifyDataSetChanged();

    }
    private void addEvent() {
        btnThemBH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(intent);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("searchString", txtSearch.getText().toString());
                startActivity(intent);
            }
        });
    }

}