package com.example.teboho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginRequirements extends AppCompatActivity {

    Button loginButton;
    EditText userName;
    EditText passWord;
    private int counter = 5;

    MyDBHandler myDB = new MyDBHandler(LoginRequirements.this);
    FinanceHelper financeHelper = new FinanceHelper(LoginRequirements.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_requirements);

        myDB.automatedPassword();
        financeHelper.autoUpdatePaymentMissed();

        loginButton = (Button) findViewById(R.id.buttonLogin);
        userName = (EditText) findViewById(R.id.editTextUsername);
        passWord = (EditText) findViewById(R.id.editTextUserPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLoginRequirements(userName.getText().toString().trim(), passWord.getText().toString().trim());
            }
        });
    }

    private void validateLoginRequirements (String username, String password)
    {
        Cursor cursor = myDB.readAllDataSecurity();

        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "No security data", Toast.LENGTH_SHORT).show();
        }
        else {
            if (cursor.moveToFirst()){
            if ((username.equals(cursor.getString(1))) &&
                    (password.equals(cursor.getString(2)))) {
                Intent i = new Intent(LoginRequirements.this, MainActivity.class);
                startActivity(i);
            } else {
                counter--;

                if (counter == 0)
                    loginButton.setEnabled(false);
            }
            }
            cursor.close();
        }
    }
}