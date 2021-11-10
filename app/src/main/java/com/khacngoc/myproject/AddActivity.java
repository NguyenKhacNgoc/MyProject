package com.khacngoc.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    private Button btnSave, btnCancel;
    private EditText edtTittle, edtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getViews();
        buttonSaveClick();
        buttonCancelClick();
    }
    private void buttonSaveClick() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                String tittle = edtTittle.getText().toString().trim();
                String text = edtText.getText().toString().trim();

                intent.putExtra("Tittle", tittle);
                intent.putExtra("Text", text);

                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private void buttonCancelClick(){
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getViews(){
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        edtTittle = findViewById(R.id.edtTittle);
        edtText = findViewById(R.id.edtText);
    }
}