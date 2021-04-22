package com.example.teboho;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class AddDebitorExisting extends AppCompatActivity {

    EditText client_amount_input;
    Button buttonDeditorExisiting;
    Switch switchApplyInterest, S_A_I_M_P;
    TextView accessiableAmountTitle, accessiableAmountValue;

    String id, name, surname, amountA, amountB, payDay, payMonth, payYear;
    String tracked_current_amountA, tracked_current_amountB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_debitor_existing);

        client_amount_input = findViewById(R.id.editTextNumberDecimalExistingDebitor);
        buttonDeditorExisiting = findViewById(R.id.buttonAddDebitorExisitng);
        switchApplyInterest = findViewById(R.id.switchApplyInterestExisting);
        S_A_I_M_P = findViewById(R.id.switchApplyInterestMissedMonthExisting);
        accessiableAmountTitle = findViewById(R.id.accessiableAmountTitleTextView);
        accessiableAmountValue = findViewById(R.id.accessiableAmountValueTextView);

        getIntentData();

        accessiableAmountTitle.setText("Issuable Amount");
        Client client = new Client(AddDebitorExisting.this, name, surname);
        accessiableAmountValue.setText("R" + String.valueOf(client.getIssuable_amount()));

        //set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setTitle(name + "  " + surname);

        buttonDeditorExisiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double interest;

                if (switchApplyInterest.isChecked())
                {
                    FinanceHelper financeHelper = new FinanceHelper(AddDebitorExisting.this);
                    interest = financeHelper.getInterest();
                }
                else
                    interest = 0.0;


                double newDebited = Double.parseDouble(tracked_current_amountA)+
                        Double.parseDouble(client_amount_input.getText().toString().trim());
                double newExpected = (Double.parseDouble(client_amount_input.getText().toString().trim()) * (interest + 1)) +
                        Double.parseDouble(tracked_current_amountB);

                MyDBHandler myDB = new MyDBHandler(AddDebitorExisting.this);
                myDB.updateData(id, newDebited, newExpected);
                myDB.addDebitRecord(name, surname, Double.parseDouble(client_amount_input.getText().toString().trim()),
                        interest, S_A_I_M_P.isChecked());

                myDB.updateSyncStatus("DebitorsList", id, "false");
                myDB.close();
                OnlineDataBaseProcessor onlineDataBaseProcessor = new OnlineDataBaseProcessor(AddDebitorExisting.this);
                onlineDataBaseProcessor.masterUpdateOnlineDatabase("");
                returnBack();
            }
        });
    }

    private void returnBack()
    {
        Intent i = new Intent(this,AddDebitor.class);
        startActivity(i);
    }

    private void getIntentData()
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
            tracked_current_amountA = amountA;
            tracked_current_amountB = amountB;
        }
        else
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
}