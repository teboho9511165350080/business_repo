package com.example.teboho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddInvestmentWithdrawal extends AppCompatActivity {

    EditText investOrWithdrawText, transactionReason;
    Button investmentButton, withdrawalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_investment_withdrawal);

        investOrWithdrawText = findViewById(R.id.InvestedOrWithdrawalAmount);
        transactionReason = findViewById(R.id.editTextTransactionReason);
        investmentButton = findViewById(R.id.InvestmentAddButton);
        withdrawalButton = findViewById(R.id.WithdrawalAddButton);

        withdrawalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(investOrWithdrawText.getText().toString().trim());
                DateCapture date = new DateCapture();
                MyDBHandler  myDB = new MyDBHandler(AddInvestmentWithdrawal.this);
                myDB.addWithdrawal (amount, date.getCurrentDay(), date.getCurrentMonth(), date.getCurrentYear(),
                        transactionReason.getText().toString().trim());
                myDB.close();
                A();
            }
        });

        investmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(investOrWithdrawText.getText().toString().trim());
                DateCapture date = new DateCapture();
                MyDBHandler  myDB = new MyDBHandler(AddInvestmentWithdrawal.this);
                myDB.addInvestment(amount, date.getCurrentDay(), date.getCurrentMonth(), date.getCurrentYear(),
                        transactionReason.getText().toString().trim());
                myDB.close();
                B();
            }
        });
    }

    private void A(){
        Intent i = new Intent (this, DisplayInvestmentWithdrawals.class);
        startActivity(i);
    }

    private void B(){
        Intent i = new Intent (this, DisplayInvestmentWithdrawals2.class);
        startActivity(i);
    }
}