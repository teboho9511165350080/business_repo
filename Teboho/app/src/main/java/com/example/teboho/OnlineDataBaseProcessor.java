package com.example.teboho;

import android.content.Context;
import android.database.Cursor;

public class OnlineDataBaseProcessor {
    Context context;

    OnlineDataBaseProcessor(Context context){
        this.context = context;
    }

    protected void masterUpdateOnlineDatabase(String syncronize)
    {
        updateOnlineDataBase(syncronize);
        updateOnlineDataBaseDebitorsRecord(syncronize);
        updateOnlineDataBasePersonalDetailsList(syncronize);
        updateOnlineDataBaseCreditorsRecord(syncronize);
    }

    protected void updateOnlineDataBaseCreditorsRecord(String syncronize)
    {
        //inserting a new person
        MyDBHandler myDBHandler = new MyDBHandler(context);
        Cursor cursor = myDBHandler.readAllDataCredit();

        if (cursor.moveToFirst())
        {
            do{
                if (!cursor.getString(7).equals("true")|| syncronize.equals("All")) //the is data that needs to be sync to online database
                {
                    OnlineDBHandler onlineDBHandler = new OnlineDBHandler(context);

                    onlineDBHandler.execute("add new creditor", cursor.getString(0), cursor.getString(1),
                            cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),
                            cursor.getString(6), cursor.getString(7));

                    MyDBHandler newHandler = new MyDBHandler(context);
                    if (onlineDBHandler.checkNetworkConnection())
                    {
                        newHandler.updateSyncStatus("CreditorsList", cursor.getString(0), "true");
                    }

                    newHandler.close();
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        myDBHandler.close();
    }

    protected void updateOnlineDataBase(String syncronize)
    {
        //inserting a new person
        MyDBHandler myDBHandler = new MyDBHandler(context);
        Cursor cursor = myDBHandler.readAllData();

        if (cursor.moveToFirst())
        {
            do{
                if (!cursor.getString(8).equals("true") || syncronize.equals("All")) //the is data that needs to be sync to online database
                {
                    OnlineDBHandler onlineDBHandler = new OnlineDBHandler(context);

                    onlineDBHandler.execute("add new debitor", cursor.getString(0), cursor.getString(1),
                            cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),
                            cursor.getString(6), cursor.getString(7), cursor.getString(8));

                    MyDBHandler newHandler = new MyDBHandler(context);

                    if (onlineDBHandler.checkNetworkConnection())
                    {
                        newHandler.updateSyncStatus("DebitorsList", cursor.getString(0), "true");
                    }
                    newHandler.close();
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        myDBHandler.close();
    }

    protected void updateOnlineDataBaseDebitorsRecord(String syncronize)
    {
        //inserting a new person
        MyDBHandler myDBHandler = new MyDBHandler(context);
        Cursor cursor = myDBHandler.readAllDataDebitorRecord();

        if (cursor.moveToFirst())
        {
            do{
                if (cursor.getString(11).equals("false") || cursor.getString(11).equals("new") ||
                        syncronize.equals("All")) //the is data that needs to be sync to online database
                {
                    OnlineDBHandler onlineDBHandler = new OnlineDBHandler(context);

                    String method = "debitors record online";
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    String surname = cursor.getString(2);
                    String amount1 = cursor.getString(3);
                    String day = cursor.getString(4);
                    String month = cursor.getString(5);
                    String year = cursor.getString(6);
                    String debit_status = cursor.getString(7);
                    String debit_payment_month_missed = cursor.getString(8);
                    String interest = cursor.getString(9);
                    String interest_confirmation = cursor.getString(10);
                    String sync = cursor.getString(11);

                    onlineDBHandler.execute(method, id, name, surname, amount1, day, month, year,
                            debit_status, debit_payment_month_missed, interest, interest_confirmation,
                            sync);
                    MyDBHandler newHandler = new MyDBHandler(context);

                    if (onlineDBHandler.checkNetworkConnection())
                    {
                        newHandler.updateSyncStatus("DebitorsRecord", id, "true");
                    }

                    newHandler.close();
                }
            }while(cursor.moveToNext());
        }

        cursor.close();
        myDBHandler.close();
    }

    protected void updateOnlineDataBasePersonalDetailsList(String syncronize)
    {
        MyDBHandler myDBHandler = new MyDBHandler(context);
        Cursor cursor = myDBHandler.readAllDataClientPersonalDetails();

        if (cursor.moveToFirst())
        {
            do{
                if (!cursor.getString(9).equals("true") || syncronize.equals("All")) //the is data that needs to be sync to online database
                {
                    OnlineDBHandler onlineDBHandler = new OnlineDBHandler(context);

                    String method = "clients personal details online";
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    String surname = cursor.getString(2);
                    String DOB = cursor.getString(3);
                    String contact = cursor.getString(4);
                    String gender = cursor.getString(5);
                    String address = cursor.getString(6);
                    String employment = cursor.getString(7);
                    String income = cursor.getString(8);
                    String sync = cursor.getString(9);

                    onlineDBHandler.execute(method, id, name, surname, DOB, contact, gender, address,
                            employment, income, sync);

                    MyDBHandler newHandler = new MyDBHandler(context);
                    if (onlineDBHandler.checkNetworkConnection())
                        newHandler.updateSyncStatus("PersonalDetails", id, "true");
                    newHandler.close();
                }
            }while(cursor.moveToNext());
        }

        cursor.close();
        myDBHandler.close();
    }
}

