package com.example.abc.sadna;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABC on 15-07-2016.
 */
public class AddNewPatient extends Activity {
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText inputName;
    EditText inputGender;
    EditText inputEmail;
EditText inputAddress;
    // url to create new product
    private static String url_create_product = "http://vishalthakkar.netai.net/androidphp/add_new_patient.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_patient);

        // Edit Text
        inputAddress=(EditText)findViewById(R.id.inputAddress1);
        inputName = (EditText) findViewById(R.id.inputName);
        inputGender = (EditText) findViewById(R.id.inputGender);
        inputEmail = (EditText) findViewById(R.id.inputEmail);

        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.btnCreate);

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                new CreateNewPatient().execute();
            }
        });
    }

    /**
     * Background Async Task to Create new Patient
     * */
    class CreateNewPatient extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddNewPatient.this);
            pDialog.setMessage("Uploading to server...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String name = inputName.getText().toString();
            String gender = inputGender.getText().toString();
            String email = inputEmail.getText().toString();
            String Address= inputAddress.getText().toString();
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("patient_name", name));
            params.add(new BasicNameValuePair("patient_gender", gender));
            params.add(new BasicNameValuePair("patient_email", email));
            params.add(new BasicNameValuePair("patient_address",Address));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag 
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), patient_info.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
}