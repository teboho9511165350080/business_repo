package com.example.teboho;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class DisplayDebitorsList extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Client> clients_list;
    CustomAdapterDisplayDebitorsList customAdapterDisplayDebitorsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_debitors_list);

        recyclerView = findViewById(R.id.recyclerViewDisplayDebitorList);

        clients_list = new ArrayList<>();

        storeDataInArraysDisplayDebitor();
        customAdapterDisplayDebitorsList = new CustomAdapterDisplayDebitorsList(
                DisplayDebitorsList.this,this, clients_list);
        recyclerView.setAdapter(customAdapterDisplayDebitorsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayDebitorsList.this));
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
                customAdapterDisplayDebitorsList.getFilter().filter(newText.toString());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1)
            recreate();
    }

    void storeDataInArraysDisplayDebitor()
    {
        MyDBHandler myDBHandler = new MyDBHandler(DisplayDebitorsList.this);
        Cursor cursor = myDBHandler.readAllData();

        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (cursor.moveToLast()) {
                do {
                    Client client = new Client(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8));
                    clients_list.add(client);
                }while (cursor.moveToPrevious());
            }
        }

        cursor.close();
        myDBHandler.close();
    }
}