package com.example.teboho;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCreditorInner extends AppCompatActivity {

    EditText client_id, client_name, client_surname, client_amountA, client_amountB_input,
            client_due_day, client_due_month, client_due_year;
    Button add_creditor_button, delete_button;

    String id, name, surname, amountA, amountB, payDay, payMonth, payYear;
    String tracked_current_amountB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_creditor_inner);

        client_amountB_input = findViewById(R.id.editCreditedAmount);
        add_creditor_button = findViewById(R.id.CreditorAddButton);
         delete_button = findViewById(R.id.CreditorDeleteButton);

        //first we call getAndSetIntentData
        getAndSetIntentData();

        //set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name + "  " + surname);
        }

        add_creditor_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                double expected =  Double.parseDouble(tracked_current_amountB);
                double paid = Double.parseDouble(client_amountB_input.getText().toString().trim());

                FinanceHelper financeHelper = new FinanceHelper(AddCreditorInner.this);

                if (paid >= expected)
                {
                    MyDBHandler myDBHandler = new MyDBHandler(AddCreditorInner.this);
                    myDBHandler.updateSyncStatus("DebitorsList", id, "delete");
                    myDBHandler.close();
                    OnlineDataBaseProcessor ODBP = new OnlineDataBaseProcessor(AddCreditorInner.this);
                    ODBP.updateOnlineDataBase("");
                }
                else
                {
                    MyDBHandler myDBHandler = new MyDBHandler(AddCreditorInner.this);
                    myDBHandler.updateSyncStatus("DebitorsList", id, "false");
                    myDBHandler.close();
                }

                financeHelper.processClientPayment(id, name, surname, expected, paid);
                OnlineDataBaseProcessor onlineDataBaseProcessor = new OnlineDataBaseProcessor(AddCreditorInner.this);
                onlineDataBaseProcessor.masterUpdateOnlineDatabase("");
                returnBack();
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    private void getAndSetIntentData()
    {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("amountA") &&
                getIntent().hasExtra("amountB") )
        {
            //getting data from the intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            surname = getIntent().getStringExtra("surname");
            amountA = getIntent().getStringExtra("amountA");
            amountB = getIntent().getStringExtra("amountB");
            payDay = getIntent().getStringExtra("payDay");
            payMonth = getIntent().getStringExtra("payMonth");
            payYear = getIntent().getStringExtra("payYear");

            //coping amountB to use for balance remaining;
            tracked_current_amountB = amountB;

            //setting intent data
            client_amountB_input.setText(amountB);
        }
        else
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " " + surname + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " " + surname + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDBHandler myDB = new MyDBHandler(AddCreditorInner.this);
                myDB.deleteOneRow(id);
                myDB.close();
                finish();
                returnBack();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                returnBack();
            }
        });
        builder.create().show();
    }

    private void returnBack()
    {
        Intent i = new Intent(this,AddCreditor.class);
        startActivity(i);
    }
}