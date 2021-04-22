package com.example.teboho;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.time.LocalDate;
import java.util.Calendar;

public class AddDebitorInner extends AppCompatActivity {

    EditText name_input, surname_input, debit_amount_input;
    Button add_debitor_button;
    Switch switchApplyInterest, S_A_I_M_P;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_debitor_inner);

        name_input = findViewById(R.id.editTextClientName);
        surname_input = findViewById(R.id.editTextSurname);
        debit_amount_input = findViewById(R.id.editTextDebitedAmount);
        switchApplyInterest = findViewById(R.id.switchApplyInterest);
        S_A_I_M_P = findViewById(R.id.switchApplyInterestMissedMonth);
        add_debitor_button = findViewById(R.id.DebitorAddButton);
        add_debitor_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double amountA = Double.parseDouble(debit_amount_input.getText().toString().trim());
                double interest;

                if (switchApplyInterest.isChecked()) {
                    FinanceHelper financeHelper = new FinanceHelper(AddDebitorInner.this);
                    interest = financeHelper.getInterest();
                } else
                    interest = 0.0;

                double amountB = amountA + (amountA * interest);

                DateCapture date = new DateCapture();

                MyDBHandler myDB = new MyDBHandler(AddDebitorInner.this);
                myDB.addDebitor(name_input.getText().toString().trim(), surname_input.getText().toString().trim(),
                        amountA, amountB, date.getPayDay(), date.getPayMonth(), date.getPayYear(), interest, S_A_I_M_P.isChecked());
                myDB.close();

                OnlineDataBaseProcessor onlineDataBaseProcessor = new OnlineDataBaseProcessor(AddDebitorInner.this);
                onlineDataBaseProcessor.masterUpdateOnlineDatabase("");

                Client client = new Client (AddDebitorInner.this, name_input.getText().toString().trim(),
                        surname_input.getText().toString().trim());

               if (!client.isClientPersonalDetailsStored())
                    additionOfClientPersonalDetails();
               else
                   returnBack();
            }
});
    }

    private void additionOfClientPersonalDetails()
    {
        Intent intent = new Intent (AddDebitorInner.this, AddClientPersonalDetails.class);
       intent.putExtra("name",  name_input.getText().toString().trim());
       intent.putExtra("surname",  surname_input.getText().toString().trim());
        startActivity(intent);
    }

    private void returnBack(){
        Intent intent = new Intent (AddDebitorInner.this, AddDebitorInner.class);
        startActivity(intent);
    }
}