package com.simplesqliteapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PersonAdapter.PersonClickListener {

    private EditText editTextName;
    private RecyclerView recyclerView;
    private PersonAdapter adapter;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        Button buttonSave = findViewById(R.id.buttonSave);
        recyclerView = findViewById(R.id.recyclerView);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        adapter = new PersonAdapter(this, new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            if (!name.isEmpty()) {
                saveToDatabase(name);
                Toast.makeText(MainActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                editTextName.setText("");
                loadDataFromDatabase();
            } else {
                Toast.makeText(MainActivity.this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
            }
        });

        loadDataFromDatabase();
    }

    private void saveToDatabase(final String name) {
        new Thread(() -> db.personDao().insertAll(new Person(name))).start();
    }

    private void loadDataFromDatabase() {
        new Thread(() -> {
            List<Person> people = db.personDao().getAll();
            runOnUiThread(() -> {
                adapter = new PersonAdapter(MainActivity.this, people, MainActivity.this);
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }

    @Override
    public void onEditButtonClick(Person person) {
        // Implementasi edit disini, misalnya dengan menampilkan dialog edit
        Toast.makeText(this, "Edit: " + person.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteButtonClick(Person person) {
        // Implementasi delete disini
        deleteFromDatabase(person);
    }

    private void deleteFromDatabase(final Person person) {
        new Thread(() -> {
            db.personDao().delete(person);
            loadDataFromDatabase(); // refresh data setelah delete
        }).start();
    }


}
