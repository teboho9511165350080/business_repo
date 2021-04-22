package com.example.teboho;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

public class FinanceHelper {

    Context context;
    double interest;

    public FinanceHelper(Context context){
        this.context = context;
        interest = 0.5;
    }

    public double getInterest() {return interest;}

    public void autoUpdatePaymentMissed()
    {
        DateCapture dateCapture = new DateCapture();
        MyDBHandler myDBHandler = new MyDBHandler(context);
        Cursor cursor = myDBHandler.readAllDataDebitorRecord();

        if (cursor.getCount() == 0)
        {
            Toast.makeText(context, "No data to perform auto payments update", Toast.LENGTH_SHORT).show();
        }
        else {
            if (cursor.moveToFirst())
            {
                do {
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    String surname = cursor.getString(2);
                    double amountA = cursor.getDouble(3);
                    int day = cursor.getInt(4);
                    int month = cursor.getInt(5);
                    int year = cursor.getInt(6);
                    String status = cursor.getString(7);
                    int missedMonths = Integer.parseInt(cursor.getString(8));
                    double interest_ = Double.parseDouble(cursor.getString(9));
                    String interest_applied = cursor.getString(10).trim();

                   if (dateCapture.isPayDayBridged(day, month, year) && status.equals("unpaid")
                           && interest_ != 0 && interest_applied.equals("yes")) {
                       if (dateCapture.getBridgedMonths() > missedMonths)
                           updateClientDebit(id, name, surname, amountA, dateCapture.getBridgedMonths(), missedMonths);
                   }
                } while (cursor.moveToNext());
             }
        }
        cursor.close();
    }

    private void updateClientDebit(String id, String name, String surname, double amountA, int bridgedMonths, int missedMonths)
    {
        MyDBHandler myDBHandler = new MyDBHandler(context);

        String ID_dynamicList = myDBHandler.getID(name, surname).item1;
        int location = Integer.parseInt(myDBHandler.getID(name, surname).item2);
        double expected;

        Cursor cursor = myDBHandler.readAllData();

        if (cursor.getCount() == 0)
        {
            Toast.makeText(context, "No data available for auto updating client in dynamic list", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (cursor.moveToPosition(location))
            {
                if (missedMonths > 0)
                    expected = cursor.getDouble(4) + amountA*Math.pow(1+interest, bridgedMonths)-amountA*Math.pow(1+interest, missedMonths);
                else
                    expected = cursor.getDouble(4) + amountA*Math.pow(1+interest, bridgedMonths)-(amountA*(1+interest));

                myDBHandler.updateData(ID_dynamicList, expected);
                myDBHandler.updateDebitRecordData(id, "nothing", bridgedMonths);
            }
            else
                Toast.makeText(context, "FinanceHelper: requested dynamicList position error", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    public void processClientPayment (String id, String name, String surname, double expected, double paid)
    {
        double balance;
        balance = expected-paid;

        MyDBHandler myDB = new MyDBHandler(context);
        myDB.addCreditRecord(name, surname, paid);
        Cursor cursor = myDB.readAllDataDebitorRecord();

        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
        } else {
            if (balance == 0)
            {
                if (cursor.moveToFirst()){
                    do{
                        if (name.equals(cursor.getString(1)) && surname.equals(cursor.getString(2))
                                && cursor.getString(7).equals("unpaid")) {

                            myDB.updateDebitRecordData(cursor.getString(0), "paid", -1);
                        }
                    }while(cursor.moveToNext());
                }
            }
            else
            {
                if (cursor.moveToFirst()){
                    do{
                        if (name.equals(cursor.getString(1)) && surname.equals(cursor.getString(2))
                                && isCorrectPayment(cursor, paid) && cursor.getString(7).equals("unpaid")) {

                            double paid_copy = paid;

                            if (cursor.getInt(8) == 0)
                                paid = paid_copy - cursor.getDouble(3)*Math.pow(1+interest, 1);
                            else
                                paid = paid_copy - (cursor.getDouble(3) * Math.pow(1 + interest, cursor.getInt(8)));

                            myDB.updateDebitRecordData(cursor.getString(0), "paid", -1);
                        }
                    }while(cursor.moveToNext());
                }
            }
        }

        myDB.updateData(id, balance);
        myDB.close();
        cursor.close();
    }

    boolean isCorrectPayment(Cursor cursor, double paid)
    {
        if (cursor.getInt(8) == 0) //there are no payments months missed
            return paid >= cursor.getDouble(3)*Math.pow(1+cursor.getDouble(9), 1);
        else
            return paid >= cursor.getDouble(3)*Math.pow(1+cursor.getDouble(9), cursor.getInt(8));
    }

    MyTuple paidAndTotalDebits(int month, int year)
    {
        MyDBHandler myDBHandler = new MyDBHandler(context);
        Cursor cursor = myDBHandler.readAllDataDebitorRecord();

        double totalDebits = 0, paidDebits = 0;

        if (cursor.getCount() == 0)
            Toast.makeText(context, "No data for display profit", Toast.LENGTH_SHORT).show();
        else
        {
            if (cursor.moveToFirst())
            {
                do{
                    if (month != -1 && year != -1) {
                        if (cursor.getInt(5) == month && cursor.getInt(6) == year) {
                            double a = totalDebits;

                            totalDebits = cursor.getDouble(3) + a;

                            if (cursor.getString(7).equals("paid")) {
                                double b = paidDebits;

                                if (cursor.getInt(8) == 0)
                                    paidDebits = cursor.getDouble(3) + b;
                                else
                                    paidDebits = cursor.getDouble(3) * Math.pow(1 + cursor.getDouble(9), cursor.getInt(8) - 1) + b;
                            }
                        }
                    }
                    else
                    {
                        double a = totalDebits;

                        if (cursor.getInt(8) == 0)
                            totalDebits = cursor.getDouble(3) + a;
                        else
                            totalDebits = cursor.getDouble(3) * Math.pow(1 + cursor.getDouble(9), cursor.getInt(8) - 1) + a;

                        if (cursor.getString(7).equals("paid")) {
                            double b = paidDebits;

                            if (cursor.getInt(8) == 0)
                                paidDebits = cursor.getDouble(3) + b;
                            else
                                paidDebits = cursor.getDouble(3) * Math.pow(1 + cursor.getDouble(9), cursor.getInt(8) - 1) + b;
                        }
                    }
                }while(cursor.moveToNext());
            }
            else
                Toast.makeText(context, "Failed to read data", Toast.LENGTH_SHORT).show();
        }

        MyTuple myTuple = new MyTuple(String.valueOf(paidDebits), String.valueOf(totalDebits));
        cursor.close();
        myDBHandler.close();

        return myTuple;
    }
}
