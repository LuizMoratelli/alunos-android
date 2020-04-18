package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.StudentAdapter;
import com.example.myapplication.dao.StudentDAO;
import com.example.myapplication.model.Student;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView studentList;
    protected Student selectedStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentList = findViewById(R.id.lista_alunos);

        onLoadListStudents();
        onLoadAddListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onLoadListStudents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_student:
                Intent intent = new Intent(MainActivity.this, FormsActivity.class);
                startActivity(intent);
                return false;
            default:
                Toast.makeText(
                        MainActivity.this,
                        "Not Implemented Yet!",
                        Toast.LENGTH_LONG
                ).show();
        }

        onLoadListStudents();
        return super.onOptionsItemSelected(item);
    }

    public void onLoadListStudents()  {
        StudentDAO dao = new StudentDAO(this);
        final ArrayList<Student> students = (ArrayList<Student>) dao.onListStudents();
        dao.close();

        StudentAdapter adapter = new StudentAdapter(this, students);

        studentList.setAdapter(adapter);
        registerForContextMenu(studentList);
    }

    private void onLoadAddListeners() {
        studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu menu = new PopupMenu(MainActivity.this, view);
                menu.getMenu().add("Editar");
                menu.getMenu().add("Remover");
                menu.getMenu().add("Enviar Mensagem");
                menu.getMenu().add("Ligar");
                menu.getMenu().add("Ver no mapa");
                menu.getMenu().add("Abrir site");

                selectedStudent = (Student) studentList.getAdapter().getItem(position);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        popUpMenuActions(item);
                        onLoadListStudents();
                        return true;
                    }
                });
                menu.show();
            }
        });
    }

    public void popUpMenuActions(MenuItem item) {
        switch (item.getTitle().toString()) {
            case "Editar":
                onUpdate();
                break;
            case "Remover":
                onDelete();
                break;
            case "Ligar":
                onCall();
                break;
            case "Enviar Mensagem":
               onSendMessage();
                break;
            case "Ver no mapa":
                onSeeInMap();
                break;
            case "Abrir site":
                openWebsite();
                break;
            default:
                Toast.makeText(MainActivity.this, "NOT IMPLEMENTED YET", Toast.LENGTH_LONG).show();
        }
    }

    private void openWebsite() {
        if (selectedStudent.getWebsite().isEmpty()) {
            Toast.makeText(
                    MainActivity.this,
                    "Esse aluno não possui um site cadastrado!",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + selectedStudent.getWebsite()));
            startActivity(browserIntent);
        }
    }

    private void onSeeInMap() {
        if (selectedStudent.getAddress().isEmpty()) {
            Toast.makeText(
                    MainActivity.this,
                    "Esse aluno não possui um endereço cadastrado!",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + selectedStudent.getAddress());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    }

    private void onCall() {
        if (selectedStudent.getPhone().isEmpty()) {
            Toast.makeText(
                    MainActivity.this,
                    "Esse aluno não possui um número cadastrado!",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", selectedStudent.getPhone(), null));
            startActivity(intent);
        }
    }

    private void onSendMessage() {
        if (selectedStudent.getPhone().isEmpty()) {
            Toast.makeText(
                    MainActivity.this,
                    "Esse aluno não possui um número cadastrado!",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.setData(Uri.parse("smsto:" + Uri.encode(selectedStudent.getPhone())));
            smsIntent.putExtra("sms_body","Mensagem");
            startActivity(smsIntent);
        }
    }

    private void onUpdate() {
        Intent intent = new Intent(MainActivity.this, FormsActivity.class);
        intent.putExtra("selected_student", selectedStudent);
        startActivity(intent);
    }

    private void onDelete() {
        StudentDAO dao = new StudentDAO(MainActivity.this);
        dao.onDeleteStudent(selectedStudent);
        dao.close();

        Toast.makeText(
                MainActivity.this,
                "Aluno removido com sucesso!",
                Toast.LENGTH_LONG
        ).show();
    }
}
