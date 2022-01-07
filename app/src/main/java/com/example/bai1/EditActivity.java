package com.example.bai1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    private EditText editTextName, editTextYear, editTextAddress;
    private Button button, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        anhXa();
        Intent intent = getIntent();
        SinhVien sinhVien = (SinhVien) intent.getSerializableExtra("dataSV");
        hienThi(sinhVien);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditActivity.this.startActivity(new Intent(EditActivity.this, MainActivity.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = sinhVien.getId();
                String name = editTextName.getText().toString().trim();
                String year = editTextYear.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                String err = Common.validateAddActivity(name, year, address);
                if (err.equals("ok")) {
                    editStudent(Constant.URLEDIT, new SinhVien(id, name, Integer.parseInt(year), address));
                } else {
                    Toast.makeText(EditActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void anhXa() {
        editTextName = (EditText) findViewById(R.id.editTextTextPersonName);
        editTextYear = (EditText) findViewById(R.id.editTextTextYear);
        editTextAddress = (EditText) findViewById(R.id.editTextTextPersonAddress);
        button = (Button) findViewById(R.id.button);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
    }

    private void hienThi(SinhVien sinhVien) {
        editTextName.setText(sinhVien.getHoTen());
        editTextYear.setText(sinhVien.getNamSinh()+"");
        editTextAddress.setText(sinhVien.getDiaChi());
    }

    private void editStudent(String url, SinhVien sinhVien) {
        RequestQueue requestQueue = Volley.newRequestQueue(EditActivity.this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("aaa",response);
                        if (response.equals("success")) {
                            Toast.makeText(EditActivity.this, "Sua thanh cong that", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(EditActivity.this, "Sua xit", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditActivity.this, "Sua that bai!", Toast.LENGTH_SHORT).show();
                        Log.d("Bai1", "onErrorResponse: " + error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", sinhVien.getId()+"");
                params.put("hotenSV", sinhVien.getHoTen());
                params.put("namsinhSV", sinhVien.getNamSinh() + "");
                params.put("diachiSV", sinhVien.getDiaChi());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}