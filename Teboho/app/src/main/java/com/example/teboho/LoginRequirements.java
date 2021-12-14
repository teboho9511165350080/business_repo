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
    OnlineDBHandler onlineDBHandler1 = new OnlineDBHandler(LoginRequirements.this);
    OnlineDBHandler onlineDBHandler2 = new OnlineDBHandler(LoginRequirements.this);
    OnlineDBHandler onlineDBHandler3 = new OnlineDBHandler(LoginRequirements.this);
    OnlineDBHandler onlineDBHandler4 = new OnlineDBHandler(LoginRequirements.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_requirements);

        if (myDB.automatedPassword())
        {
            //the app is installed on the new device
            //all the data from online database should be collect and stored on local database

            onlineDBHandler1.execute("new installation debitors data","3434","4543","57776");
            onlineDBHandler2.execute("new installation debitors:creditors data","3434","4543","57776");
            onlineDBHandler3.execute("new installation personal data","3434","4543","57776");
            onlineDBHandler4.execute("new installation creditors data", "343","233","324");
        }
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