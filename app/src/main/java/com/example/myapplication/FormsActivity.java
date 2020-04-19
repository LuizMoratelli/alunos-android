package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.dao.StudentDAO;
import com.example.myapplication.helper.FormHelper;
import com.example.myapplication.model.Student;


public class FormsActivity extends AppCompatActivity {

    private Button btnSave;
    private Student studentToUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Student student = (Student) getIntent().getSerializableExtra("selected_student");
            if (student != null) {
                this.studentToUpdate = student;
                (new FormHelper(FormsActivity.this)).setStudentOnForm(student);
            }
        }

        btnSave = findViewById(R.id.botao);

        addListeners();
    }

    private void addListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (studentToUpdate == null) {
                    saveStudent();
                } else {
                    updateStudent();
                }

                finish();
            }
        });
    }

    private void saveStudent(){
        FormHelper fh = new FormHelper(this);
        Student student = fh.getStudentFromForm();

        (new StudentDAO(this)).onInsertStudent(student);
        Toast.makeText(
                FormsActivity.this,
                "Aluno inserido com sucesso",
                Toast.LENGTH_LONG
        ).show();
    }

    private void updateStudent()
    {
        Student student = (new FormHelper(this)).getStudentFromForm();
        student.setId(studentToUpdate.getId());

        (new StudentDAO(this)).onUpdateStudent(student);

        Toast.makeText(
                FormsActivity.this,
                "Aluno atualizado com sucesso!",
                Toast.LENGTH_LONG
        ).show();
    }
}
