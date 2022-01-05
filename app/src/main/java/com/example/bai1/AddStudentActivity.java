package com.example.bai1;

import androidx.annotation.Nullable;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddStudentActivity extends AppCompatActivity {
    private EditText editTextName, editTextYear, editTextAddress;
    private Button button, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        editTextName = (EditText) findViewById(R.id.editTextTextPersonName);
        editTextYear = (EditText) findViewById(R.id.editTextTextYear);
        editTextAddress = (EditText) findViewById(R.id.editTextTextPersonAddress);
        button = (Button) findViewById(R.id.button);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = Integer.parseInt(editTextYear.getText().toString());
               addStudent ("http://10.0.5.54/web/insert.php", new SinhVien(0, editTextName.getText().toString(), year, editTextAddress.getText().toString()));
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddStudentActivity.this.startActivity(new Intent(AddStudentActivity.this, MainActivity.class));
            }
        });
    }

    private void addStudent(String url, SinhVien sinhVien) {
        RequestQueue queue = Volley.newRequestQueue(AddStudentActivity.this);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddStudentActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddStudentActivity.this, MainActivity.class));

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddStudentActivity.this, "Them that bai!", Toast.LENGTH_SHORT).show();
                        Log.d("Bai1", "onErrorResponse: "+ error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("hotenSV",sinhVien.getHoTen());
                params.put("namsinhSV", sinhVien.getNamSinh()+"");
                params.put("diachiSV", sinhVien.getDiaChi());
                return params;
            }
        };
        queue.add(request);
    }
}