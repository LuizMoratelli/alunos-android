package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.model.Student;

import java.io.File;
import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter<Student> {
    public StudentAdapter(Context context, ArrayList<Student> students) {
        super(context, 0, students);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Student student = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        ImageView picture = (ImageView) convertView.findViewById(R.id.foto);
        TextView name = (TextView) convertView.findViewById(R.id.nome);

        try {
            File imageFile = new File(student.getPicture());
            Bitmap image = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            picture.setImageBitmap(image);
        } catch (Exception e) {
            picture.setImageResource(R.drawable.ic_no_image);
        }

        name.setText(student.getName());

        return convertView;

    }
}
