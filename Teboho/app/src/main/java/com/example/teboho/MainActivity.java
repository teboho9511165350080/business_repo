package com.example.teboho;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends Activity {

    ConstraintLayout constraintLayout;
    ImageView syncImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        avaliableBalanceCreator();
        debitedAmountCreator();
        investedAmountCreator();
        withdrawnAmountCreator();
        profitCreator();

        syncImage = findViewById(R.id.syncAll);
        constraintLayout = findViewById(R.id.constraintLayoutProfit);

        syncImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnlineDataBaseProcessor onlineDataBaseProcessor = new OnlineDataBaseProcessor(
                        MainActivity.this);

                onlineDataBaseProcessor.masterUpdateOnlineDatabase("All");
            }
        });

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plotProfitGraph();
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null && !networkInfo.isConnected())
        {
            Toast.makeText(this, "Internet Not Available", Toast.LENGTH_SHORT).show();
        }
        else
        {
            OnlineDataBaseProcessor onlineDataBaseProcessor = new OnlineDataBaseProcessor(
                    MainActivity.this);
            onlineDataBaseProcessor.masterUpdateOnlineDatabase("");
        }
    }

    private void plotProfitGraph()
    {
        Intent i = new Intent (this, profitCurve.class);
        startActivity(i);
    }

    public void onClickFindMenu (View view)
    {
        Spinner contentType = (Spinner) findViewById(R.id.menuContent);
        String content = String.valueOf(contentType.getSelectedItem());

        if (content.equals("Add Creditor"))
        {
            Intent i = new Intent(this,AddCreditor.class);
            startActivity(i);
        }
        else if (content.equals("Add Debitor"))
        {
            Intent i = new Intent(this,AddDebitor.class);
            startActivity(i);
        }
        else if (content.equals("Add Investment/Withdrawal"))
        {
            Intent i = new Intent(this,AddInvestmentWithdrawal.class);
            startActivity(i);
        }
        else if (content.equals("Check Debitors List"))
        {
            Intent i = new Intent (this, DisplayDebitorsList.class);
            startActivity(i);
        }
        else if (content.equals("Check Client Details"))
        {
            Intent i = new Intent (this, DisplayPersonalDetails.class);
            startActivity(i);
        }
        else if (content.equals("Logout"))
        {
            Intent i = new Intent (this, LoginRequirements.class);
            startActivity(i);
        }
    }

    private void avaliableBalanceCreator()
    {
        MyDBHandler myDB = new MyDBHandler(MainActivity.this);

        TextView available_balance_title_text = findViewById(R.id.currentBalanceTitleTextView);
        available_balance_title_text.setText(R.string.available_balance_string);

        TextView available_balance_value_text = findViewById(R.id.currentBalanceValueTextView);
        double balance = myDB.getStaticSumOfCredits()-myDB.getStaticSumOfDebits()
                +myDB.getSumOfInvestments()-myDB.getSumOfWithdrawals();
        available_balance_value_text.setText("R" + String.valueOf(balance));
    }

    private void debitedAmountCreator()
    {
        MyDBHandler myDB = new MyDBHandler(MainActivity.this);

        TextView dynamicDebitedAmountTitle = findViewById(R.id.debitedAmountTitleTextView);
        dynamicDebitedAmountTitle.setText(R.string.debited_amount_string);

        TextView dynamicDebitedAmountValue = findViewById(R.id.debitedAmountValueTextView);
        double dynamic_debited = myDB.getDynamicSumOfDebits();
        dynamicDebitedAmountValue.setText("R" + String.valueOf(dynamic_debited));
    }

    private void investedAmountCreator()
    {
        MyDBHandler myDB = new MyDBHandler(MainActivity.this);

        TextView investedAmountTitle = findViewById(R.id.investedAmountTitleTextView);
        investedAmountTitle.setText(R.string.invested_amount_string);

        TextView investedAmountValue = findViewById(R.id.investedAmountValueTextView);
        double totalInvestments = myDB.getSumOfInvestments();
        investedAmountValue.setText("R" + String.valueOf(totalInvestments));

        ConstraintLayout layout = findViewById(R.id.invested_constraint_layout);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                B();
            }
        });
    }

    private void withdrawnAmountCreator()
    {
        MyDBHandler myDB = new MyDBHandler(MainActivity.this);

        TextView withdrawalAmountTitle = findViewById(R.id.withdrawnAmountTitleTextView);
        withdrawalAmountTitle.setText(R.string.withdrawn_amount_string);

        TextView withdrawnAmountValue = findViewById(R.id.withdrawnAmountValueTextView);
        double totalWithdrawals = myDB.getSumOfWithdrawals();
        withdrawnAmountValue.setText("R" + String.valueOf(totalWithdrawals));

        ConstraintLayout layout = findViewById(R.id.withdrawn_constraint_layout);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                A();
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

    private void profitCreator()
    {
        MyDBHandler myHandler = new MyDBHandler(MainActivity.this);
        FinanceHelper financeHelper = new FinanceHelper(MainActivity.this);

        TextView profitTitle = findViewById(R.id.ProfitAmountTitleTextView2);
        TextView profitValue = findViewById(R.id.profitAmountValueTextView);
        TextView profitPercentage = findViewById(R.id.percentageProfitTextView);

        double paidDebits = Double.parseDouble(financeHelper.paidAndTotalDebits(-1, -1).getItem1());
        double totalDebits = Double.parseDouble(financeHelper.paidAndTotalDebits(-1, -1).getItem2());
        double profit = paidDebits*(1+financeHelper.getInterest()) - totalDebits;
        double percentage = profit/(totalDebits)*100;

        profitTitle.setText("TOTAL BUSINESS PROFIT");
        profitValue.setText("profit: R" + profit);
        profitPercentage.setText("percentage: " + percentage + "%");
    }
}