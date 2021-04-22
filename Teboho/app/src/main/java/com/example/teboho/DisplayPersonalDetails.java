package com.example.teboho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DisplayPersonalDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Client> clients_list;
    CustomAdapterDisplayClientsPersonalDetails customAdapterDisplayClientsPersonalDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_personal_details);

        recyclerView = findViewById(R.id.recyclerViewDisplayClientsPersonalDetails);

        clients_list = new ArrayList<>();

        storeDataInArray();
        customAdapterDisplayClientsPersonalDetails = new CustomAdapterDisplayClientsPersonalDetails(
                DisplayPersonalDetails.this,this, clients_list);
        recyclerView.setAdapter(customAdapterDisplayClientsPersonalDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayPersonalDetails.this));
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
                customAdapterDisplayClientsPersonalDetails.getFilter().filter(newText.toString());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    void storeDataInArray()
    {
        MyDBHandler myDBHandler = new MyDBHandler(DisplayPersonalDetails.this);
        Cursor cursor = myDBHandler.readAllDataClientPersonalDetails();

        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (cursor.moveToLast()) {
                do {
                    Client client = new Client(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(5), cursor.getString(4), cursor.getString(6));
                    clients_list.add(client);
                }while (cursor.moveToPrevious());
            }
        }

        cursor.close();
        myDBHandler.close();
    }
}