package com.example.bai1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SinhVienAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<SinhVien> sinhVienList;

    public SinhVienAdapter(MainActivity context, int layout, List<SinhVien> sinhVienList) {
        this.context = context;
        this.layout = layout;
        this.sinhVienList = sinhVienList;
    }

    @Override
    public int getCount() {
        return sinhVienList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView txtHoTen, txtNamSinh, txtDiaChi;
        ImageView imgEdit, imgDel;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtHoTen = view.findViewById(R.id.textviewhoten);
            holder.txtNamSinh = view.findViewById(R.id.textviewnamsinh);
            holder.txtDiaChi = view.findViewById(R.id.textviewdiachi);
            holder.imgDel = view.findViewById(R.id.imageviewdel);
            holder.imgEdit  = view.findViewById(R.id.imageviewedit);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        SinhVien sinhVien = sinhVienList.get(i);
        holder.txtHoTen.setText(sinhVien.getHoTen());
        holder.txtNamSinh.setText("Nam sinh: "+sinhVien.getNamSinh());
        holder.txtDiaChi.setText("Dia chi: "+sinhVien.getDiaChi());
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("dataSV", sinhVien);
                context.startActivity(intent);
            }
        });
        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setTitle("Xóa hay không nói 1 lời <3");
                builder.setMessage("Cậu chắc chứ");
                builder.setPositiveButton("Chắc",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                context.delStudent(sinhVien.getId());
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }
}
