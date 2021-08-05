package com.example.teboho;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AddDebitor extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_debitor_button;
    List<Client> clients_list;
    CustomAdapterDebitor customAdapterDebitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_debitor);

        recyclerView = findViewById(R.id.recyclerViewAddDebitor);
        add_debitor_button = findViewById(R.id.add_button_debitor);

        add_debitor_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddDebitor.this, AddDebitorInner.class);
                startActivity(intent);
            }
        });

        clients_list = new ArrayList<>();

        storeDataInArrays();
        customAdapterDebitor = new CustomAdapterDebitor(AddDebitor.this, this,  clients_list);
        recyclerView.setAdapter(customAdapterDebitor);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddDebitor.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.client_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapterDebitor.getFilter().filter(newText.toString());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    void storeDataInArrays()
    {
        MyDBHandler myDBHandler = new MyDBHandler(AddDebitor.this);
        Cursor cursor = myDBHandler.readAllData();

        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (cursor.moveToNext())
            {
                Client client = new Client (cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                        cursor.getString(7));

                clients_list.add(client);
            }
        }

        cursor.close();
        myDBHandler.close();
    }
}