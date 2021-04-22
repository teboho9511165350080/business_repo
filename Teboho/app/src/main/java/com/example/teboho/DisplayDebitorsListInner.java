package com.example.teboho;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DisplayDebitorsListInner extends AppCompatActivity {

    String id, name, surname, amountA, amountB, payDay, payMonth, payYear;
    RecyclerView recyclerView;
    MyDBHandler myDB;
    ArrayList<String> client_id, client_name, client_surname,
            client_amount_A, client_pay_day, client_pay_month, client_pay_year, debit_status;
    CustomAdapterDisplayDebitorsListInner customAdapterDisplayDebitorsListInner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_debitors_list_inner);

        recyclerView = findViewById(R.id.recyclerViewDisplayDebitorListInner);

        myDB = new MyDBHandler(DisplayDebitorsListInner.this);
        client_id = new ArrayList<>();
        client_name = new ArrayList<>();
        client_surname = new ArrayList<>();
        client_amount_A = new ArrayList<>();
        client_pay_day = new ArrayList<>();
        client_pay_month = new ArrayList<>();
        client_pay_year = new ArrayList<>();
        debit_status = new ArrayList<>();

        getIntentDataDisplayDebitorsListInner();
        storeDataInArraysDisplayDebitorsListInner();
        customAdapterDisplayDebitorsListInner = new CustomAdapterDisplayDebitorsListInner(DisplayDebitorsListInner.this,
                this,  client_id, client_name, client_surname,
                client_amount_A, client_pay_day, client_pay_month, client_pay_year, debit_status);
        recyclerView.setAdapter(customAdapterDisplayDebitorsListInner);
        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayDebitorsListInner.this));
    }

    void storeDataInArraysDisplayDebitorsListInner()
    {
        Cursor cursor = myDB.readAllDataDebitorRecord();

        if (getIntent().hasExtra("id") && getIntent().hasExtra("amountA") &&
                getIntent().hasExtra("amountB")) {
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            } else {
                if (cursor.moveToLast()){
                do{
                    if (name.equals(cursor.getString(1)) && surname.equals(cursor.getString(2))) {
                        client_id.add(cursor.getString(0));
                        client_name.add(cursor.getString(1));
                        client_surname.add(cursor.getString(2));
                        client_amount_A.add(cursor.getString(3));
                        client_pay_day.add(cursor.getString(4));
                        client_pay_month.add(cursor.getString(5));
                        client_pay_year.add(cursor.getString(6));
                        debit_status.add(cursor.getString(7));
                    }
                }while(cursor.moveToPrevious());
            }
            }
        }
        cursor.close();
    }

    void getIntentDataDisplayDebitorsListInner()
    {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("amountA") &&
                getIntent().hasExtra("amountB") )
        {
            //getting data from the intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            surname = getIntent().getStringExtra("surname");
            amountA = getIntent().getStringExtra("amountA");
            payDay = getIntent().getStringExtra("payDay");
            payMonth = getIntent().getStringExtra("payMonth");
            payYear = getIntent().getStringExtra("payYear");
        }
        else
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
}