package com.example.myapplication.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.myapplication.FormsActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.Student;

public class FormHelper {
    private EditText name;
    private EditText phone;
    private EditText address;
    private EditText website;
    private SeekBar grade;
    private ImageView picture;
    private Student student;

    public FormHelper(FormsActivity form) {
        name = form.findViewById(R.id.nome);
        phone = form.findViewById(R.id.telefone);
        address = form.findViewById(R.id.endereco);
        website = form.findViewById(R.id.site);
        grade = form.findViewById(R.id.nota);
        picture = form.findViewById(R.id.foto);

        student = new Student();
    }

    public Student getStudentFromForm() {
        student.setName(name.getEditableText().toString());
        student.setPhone(phone.getEditableText().toString());
        student.setAddress(address.getEditableText().toString());
        student.setWebsite(website.getEditableText().toString());
        student.setGrade((double) grade.getProgress());

        return student;
    }

    public void setStudentOnForm(Student student) {
        this.student = student;

        name.setText(student.getName());
        address.setText(student.getAddress());
        phone.setText(student.getPhone());
        website.setText(student.getWebsite());
        grade.setProgress((int) Math.round(student.getGrade()));

        if (student.getPicture() != null) {
            this.loadPicture(student.getPicture());
        }
    }

    private void loadPicture(String path) {
        Bitmap image = BitmapFactory.decodeFile(path);
        Bitmap scaledImage = Bitmap.createScaledBitmap(image, 100,100, true);

        student.setPicture(path);
        picture.setImageBitmap(scaledImage);
    }
}
