package com.example.admin.simpleloginapp;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper mydb;//create instance of DatabaseHelper class
    EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //setting up the action bar with default app logo
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
mydb= new DatabaseHelper(this);
       username =(EditText)findViewById(R.id.edittext_username);
        password =(EditText)findViewById(R.id.edittext_password);



    }
    // login method used to acquire username and password from SQlite database
    public void login(View view){
        Intent intent = new Intent(this,Welcome.class); //start welcome user activity is login is successful
        String user = username.getText().toString();
        String pass = password.getText().toString();
        if (user.length() > 0 && pass.length() > 0) {
            try {

                if (mydb.Login(user,pass)) {
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this,
                            "Invalid username or password",
                            Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Some problem occurred",
                        Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(MainActivity.this,
                    "Username or Password is empty", Toast.LENGTH_LONG).show();
        }
    }

    public void addData(){
        boolean info = mydb.insertData(username.getText().toString(), password.getText().toString());
        if(info = true)
            Toast.makeText(MainActivity.this, "Sign up successful", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this,"Sign up unsuccessful",Toast.LENGTH_LONG).show();
    }
    public void viewData(){

                Cursor res= mydb.viewAllData();
                if(res.getCount()==0){
                    showMessage("Error","Nothing found");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while(res.moveToNext()){
                    stringBuffer.append("Id: "+res.getString(0)+ "\n");
                    stringBuffer.append("Username: "+res.getString(1)+ "\n");



                }
                showMessage("MEMBERS", stringBuffer.toString());
            }


    public void showMessage(String title, String meso){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(meso);
        builder.show();
    }

public void changeFragments(View view) {
    Fragment fr;
     if (view == findViewById(R.id.button_signup)) {
        fr = new Frament_Signup();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main, fr);
        fragmentTransaction.commit();
         fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    }
    if (view == findViewById(R.id.button_about)) {
        fr = new Fragment_Instructions();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main, fr);
        fragmentTransaction.commit();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
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

      if(id == R.id.action_submit){
          addData();
      }
        if(id== R.id.menu_members){
            viewData();
        }

        return super.onOptionsItemSelected(item);
    }
}
