package com.example.teboho;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class OnlineDBHandler extends AsyncTask <String, Void, String> {

    Context context;

    OnlineDBHandler(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        if (params[2].indexOf('\'') != -1) {
            String a = "\'";
            params[2] = params[2].split(a)[0] + a + a + params[2].split(a)[1];
        }
        if (params[3].indexOf('\'') != -1) {
            String a = "\'";
            params[3] = params[3].split(a)[0] + a + a + params[3].split(a)[1];
        }

        if (params[0].equals("add new debitor"))
            return debitorsCreditorsListHelper (params);
        else if (params[0].equals("debitors record online"))
            return  debitorsRecordListHelper(params);
        else if (params[0].equals("clients personal details online"))
            return clientsPersonalInformationListHelper(params);
        else if (params[0].equals("new installation debitors data"))
            return debitorsRecordListHelperFetch ();
        else if (params[0].equals("new installation debitors:creditors data"))
            return debitorsCreditorsRecordListHelperFetch ();
        else if (params[0].equals("new installation personal data"))
            return clientsPersonalInformationListHelperFetch ();
        else if (params[0].equals("add new creditor"))
            return CreditorsListHelper (params);
        else if (params[0].equals("new installation creditors data"))
            return CreditorsListHelperFetch ();
        else
            return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }

    private String debitorsCreditorsListHelper (String... params)
    {
        String client_url = "http://lekenoempire.co.za/test/DynamicList/add_client.php";

        if (params[9].equals("false"))
            client_url = "http://lekenoempire.co.za/test/DynamicList/update_client.php";

        if (params[9].equals("delete"))
            client_url = "http://lekenoempire.co.za/test/DynamicList/delete_client.php";

        String method = params[0];

        if (method.equals("add new debitor"))
        {
            String id = params[1];
            String name = params[2];
            String surname = params[3];
            String amount1 = params[4];
            String amount2 = params[5];
            String day = params[6];
            String month = params[7];
            String year = params[8];

            try {
                URL url = new URL(client_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(1000);
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("_id", "UTF-8") +"="+URLEncoder.encode(id, "UTF-8")+"&"+
                        URLEncoder.encode("name", "UTF-8") +"="+URLEncoder.encode(name, "UTF-8")+"&"+
                        URLEncoder.encode("surname", "UTF-8") +"="+URLEncoder.encode(surname, "UTF-8")+"&"+
                        URLEncoder.encode("amount1", "UTF-8") +"="+URLEncoder.encode(amount1, "UTF-8")+"&"+
                        URLEncoder.encode("amount2", "UTF-8") +"="+URLEncoder.encode(amount2, "UTF-8")+"&"+
                        URLEncoder.encode("day", "UTF-8") +"="+URLEncoder.encode(day, "UTF-8")+"&"+
                        URLEncoder.encode("month", "UTF-8") +"="+URLEncoder.encode(month, "UTF-8")+"&"+
                        URLEncoder.encode("year", "UTF-8") +"="+URLEncoder.encode(year, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Registration Success";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private String debitorsCreditorsRecordListHelperFetch ()
    {
        String JSON_STRING;

        String data_url = "http://lekenoempire.co.za/test/DynamicList/get_client.php";

        try{
            URL url = new URL(data_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();

            while((JSON_STRING = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(JSON_STRING+"\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            JSONArray jsonArray = new JSONArray(stringBuilder.toString().trim());
            MyDBHandler myDBHandler = new MyDBHandler(context);

            for (int count = 0; count < jsonArray.length(); count++)
            {
                JSONObject JO = jsonArray.getJSONObject(count);
                myDBHandler.addDebitor(JO.getInt("_id"), JO.getString("name"), JO.getString("surname"),
                        JO.getDouble("amount1"), JO.getDouble("amount2"),JO.getInt("day"), JO.getInt("month"),
                        JO.getInt("year"));
            }

            return "Online Debitors-Creditor Data Fetch Success";

        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String debitorsRecordListHelperFetch ()
    {

        String data_url = "http://lekenoempire.co.za/test/DebitorsList/get_client_debitor.php";

        try{
            URL url = new URL(data_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();

            String JSON_STRING = null;

            while((JSON_STRING = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(JSON_STRING+"\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            JSONArray jsonArray = new JSONArray(stringBuilder.toString().trim());
            MyDBHandler myDBHandler = new MyDBHandler(context);

            for (int count = 0; count < jsonArray.length(); count++)
            {
                JSONObject JO = jsonArray.getJSONObject(count);
                myDBHandler.addDebitRecord(JO.getInt("_id"), JO.getString("name"), JO.getString("surname"),
                        JO.getDouble("amount1"), JO.getInt("day"), JO.getInt("month"), JO.getInt("year"),
                        JO.getString("status"), JO.getInt("months_missed"), JO.getDouble("interest"),
                        JO.getString("interest_conf"), JO.getString("sync"));
            }

            return "Online Debitors Data Fetch Success";

        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String debitorsRecordListHelper (String... params)
    {

        String client_url = "http://lekenoempire.co.za/test/DebitorsList/add_client_debitor.php";

        if (params[12].equals("false"))
            client_url = "http://lekenoempire.co.za/test/DebitorsList/update_client_debitor.php";

        if (params[12].equals("delete"))
            client_url = "http://lekenoempire.co.za/test/DebitorsList/delete_client_debitor.php";

        String id = params[1];
        String name = params[2];
        String surname = params[3];
        String amount1 = params[4];
        String day = params[5];
        String month = params[6];
        String year = params[7];
        String debit_status = params[8];
        String debit_payment_month_missed = params[9];
        String interest = params[10];
        String interest_confirmation = params[11];
        String sync_debitor = params[12];

        try {
            URL url = new URL(client_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
            String data = URLEncoder.encode("_id_debitor", "UTF-8") +"="+URLEncoder.encode(id, "UTF-8")+"&"+
                    URLEncoder.encode("name_debitor", "UTF-8") +"="+URLEncoder.encode(name, "UTF-8")+"&"+
                    URLEncoder.encode("surname_debitor", "UTF-8") +"="+URLEncoder.encode(surname, "UTF-8")+"&"+
                    URLEncoder.encode("amount1_debitor", "UTF-8") +"="+URLEncoder.encode(amount1, "UTF-8")+"&"+
                    URLEncoder.encode("day_debited", "UTF-8") +"="+URLEncoder.encode(day, "UTF-8")+"&"+
                    URLEncoder.encode("month_debited", "UTF-8") +"="+URLEncoder.encode(month, "UTF-8")+"&"+
                    URLEncoder.encode("year_debited", "UTF-8") +"="+URLEncoder.encode(year, "UTF-8")+"&"+
                    URLEncoder.encode("debit_status", "UTF-8") +"="+URLEncoder.encode(debit_status, "UTF-8")+"&"+
                    URLEncoder.encode("debit_payment_month_missed", "UTF-8") +"="+URLEncoder.encode(debit_payment_month_missed, "UTF-8")+"&"+
                    URLEncoder.encode("interest", "UTF-8") +"="+URLEncoder.encode(interest, "UTF-8")+"&"+
                    URLEncoder.encode("interest_confirmation", "UTF-8") +"="+URLEncoder.encode(interest_confirmation, "UTF-8")+"&"+
                    URLEncoder.encode("sync_debitor", "UTF-8") +"="+URLEncoder.encode(sync_debitor, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();
            InputStream IS = httpURLConnection.getInputStream();
            IS.close();
            return "Online Debitor Insertion Success";

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String clientsPersonalInformationListHelper (String... params)
    {

        String client_url = "http://lekenoempire.co.za/test/PersonalDetailsList/add_personal_details.php";

        if (params[10].equals("false"))
            client_url = "http://lekenoempire.co.za/test/PersonalDetailsList/update_personal_details.php";

         if (params[10].equals("delete"))
           client_url = "http://lekenoempire.co.za/test/PersonalDetailsList/delete_personal_details.php";

        String id = params[1];
        String name = params[2];
        String surname = params[3];
        String DOB = params[4];
        String contact = params[5];
        String gender = params[6];
        String address = params[7];
        String employment = params[8];
        String income = params[9];
        String sync = params[10];

        try {
            URL url = new URL(client_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
            String data = URLEncoder.encode("_id_personal_details", "UTF-8") +"="+URLEncoder.encode(id, "UTF-8")+"&"+
                    URLEncoder.encode("name_personal_details", "UTF-8") +"="+URLEncoder.encode(name, "UTF-8")+"&"+
                    URLEncoder.encode("surname_personal_details", "UTF-8") +"="+URLEncoder.encode(surname, "UTF-8")+"&"+
                    URLEncoder.encode("date_of_birth_personal_details", "UTF-8") +"="+URLEncoder.encode(DOB, "UTF-8")+"&"+
                    URLEncoder.encode("contact_personal_details", "UTF-8") +"="+URLEncoder.encode(contact, "UTF-8")+"&"+
                    URLEncoder.encode("gender_personal_details", "UTF-8") +"="+URLEncoder.encode(gender, "UTF-8")+"&"+
                    URLEncoder.encode("house_address_personal_details", "UTF-8") +"="+URLEncoder.encode(address, "UTF-8")+"&"+
                    URLEncoder.encode("employment_personal_details", "UTF-8") +"="+URLEncoder.encode(employment, "UTF-8")+"&"+
                    URLEncoder.encode("monthly_income_personal_details", "UTF-8") +"="+URLEncoder.encode(income, "UTF-8")+"&"+
                    URLEncoder.encode("sync_personal_details", "UTF-8") +"="+URLEncoder.encode(sync, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();
            InputStream IS = httpURLConnection.getInputStream();
            IS.close();
            return "Online Personal Details Insertion Success";

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String clientsPersonalInformationListHelperFetch ()
    {

        String data_url = "http://lekenoempire.co.za/test/PersonalDetailsList/get_personal_details.php";

        try{
            URL url = new URL(data_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();

            String JSON_STRING = null;

            while((JSON_STRING = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(JSON_STRING+"\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            JSONArray jsonArray = new JSONArray(stringBuilder.toString().trim());
            MyDBHandler myDBHandler = new MyDBHandler(context);

            for (int count = 0; count < jsonArray.length(); count++)
            {
                JSONObject JO = jsonArray.getJSONObject(count);
                myDBHandler.addClientPersonalDetails(JO.getInt("_id"), JO.getString("name"),
                        JO.getString("surname"), JO.getString("contact"), JO.getString("DOB"),
                        JO.getString("address"), JO.getDouble("income"), JO.getString("employment"),
                        JO.getString("gender"));
            }

            return "Online Debitors Data Fetch Success";

        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String CreditorsListHelper (String... params)
    {
        String client_url = "http://lekenoempire.co.za/test/CreditorsList/add_client_creditor.php";

        String method = params[0];

        if (method.equals("add new creditor"))
        {
            String id = params[1];
            String name = params[2];
            String surname = params[3];
            String amount1 = params[4];
            String day = params[5];
            String month = params[6];
            String year = params[7];
            String sync = params[8];

            try {
                URL url = new URL(client_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(1000);
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("_id_creditor", "UTF-8") +"="+URLEncoder.encode(id, "UTF-8")+"&"+
                        URLEncoder.encode("name_creditor", "UTF-8") +"="+URLEncoder.encode(name, "UTF-8")+"&"+
                        URLEncoder.encode("surname_creditor", "UTF-8") +"="+URLEncoder.encode(surname, "UTF-8")+"&"+
                        URLEncoder.encode("amount1_creditor", "UTF-8") +"="+URLEncoder.encode(amount1, "UTF-8")+"&"+
                        URLEncoder.encode("day_credited", "UTF-8") +"="+URLEncoder.encode(day, "UTF-8")+"&"+
                        URLEncoder.encode("month_credited", "UTF-8") +"="+URLEncoder.encode(month, "UTF-8")+"&"+
                        URLEncoder.encode("year_credited", "UTF-8") +"="+URLEncoder.encode(year, "UTF-8")+"&"+
                        URLEncoder.encode("sync_creditor", "UTF-8") +"="+URLEncoder.encode(sync, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Creditor registration Success";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private String CreditorsListHelperFetch ()
    {

        String data_url = "http://lekenoempire.co.za/test/CreditorsList/get_client_creditor.php";

        try{
            URL url = new URL(data_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();

            String JSON_STRING = null;

            while((JSON_STRING = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(JSON_STRING+"\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            JSONArray jsonArray = new JSONArray(stringBuilder.toString().trim());
            MyDBHandler myDBHandler = new MyDBHandler(context);

            for (int count = 0; count < jsonArray.length(); count++)
            {
                JSONObject JO = jsonArray.getJSONObject(count);
                System.out.println(JO);
                myDBHandler.addCreditRecord(JO.getInt("_id_creditor"), JO.getString("name_creditor"),
                        JO.getString("surname_creditor"), JO.getDouble("amount1_creditor"),
                        JO.getInt("day_credited"), JO.getInt("month_credited"), JO.getInt("year_credited"),
                        JO.getString("sync_creditor"));
            }

            return "Online Creditors Data Fetch Success";

        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkNetworkConnection()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }
}
