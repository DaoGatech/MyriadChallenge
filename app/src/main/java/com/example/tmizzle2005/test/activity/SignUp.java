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
import com.example.tmizzle2005.test.POJO.SignUpMessage;
import com.example.tmizzle2005.test.R;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SignUp extends Activity {

    private EditText email;
    private SharedPreferences prefs;
    private EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        prefs = getSharedPreferences("savedData", Context.MODE_PRIVATE);
        if(prefs.contains("storedEmail")) {
            Intent next = new Intent(SignUp.this,KingDomList.class);
            startActivity(next);
        } else {
            name = (EditText) findViewById(R.id.name);
            email = (EditText) findViewById(R.id.email);
            Button submit = (Button) findViewById(R.id.submit);
            submit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                        signUp();
                    } else {
                        Toast.makeText(getApplicationContext(),"Your Email Address is invalid. Please try again.",Toast.LENGTH_LONG).show();
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

    private void signUp() {
        //specify endpoint and build the restadapter instance
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://challenge2015.myriadapps.com")
                .build();

        API api=restAdapter.create(API.class);
        api.signUp(email.getText().toString(), new Callback<SignUpMessage>() {
            @Override
            public void success(SignUpMessage result, Response response) {
                SharedPreferences.Editor editor = getSharedPreferences("savedData", Context.MODE_PRIVATE).edit();
                editor.putString("storedEmail",email.getText().toString()).apply();
                editor.putString("storedName",name.getText().toString()).apply();
                Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_LONG).show();
                Intent a = new Intent(SignUp.this, KingDomList.class);
                startActivity(a);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(),"Opps. Error Connecting to the Server",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    public int getDifferKey() {
        return 111693;
    }
}
