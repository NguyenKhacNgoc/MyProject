package com.khacngoc.myproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<mActivity> arrayList;
    ListView listView;
    Button btnAdd;
    mActivityAdapter adapter;
    String tittle,text;
    Database database;
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent intent = result.getData();
                        tittle  = intent.getStringExtra("Tittle");
                        text  = intent.getStringExtra("Text");
                        if(tittle.equals("") && text.equals("")){
                            Toast.makeText(MainActivity.this, "Vui long nhap lai", Toast.LENGTH_SHORT).show();
                        }else {
                            insertDabase();
                            Toast.makeText(MainActivity.this,"Đã thêm thành công!",Toast.LENGTH_SHORT).show();
                            getDatabase();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();
        mSetAdapter();
        initDatabase();
        buttonClickAdd();
        getDatabase();

    }
    private void insertDabase() {
        database.QuerryData("INSERT INTO CongViec VALUES(null, '"+ tittle +"', '"+ text +"')");
    }

    private void getDatabase() {
        Cursor dataCongViec = database.GetData("SELECT * FROM CongViec");
        arrayList.clear();
        while(dataCongViec.moveToNext()){
            int id = dataCongViec.getInt(0);
            String tt = dataCongViec.getString(1);
            String txt = dataCongViec.getString(2);
            arrayList.add(new mActivity(id,tt,txt));
        }
        adapter.notifyDataSetChanged();
    }

    private void initDatabase() {
        database = new Database(this,"ghichu.sqlite", null,1);
        database.QuerryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, tieuDeCV VARCHAR(200), tenCV VARCHAR)");
    }

    private void mSetAdapter(){
        adapter = new mActivityAdapter(MainActivity.this, R.layout.activity_list, arrayList);
        listView.setAdapter(adapter);
    }

    private void buttonClickAdd(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                activityResultLauncher.launch(intent);
            }
        });
    }

    private void getViews(){
        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);
        arrayList = new ArrayList<>();
    }

    public void XoaCongViec(String tittle,final int id){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Bạn có muốn xóa "+ tittle + " không?");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QuerryData("DELETE FROM CongViec WHERE Id = '"+ id +"' ");
                Toast.makeText(MainActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                getDatabase();
            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_demo, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuSetting){

        }
        return super.onOptionsItemSelected(item);
    }



}