package com.example.bai1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listViewSinhVien;
    ArrayList<SinhVien> sinhVienArrayList;
    SinhVienAdapter sinhVienAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewSinhVien = findViewById(R.id.listviewSinhvien);
        sinhVienArrayList = new ArrayList<>();

        sinhVienAdapter = new SinhVienAdapter(this, R.layout.dong_sinh_vien, sinhVienArrayList);
        listViewSinhVien.setAdapter(sinhVienAdapter);

        getData("http://10.0.5.54/web/demo.php");
    }

    private void getData (String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for ( int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        sinhVienArrayList.add(new SinhVien(
                                object.getInt("id"),
                                object.getString("HoTen"),
                                object.getInt("NamSinh"),
                                object.getString("DiaChi")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    sinhVienAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "err", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}