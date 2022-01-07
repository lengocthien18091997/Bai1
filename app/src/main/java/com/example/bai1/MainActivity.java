package com.example.bai1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listViewSinhVien;
    ArrayList<SinhVien> sinhVienArrayList;
    SinhVienAdapter sinhVienAdapter;
    ImageView imageViewEdit, imageViewDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewSinhVien = findViewById(R.id.listviewSinhvien);
        sinhVienArrayList = new ArrayList<>();

        sinhVienAdapter = new SinhVienAdapter(this, R.layout.dong_sinh_vien, sinhVienArrayList);
        listViewSinhVien.setAdapter(sinhVienAdapter);
        getData(Constant.URLVIEW);

    }

    private void getData (String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                sinhVienArrayList.clear();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_student, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAddStudent) {
            startActivity(new Intent( MainActivity.this, AddActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    public void delStudent(int id) {
        Log.i("aaa","this: "+ id);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.URLDEL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("aaa",response);
                        if (response.equals("success")) {
                            Toast.makeText(MainActivity.this, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                            getData(Constant.URLVIEW);
                        } else {
                            Toast.makeText(MainActivity.this, "Xoa xit", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Xoa that bai!", Toast.LENGTH_SHORT).show();
                        Log.d("Bai1", "onErrorResponse: " + error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id+"");
                Log.i("aaa", "this2"+ id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}