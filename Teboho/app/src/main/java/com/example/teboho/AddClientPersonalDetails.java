package com.example.teboho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class AddClientPersonalDetails extends AppCompatActivity {

    EditText client_name, client_surname, contact, DOB, address, monthly_income;
    Switch employment, gender;
    Button addPersonalDetailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client_personal_details);

        client_name = findViewById(R.id.personalClientName);
        client_surname = findViewById(R.id.personalClientSurname);
        contact = findViewById(R.id.personalContact);
        DOB = findViewById(R.id.personalIdentityNo);
        address = findViewById(R.id.personalPostalAddress);
        monthly_income = findViewById(R.id.personalIncome);
        employment = findViewById(R.id.switchPersonalEmployment);
        gender = findViewById(R.id.switchPersonalGender);
        addPersonalDetailsButton = findViewById(R.id.buttonPersonal);

        getIntendDataSetData();

        addPersonalDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyDBHandler myDBHandler = new MyDBHandler(AddClientPersonalDetails.this);
                myDBHandler.addClientPersonalDetails(client_name.getText().toString().trim(), client_surname.getText().toString().trim(),
                        contact.getText().toString().trim(), DOB.getText().toString().trim(), address.getText().toString().trim(),
                        Double.parseDouble(monthly_income.getText().toString().trim()), employment.isChecked(), gender.isChecked());
                myDBHandler.close();

                returnBack();
            }
        });
    }

    private void getIntendDataSetData(){

        if (getIntent().hasExtra("name") && getIntent().hasExtra("surname"))
        {

            client_name.setText(getIntent().getStringExtra("name"));
            client_surname.setText(getIntent().getStringExtra("surname"));
        }
        else
        {
            Toast.makeText(this, "No data, cannot store client personal details", Toast.LENGTH_SHORT).show();
            //add function to return back to mainfunction for now
        }
    }

    private void returnBack()
    {
        Intent i = new Intent(this,AddDebitorInner.class);
        startActivity(i);
    }
}