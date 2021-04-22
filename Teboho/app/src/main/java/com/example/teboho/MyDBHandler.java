package com.example.teboho;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Map;

public class MyDBHandler extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "BusinessDataBase.db";
    private static final int DATABASE_VERSION = 3;

    //Dynamic creditors/creditors list table.
    private static final String TABLE_NAME = "DebitorsList";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SURNAME = "surname";
    private static final String COLUMN_AMOUNT1 = "amount1";
    private static final String COLUMN_AMOUNT2 = "amount2";
    private static final String COLUMN_DAY = "day";
    private static final String COLUMN_MONTH = "month";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_SYNC_INFO = "sync";

    //Static debitors list table parameters
    private static final String TABLE_NAME_DEBITORS = "DebitorsRecord";
    private static final String COLUMN_ID_DEBITORS = "_id_debitor";
    private static final String COLUMN_NAME_DEBITORS = "name_debitor";
    private static final String COLUMN_SURNAME_DEBITORS = "surname_debitor";
    private static final String COLUMN_AMOUNT1_DEBITORS = "amount1_debitor";
    private static final String COLUMN_DAY_DEBITORS = "day_debited";
    private static final String COLUMN_MONTH_DEBITORS = "month_debited";
    private static final String COLUMN_YEAR_DEBITORS = "year_debited";
    private static final String COLUMN_STATUS_DEBITORS = "debit_status";
    private static final String COLUMN_MISSED_MONTHS_DEBITORS = "debit_payment_month_missed";
    private static final String COLUMN_INTEREST_APPLIED_DEBITORS = "interest";
    private static final String COLUMN_INTEREST_APPLIED_MISSED_MONTH_CONFIRMATION_DEBITORS = "interest_confirmation";
    private static final String COLUMN_SYNC_INFO_DEBITORS = "sync_debitor";

    //Static creditors list table parameters
    private static final String TABLE_NAME_CREDITORS = "CreditorsRecord";
    private static final String COLUMN_ID_CREDITORS = "_id_creditor";
    private static final String COLUMN_NAME_CREDITORS = "name_creditor";
    private static final String COLUMN_SURNAME_CREDITORS = "surname_creditor";
    private static final String COLUMN_AMOUNT1_CREDITORS = "amount1_creditor";
    private static final String COLUMN_DAY_CREDITORS = "day_credited";
    private static final String COLUMN_MONTH_CREDITORS = "month_credited";
    private static final String COLUMN_YEAR_CREDITORS = "year_credited";
    private static final String COLUMN_SYNC_INFO_CREDITORS = "sync_creditor";

    //withdrawal parameters
    private static final String TABLE_NAME_WITHDRAWAL = "WithdrawalsRecord";
    private static final String COLUMN_ID_WITHDRAWAL = "_id_withdrawal";
    private static final String COLUMN_WITHDRAWAL_AMOUNT = "amountWithdrawn";
    private static final String COLUMN_DAY_WITHDRAWN = "dayWithdrawn";
    private static final String COLUMN_MONTH_WITHDRAWN = "monthWithdrawn";
    private static final String COLUMN_YEAR_WITHDRAWN = "yearWithdrawn";
    private static final String COLUMN_MESSAGE_WITHDRAWN = "messageWithdrawn";
    private static final String COLUMN_SYNC_INFO_WITHDRAWN = "sync_withdrawn";

    //investments table parameters
    private static final String TABLE_NAME_INVESTMENT = "InvestmentRecord";
    private static final String COLUMN_ID_INVESTMENT = "_id_investment";
    private static final String COLUMN_INVESTMENT_AMOUNT = "amountInvested";
    private static final String COLUMN_DAY_INVESTED = "dayInvested";
    private static final String COLUMN_MONTH_INVESTED = "monthInvested";
    private static final String COLUMN_YEAR_INVESTED = "yearInvested";
    private static final String COLUMN_MESSAGE_INVESTED = "messageInvested";
    private static final String COLUMN_SYNC_INFO_INVESTED = "sync_invested";

    //Security table.
    private static final String TABLE_NAME_SECURITY = "securityStorage";
    private static final String COLUMN_ID_SECURITY = "_id_security";
    private static final String COLUMN_USERNAME_SECURITY = "username_security";
    private static final String COLUMN_PASSWORD_SECURITY = "password_security";

    //Clients Personal Details Table
    private static final String TABLE_NAME_PERSONAL_DETAILS = "PersonalDetails";
    private static final String COLUMN_ID_PERSONAL_DETAILS = "_id_personal_details";
    private static final String COLUMN_NAME_PERSONAL_DETAILS = "name_personal_details";
    private static final String COLUMN_SURNAME_PERSONAL_DETAILS = "surname_personal_details";
    private static final String COLUMN_DOB_PERSONAL_DETAILS = "date_of_birth_personal_details";
    private static final String COLUMN_CONTACT_PERSONAL_DETAILS = "contact_personal_details";
    private static final String COLUMN_GENDER_PERSONAL_DETAILS = "gender_personal_details";
    private static final String COLUMN_HOUSE_ADDRESS_PERSONAL_DETAILS = "house_address_personal_details";
    private static final String COLUMN_EMPLOYMENT_PERSONAL_DETAILS = "employment_personal_details";
    private static final String COLUMN_MONTHLY_INCOME_PERSONAL_DETAILS = "monthly_income_personal_details";
    private static final String COLUMN_SYNC_INFO_PERSONAL_DETAILS = "sync_personal_details";

     MyDBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_SURNAME + " TEXT, " +
                        COLUMN_AMOUNT1 + " REAL, " +
                        COLUMN_AMOUNT2 + " REAL, " +
                        COLUMN_DAY + " INTEGER, " +
                        COLUMN_MONTH + " INTEGER, " +
                        COLUMN_YEAR + " INTEGER, " +
                        COLUMN_SYNC_INFO + " TEXT);";


        String query2 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_SECURITY +
                " (" + COLUMN_ID_SECURITY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME_SECURITY + " TEXT, " +
                COLUMN_PASSWORD_SECURITY + " TEXT);";

        String query3 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_WITHDRAWAL +
                " (" + COLUMN_ID_WITHDRAWAL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_WITHDRAWAL_AMOUNT + " REAL, " +
                COLUMN_DAY_WITHDRAWN + " INTEGER, " +
                COLUMN_MONTH_WITHDRAWN + " INTEGER, " +
                COLUMN_YEAR_WITHDRAWN + " INTEGER, " +
                COLUMN_MESSAGE_WITHDRAWN + " TEXT, " +
                COLUMN_SYNC_INFO_WITHDRAWN + " TEXT);";

        String query4 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_INVESTMENT +
                " (" + COLUMN_ID_INVESTMENT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_INVESTMENT_AMOUNT + " REAL, " +
                COLUMN_DAY_INVESTED + " INTEGER, " +
                COLUMN_MONTH_INVESTED + " INTEGER, " +
                COLUMN_YEAR_INVESTED  + " INTEGER, " +
                COLUMN_MESSAGE_INVESTED + " TEXT, " +
                COLUMN_SYNC_INFO_INVESTED + " TEXT);";

        String query5 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_DEBITORS +
                " (" + COLUMN_ID_DEBITORS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_DEBITORS + " TEXT, " +
                COLUMN_SURNAME_DEBITORS + " TEXT, " +
                COLUMN_AMOUNT1_DEBITORS + " REAL, " +
                COLUMN_DAY_DEBITORS + " INTEGER, " +
                COLUMN_MONTH_DEBITORS + " INTEGER, " +
                COLUMN_YEAR_DEBITORS + " INTEGER, " +
                COLUMN_STATUS_DEBITORS + " TEXT, " +
                COLUMN_MISSED_MONTHS_DEBITORS + " INTEGER, " +
                COLUMN_INTEREST_APPLIED_DEBITORS + " REAL, " +
                COLUMN_INTEREST_APPLIED_MISSED_MONTH_CONFIRMATION_DEBITORS + " TEXT, " +
                COLUMN_SYNC_INFO_DEBITORS + " TEXT);";

        String query6 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CREDITORS +
                " (" + COLUMN_ID_CREDITORS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_CREDITORS + " TEXT, " +
                COLUMN_SURNAME_CREDITORS + " TEXT, " +
                COLUMN_AMOUNT1_CREDITORS + " REAL, " +
                COLUMN_DAY_CREDITORS + " INTEGER, " +
                COLUMN_MONTH_CREDITORS + " INTEGER, " +
                COLUMN_YEAR_CREDITORS + " INTEGER, " +
                COLUMN_SYNC_INFO_CREDITORS + " TEXT);";

        String query7 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PERSONAL_DETAILS +
                " (" + COLUMN_ID_PERSONAL_DETAILS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_PERSONAL_DETAILS + " TEXT, " +
                COLUMN_SURNAME_PERSONAL_DETAILS + " TEXT, " +
                COLUMN_DOB_PERSONAL_DETAILS + " TEXT, " +
                COLUMN_CONTACT_PERSONAL_DETAILS + " TEXT, " +
                COLUMN_GENDER_PERSONAL_DETAILS + " TEXT, " +
                COLUMN_HOUSE_ADDRESS_PERSONAL_DETAILS + " TEXT, " +
                COLUMN_EMPLOYMENT_PERSONAL_DETAILS + " TEXT, " +
                COLUMN_MONTHLY_INCOME_PERSONAL_DETAILS + " REAL, " +
                COLUMN_SYNC_INFO_PERSONAL_DETAILS + " TEXT);";

        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(query4);
        db.execSQL(query5);
        db.execSQL(query6);
        db.execSQL(query7);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SECURITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DEBITORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CREDITORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_WITHDRAWAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_INVESTMENT);*/
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PERSONAL_DETAILS);
        onCreate(db);
    }
    void addDebitor (String name, String surname, double amount1, double amount2, int day,
                     int month, int year, double interest, boolean note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put (COLUMN_NAME, name);
        cv.put (COLUMN_SURNAME, surname);
        cv.put (COLUMN_AMOUNT1, amount1);
        cv.put (COLUMN_AMOUNT2, amount2);
        cv.put (COLUMN_DAY, day);
        cv.put (COLUMN_MONTH, month);
        cv.put (COLUMN_YEAR, year);
        cv.put (COLUMN_SYNC_INFO, "new");

        long result = db.insert (TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(context, "Added succesffully!", Toast.LENGTH_SHORT).show();
            addDebitRecord(name, surname, amount1, interest, note);
        }
        db.close();
    }

    void addClientPersonalDetails (String name, String surname, String contact, String identity, String address,
                     double monthlyIncome, boolean employment, boolean gender)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put (COLUMN_NAME_PERSONAL_DETAILS, name);
        cv.put (COLUMN_SURNAME_PERSONAL_DETAILS, surname);
        cv.put (COLUMN_CONTACT_PERSONAL_DETAILS, contact);
        cv.put (COLUMN_DOB_PERSONAL_DETAILS, identity);
        cv.put (COLUMN_HOUSE_ADDRESS_PERSONAL_DETAILS, address);
        cv.put (COLUMN_MONTHLY_INCOME_PERSONAL_DETAILS, monthlyIncome);
        cv.put(COLUMN_SYNC_INFO_PERSONAL_DETAILS, "new");

        if (employment)
            cv.put (COLUMN_EMPLOYMENT_PERSONAL_DETAILS, "employed");
        else
            cv.put (COLUMN_EMPLOYMENT_PERSONAL_DETAILS, "unemployed");

        if (gender)
            cv.put (COLUMN_GENDER_PERSONAL_DETAILS, "female");
        else
            cv.put (COLUMN_GENDER_PERSONAL_DETAILS, "male");

        long result = db.insert (TABLE_NAME_PERSONAL_DETAILS, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed: handler-> client personal details insertion", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(context, "Client personal details added succesffully!", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    Cursor readAllData()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null)
        {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readAllDataDebitorRecord()
    {
        String query = "SELECT * FROM " + TABLE_NAME_DEBITORS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null)
        {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readAllDataClientPersonalDetails()
    {
        String query = "SELECT * FROM " + TABLE_NAME_PERSONAL_DETAILS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null)
        {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readAllDataSecurity()
    {
        String query = "SELECT * FROM " + TABLE_NAME_SECURITY;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null)
        {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String id, double balance)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        if (balance == 0) //the client has fully paid the debt
            deleteOneRow(id);
        else // the client did not pay the full amount
        {
            cv.put(COLUMN_AMOUNT2, balance);

            long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{id});
            if (result == -1)
                Toast.makeText(context, "Failed to update.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    void updateData(String id, double newDebited, double newExpected)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        if ((newDebited > 0 )&&(newExpected > 0))
        {
            cv.put(COLUMN_AMOUNT1, newDebited);
            cv.put(COLUMN_AMOUNT2, newExpected);

            long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{id});
            if (result == -1)
                Toast.makeText(context, "Failed to update.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show();
        }

        if (newDebited == 0 && newExpected == 0)
            deleteOneRow(id);

        db.close();
    }

    void updateDebitRecordData(String id, String status, int missedMonths)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        if (missedMonths != -1 || status.equals("paid"))
        {
            if (missedMonths != -1)
                cv.put(COLUMN_MISSED_MONTHS_DEBITORS, missedMonths);
            if (status.equals("paid"))
                cv.put(COLUMN_STATUS_DEBITORS, status);

            cv.put(COLUMN_SYNC_INFO_DEBITORS, "false");

            long result = db.update(TABLE_NAME_DEBITORS, cv, "_id_debitor=?", new String[]{id});
            if (result == -1)
                Toast.makeText(context, "Update failed: status or missed months.", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    void updatePassword(String username, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME_SECURITY, username);
        cv.put(COLUMN_PASSWORD_SECURITY, password);

        String id = "1";
        long result = db.update(TABLE_NAME_SECURITY,cv,"_id_security", new String[]{id});

        if (result == -1)
            Toast.makeText(context, "Failed to update security.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Security update successfully", Toast.LENGTH_SHORT).show();

        db.close();
    }

    void automatedPassword()
    {
        if (!isTableNotEmpty(TABLE_NAME_SECURITY)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            String username = "Teboho";
            String password = "1234";

            cv.put(COLUMN_USERNAME_SECURITY, username);
            cv.put(COLUMN_PASSWORD_SECURITY, password);

            long result = db.insert (TABLE_NAME_SECURITY, null, cv);

            if (result == -1) {
                Toast.makeText(context, "Failed to create first password", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "first password created succesffully!", Toast.LENGTH_SHORT).show();
            }

            db.close();
        }
    }

    boolean isTableNotEmpty(String tableName)
    {
        boolean empty = true;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);

        if (cursor != null && cursor.moveToFirst())
        {
            empty = (cursor.getInt(0) == 1);
        }

        cursor.close();
        db.close();
        return empty;
    }

    void deleteOneRow (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{id});
        if (result == -1){
            Toast.makeText(context, "Failed to delete client", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Client successfully deleted", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    void deleteOneRowDebitRecord (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_DEBITORS, "_id_debitor=?", new String[]{id});
        if (result == -1){
            Toast.makeText(context, "Failed to delete debit record", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "debit successfully deleted", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    MyTuple getID(String name, String surname)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        int id = -1, position = 0, counter = 0;

        if (cursor.getCount() == 0)
        {
            Toast.makeText(context, "No data on dynamic list", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (cursor.moveToNext())
            {
                if (name.equals(cursor.getString(1)) && surname.equals(cursor.getString(2))) {
                    id = cursor.getInt(0);
                    position = counter;
                }
                counter++;
            }
        }

        db.close();
        cursor.close();

        MyTuple myTuple = new MyTuple(String.valueOf(id), String.valueOf(position));
        return myTuple;
    }

    void deleteAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }

    void  addDebitRecord (String name, String surname, double amount, double interest, boolean note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        DateCapture date = new DateCapture();

        cv.put (COLUMN_NAME_DEBITORS, name);
        cv.put (COLUMN_SURNAME_DEBITORS, surname);
        cv.put (COLUMN_AMOUNT1_DEBITORS, amount);
        cv.put (COLUMN_DAY_DEBITORS, date.getCurrentDay());
        cv.put (COLUMN_MONTH_DEBITORS, date.getCurrentMonth());
        cv.put (COLUMN_YEAR_DEBITORS, date.getCurrentYear());
        cv.put(COLUMN_STATUS_DEBITORS, "unpaid");
        cv.put(COLUMN_MISSED_MONTHS_DEBITORS, 0);
        cv.put (COLUMN_INTEREST_APPLIED_DEBITORS, interest);
        cv.put(COLUMN_SYNC_INFO_DEBITORS, "new");

        if (note)
            cv.put(COLUMN_INTEREST_APPLIED_MISSED_MONTH_CONFIRMATION_DEBITORS, "yes");
        else
            cv.put(COLUMN_INTEREST_APPLIED_MISSED_MONTH_CONFIRMATION_DEBITORS, "no");

        long result = db.insert (TABLE_NAME_DEBITORS, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to add client debit record", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(context, "Added successfully to debit record!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    void addCreditRecord (String name, String surname, double amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        DateCapture date = new DateCapture();

        cv.put (COLUMN_NAME_CREDITORS, name);
        cv.put (COLUMN_SURNAME_CREDITORS, surname);
        cv.put (COLUMN_AMOUNT1_CREDITORS, amount);
        cv.put (COLUMN_DAY_CREDITORS, date.getCurrentDay());
        cv.put (COLUMN_MONTH_CREDITORS, date.getCurrentMonth());
        cv.put (COLUMN_YEAR_CREDITORS, date.getCurrentYear());
        long result = db.insert (TABLE_NAME_CREDITORS, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to add client credit record", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(context, "Added successfully to credit record!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    void addInvestment(double amount3, int day, int month, int year, String message)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_INVESTMENT_AMOUNT, amount3);
        cv.put(COLUMN_DAY_INVESTED, day);
        cv.put(COLUMN_MONTH_INVESTED, month);
        cv.put(COLUMN_YEAR_INVESTED, year);
        cv.put(COLUMN_MESSAGE_INVESTED, message);

        long result = db.insert(TABLE_NAME_INVESTMENT, null, cv);

        if (result == -1){
            Toast.makeText(context, "Failed to add investment", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Investment added successfully!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    void addWithdrawal(double amount4, int day, int month, int year, String msg)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WITHDRAWAL_AMOUNT, amount4);
        cv.put(COLUMN_DAY_WITHDRAWN, day);
        cv.put(COLUMN_MONTH_WITHDRAWN, month);
        cv.put(COLUMN_YEAR_WITHDRAWN, year);
        cv.put(COLUMN_MESSAGE_WITHDRAWN, msg);

        long result = db.insert(TABLE_NAME_WITHDRAWAL, null, cv);

        if (result == -1){
            Toast.makeText(context, "Failed to add withdrawal", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Withdrawal added successfully!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    double getStaticSumOfDebits()
    {
        double sum = 0;
        String query = "SELECT * FROM " + TABLE_NAME_DEBITORS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            do{
                sum += cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT1_DEBITORS));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return sum;
    }

    double getStaticSumOfCredits()
    {
        double sum = 0;
        String query = "SELECT * FROM " + TABLE_NAME_CREDITORS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            do{
                sum += cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT1_CREDITORS));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return sum;
    }

    double getSumOfInvestments()
    {
        double sum = 0;
        String query = "SELECT * FROM " + TABLE_NAME_INVESTMENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            do{
                sum += cursor.getDouble(cursor.getColumnIndex(COLUMN_INVESTMENT_AMOUNT));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return sum;
    }

    double getSumOfWithdrawals()
    {
        double sum = 0;
        String query = "SELECT * FROM " + TABLE_NAME_WITHDRAWAL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            do{
                sum += cursor.getDouble(cursor.getColumnIndex(COLUMN_WITHDRAWAL_AMOUNT));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return sum;
    }

    double getDynamicSumOfDebits()
    {
        double sum = 0;
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            do{
                sum += cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return sum;
    }

    void updateSyncStatus(String tableName, String id, String sync)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long result = -1;

        if (tableName.equals("DebitorsList"))
        {
            cv.put(COLUMN_SYNC_INFO, sync);
            result = db.update(TABLE_NAME, cv, "_id=?", new String[]{id});
        }
        else if (tableName.equals("DebitorsRecord"))
        {
            cv.put(COLUMN_SYNC_INFO_DEBITORS, sync);
            result = db.update(TABLE_NAME_DEBITORS, cv, "_id_debitor=?", new String[]{id});
        }
        else if (tableName.equals("PersonalDetails"))
        {
            cv.put(COLUMN_SYNC_INFO_PERSONAL_DETAILS, sync);
            result = db.update(TABLE_NAME_PERSONAL_DETAILS, cv, "_id_personal_details=?", new String[]{id});
        }

        if (result == -1)
            Toast.makeText(context, "Failed to update sync: " + tableName, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Update successful: " + tableName, Toast.LENGTH_SHORT).show();

        db.close();
    }

    Cursor readAllDataWithdrawal()
    {
        String query = "SELECT * FROM " + TABLE_NAME_WITHDRAWAL;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null)
        {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readAllDataInvestment()
    {
        String query = "SELECT * FROM " + TABLE_NAME_INVESTMENT;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null)
        {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
