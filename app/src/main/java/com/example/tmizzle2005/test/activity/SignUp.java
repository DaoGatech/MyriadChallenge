package com.example.tmizzle2005.test.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.tmizzle2005.test.Inteface.API;
import com.example.tmizzle2005.test.Model.SignUpMessage;
import com.example.tmizzle2005.test.R;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * This class signs the user in
 */
public class SignUp extends Activity {

    //declare the EditText of email and name
    //declare the SharedPreferences variable
    private EditText email;
    private SharedPreferences prefs;
    private EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize the view of the activity
        setContentView(R.layout.sign_up);
        //initialize the SharedPreferences variable
        prefs = getSharedPreferences("savedData", Context.MODE_PRIVATE);
        //if the email is already stored in the system, go straight to the Kingdom List
        //otherwise, show the login page
        if(prefs.contains("storedEmail")) {
            Intent next = new Intent(SignUp.this,KingDomList.class);
            startActivity(next);
        } else {
            //initialize the edittext
            name = (EditText) findViewById(R.id.name);
            email = (EditText) findViewById(R.id.email);
            Button submit = (Button) findViewById(R.id.submit);
            //if the submit button is pressed
            submit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //check if the email is valid. If it is, log the user in, if not, shows the
                    //message
                    if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                        signUp();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Your Email Address is invalid. Please try again.",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This function calls the API and use retrofit to POST the email to server
     */
    private void signUp() {
        //specify endpoint and build the restadapter instance
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://challenge2015.myriadapps.com")
                .build();

        API api=restAdapter.create(API.class);
        //call the method signUp from the Interface
        api.signUp(email.getText().toString(), new Callback<SignUpMessage>() {
            @Override
            public void success(SignUpMessage result, Response response) {
                //declare the editor of the SharedPreferences
                SharedPreferences.Editor editor = getSharedPreferences("savedData",
                        Context.MODE_PRIVATE).edit();
                //store the email and the name of the user locally
                editor.putString("storedEmail",email.getText().toString()).apply();
                editor.putString("storedName",name.getText().toString()).apply();
                //return the message from the server
                Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_LONG).show();
                //go to the Kingdom list
                Intent a = new Intent(SignUp.this, KingDomList.class);
                startActivity(a);
            }

            @Override
            public void failure(RetrofitError error) {
                //if fails to talk to the server, tells the user
                Toast.makeText(getApplicationContext(),"Opps. Error Connecting to the Server",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * This function is to return a 6-digit number code servers as a signature for this app
     * The secret code appears in all the SharedPreferences key to differentiate the variables saved
     * by this app from the variables saved by another app
     * 111693 is my birthday in the format month/day/year
     * @return int the 6-digit number to differentiate this app to another app.
     */
    public int getDifferKey() {
        return 111693;
    }
}
