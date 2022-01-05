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

import java.text.NumberFormat;
import java.text.ParsePosition;
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
                String err = validateAddActivity();
                if (err.equals("ok")) {
                    addStudent ("http://10.0.5.54/web/insert.php", new SinhVien(0,
                            editTextName.getText().toString(),
                            Integer.parseInt(editTextYear.getText().toString()),
                            editTextAddress.getText().toString()));
                } else {
                    Toast.makeText(AddStudentActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddStudentActivity.this.startActivity(new Intent(AddStudentActivity.this, MainActivity.class));
            }
        });
    }

    private String validateAddActivity() {
        String err = "ok";
        String name = editTextName.getText().toString().trim();
        String year = editTextYear.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        if (name.isEmpty() || year.isEmpty() || address.isEmpty()) {
            err = "Nhập thiếu hạng mục!";
        } else if (!isNumeric(year)){
            err = "Nhập sai định dạng năm sinh!";
        }
        return err;
    }

    private boolean isNumeric(String year) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(year, pos);
        return year.length() == pos.getIndex();
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