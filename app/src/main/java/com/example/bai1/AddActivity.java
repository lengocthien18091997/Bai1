package com.example.bai1;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    private EditText editTextName, editTextYear, editTextAddress;
    private Button button, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTextName = (EditText) findViewById(R.id.editTextTextPersonName);
        editTextYear = (EditText) findViewById(R.id.editTextTextYear);
        editTextAddress = (EditText) findViewById(R.id.editTextTextPersonAddress);
        button = (Button) findViewById(R.id.button);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String year = editTextYear.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                String err = Common.validateAddActivity(name, year, address);
                if (err.equals("ok")) {
                    addStudent("http://10.0.5.54/web/insert.php", new SinhVien(0, name, Integer.parseInt(year), address));
                } else {
                    Toast.makeText(AddActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddActivity.this.startActivity(new Intent(AddActivity.this, MainActivity.class));
            }
        });
    }

    private void addStudent(String url, SinhVien sinhVien) {
        RequestQueue queue = Volley.newRequestQueue(AddActivity.this);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddActivity.this, MainActivity.class));

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddActivity.this, "Them that bai!", Toast.LENGTH_SHORT).show();
                        Log.d("Bai1", "onErrorResponse: " + error);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("hotenSV", sinhVien.getHoTen());
                params.put("namsinhSV", sinhVien.getNamSinh() + "");
                params.put("diachiSV", sinhVien.getDiaChi());
                return params;
            }
        };
        queue.add(request);
    }
}