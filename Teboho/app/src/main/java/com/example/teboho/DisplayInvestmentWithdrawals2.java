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
import android.widget.Toast;
import java.util.ArrayList;

public class DisplayInvestmentWithdrawals2 extends AppCompatActivity {

    RecyclerView recyclerView;
    MyDBHandler myDB;
    ArrayList<String> transaction_amount, transaction_reason, transaction_date;
    CustomAdapterDisplayInvestmentWithdrawal customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_investment_withdrawals2);

        recyclerView = findViewById(R.id.recyclerViewDisplayInvestmentWithdrawals2);

        myDB = new MyDBHandler(DisplayInvestmentWithdrawals2.this);
        transaction_amount = new ArrayList<>();
        transaction_reason = new ArrayList<>();
        transaction_date = new ArrayList<>();

        storeDataInArrayWithdrawal();
        customAdapter = new CustomAdapterDisplayInvestmentWithdrawal(DisplayInvestmentWithdrawals2.this,this, transaction_amount, transaction_reason, transaction_date);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayInvestmentWithdrawals2.this));
    }

    void storeDataInArrayWithdrawal()
    {
        Cursor cursor = myDB.readAllDataInvestment();

        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (cursor.moveToLast()) {
                do {
                    transaction_amount.add(cursor.getString(1));
                    transaction_reason.add(cursor.getString(5));
                    transaction_date.add(cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4));
                }while (cursor.moveToPrevious());
            }
        }

        cursor.close();
    }
}