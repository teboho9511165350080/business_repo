package com.example.teboho;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

public class Client{

    Context context;
    String name, surname, gender, contact, address;
    String AmountA, AmountB;
    String day, month, year;
    String id;
    String sync;

    private Boolean newClient = true;
    private Boolean clientRegistered = false;
    private double HighestDebit = 0;
    private double profitGenerated = 0;
    private double lossGenerated = 0;
    private double totalCurrentDebits = 0;
    private double issuable_amount = 0;

    Client (Context context, String name, String surname){

        this.context = context;
        this.name = name;
        this.surname = surname;
        checkClientExistence();
        compileFinancialRecord();
        checkClientRegistration();
    }

    Client (String id, String name, String surname, String gender, String contact, String address){

        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.contact = contact;
        this.address = address;
        this.id = id;
    }

    Client (String id, String name, String surname, String AmountA, String AmountB,
            String day, String month, String year, String sync){

        this.id = id;
        this.name = name;
        this.surname = surname;
        this.AmountA = AmountA;
        this.AmountB = AmountB;
        this.day = day;
        this.month = month;
        this.year = year;
        this.sync = sync;
    }

    Client (String id, String name, String surname, String AmountA,
            String day, String month, String year, String sync){

        this.id = id;
        this.name = name;
        this.surname = surname;
        this.AmountA = AmountA;
        this.day = day;
        this.month = month;
        this.year = year;
        this.sync = sync;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getAmountA() {
        return AmountA;
    }
    public String getGender() {
        return gender;
    }
    public String getContact() {
        return contact;
    }
    public String getAddress() {
        return address;
    }

    public String getAmountB() { return AmountB; }
    public String getDay(){ return day; }
    public String getMonth(){ return month; }
    public String getYear(){return year; }

    public String getSync(){return sync;}

    public double getHighestDebit() {
        return HighestDebit;
    }
    public double getProfitGenerated() {
        return profitGenerated;
    }
    public double getLossGenerated() {
        return lossGenerated;
    }
    public double getTotalCurrentDebits() {
        return totalCurrentDebits;
    }
    public double getIssuable_amount() {
        return issuable_amount;
    }
    public boolean isClientNew(){
        return newClient;
    }

    public boolean isClientPersonalDetailsStored(){ return clientRegistered; }

    private void checkClientExistence(){

        MyDBHandler myDBHandler = new MyDBHandler(context);
        Cursor cursor = myDBHandler.readAllDataDebitorRecord();

        if (cursor.getCount() == 0)
        {
            Toast.makeText(context, "No data to check if client is new", Toast.LENGTH_SHORT).show();
        }
        else {
            if (cursor.moveToFirst())
            {
                do {
                    if (cursor.getString(1).equals(name) && cursor.getString(2).equals(surname)) {
                        newClient = false;
                        break;
                    }
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        myDBHandler.close();
    }

    private void compileFinancialRecord()
    {
        if (!isClientNew())
        {
            DateCapture dateCapture = new DateCapture(Integer.parseInt(getStartEndDate().getItem1()),
                    Integer.parseInt(getStartEndDate().getItem2()), Integer.parseInt(getStartEndDate().getItem3()),
                    Integer.parseInt(getStartEndDate().getItem4()));

            MyDBHandler myDBHandler = new MyDBHandler(context);
            Cursor cursor = myDBHandler.readAllDataDebitorRecord();

            if (cursor.getCount() == 0)
            {
                Toast.makeText(context, "No data to check if client is new", Toast.LENGTH_SHORT).show();
            }
            else {
                do {
                    int month = dateCapture.getStartMonth();
                    int year = dateCapture.getStartYear();
                    double a = 0;

                    if (cursor.moveToFirst()) {
                        do {
                            if (name.equals(cursor.getString(1)) && surname.equals(cursor.getString(2))) {
                                if (month == cursor.getInt(5) && year == cursor.getInt(6)) {
                                    //calculating the total monthly debited amount for a particular client
                                    double b = cursor.getDouble(3);
                                    a += b;

                                    //calculating profit generated
                                    if (cursor.getString(7).equals("paid")){
                                        double delta_profit = cursor.getDouble(3)*(Math.pow(1+cursor.getDouble(9), 1+cursor.getInt(8))-1);
                                        profitGenerated += delta_profit;
                                    }

                                    //calculating loss generated
                                    if (!cursor.getString(7).equals("paid") && cursor.getInt(8) != 0)
                                    {
                                        double delta_loss = cursor.getDouble(3)*(Math.pow(1+cursor.getDouble(9), 1+cursor.getInt(8))-1);
                                        lossGenerated += delta_loss;
                                    }

                                    //calculating the total debits of a client for the current 30 days period.
                                    if (!cursor.getString(7).equals("paid") && cursor.getInt(8) == 0)
                                    {
                                        double delta_debit = cursor.getDouble(3);
                                        totalCurrentDebits += delta_debit;
                                    }
                                }
                            }
                        } while (cursor.moveToNext());
                    }

                    //updating the highest debited monthly amount
                    if (a >= HighestDebit)
                        HighestDebit = a;

                }while(dateCapture.moveToNextMonth());
            }

            calculateIssuableAmount();

            cursor.close();
            myDBHandler.close();
        }
    }

    private void calculateIssuableAmount()
    {
        if (!isClientNew()) {
            if (profitGenerated >= 2 * HighestDebit) //client reahed the minimum reliability breakeven point
            {
                issuable_amount = (profitGenerated / 2) - (totalCurrentDebits + lossGenerated);
            } else  // the client hasn't reached the minimum reliability breakeven point
            {
                issuable_amount = HighestDebit - totalCurrentDebits;
            }
        }
    }

    MyTuple getStartEndDate()
    {
        MyDBHandler myDBHandler = new MyDBHandler(context);
        Cursor cursor = myDBHandler.readAllDataDebitorRecord();
        MyTuple myTuple = null;

        if (cursor.getCount() == 0)
        {
            Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String startMonth, startYear, endMonth, endYear;

            if (cursor.moveToFirst())
            {
                startMonth = cursor.getString(5);
                startYear = cursor.getString(6);

                if (cursor.moveToLast())
                {
                    endMonth = cursor.getString(5);
                    endYear = cursor.getString(6);

                    myTuple = new MyTuple(startMonth, startYear, endMonth, endYear);
                }
            }
            cursor.close();
        }

        myDBHandler.close();

        return myTuple;
    }

    private void checkClientRegistration() {

        MyDBHandler myDBHandler = new MyDBHandler(context);
        Cursor cursor = myDBHandler.readAllDataClientPersonalDetails();

        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No client personal data available", Toast.LENGTH_SHORT).show();
        } else {
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getString(1).equals(name) && cursor.getString(2).equals(surname)) {
                        clientRegistered = true;
                        break;
                    }
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        myDBHandler.close();
    }
}
