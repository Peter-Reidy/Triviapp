package app.ie.triviapp.database;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import app.ie.triviapp.Base;
import app.ie.triviapp.R;

/**
 * This Class is intended to allow users to change ad delete their login data.
 * In the current version, only the delete function has been implemented.
 *
 * Created by Peter on 04/05/2018.
 */

public class MyAccount extends Base {


    private Button updateButton; //intended to take the user to a new activity to change info... not implemented
    private Button deleteButton; //this button deletes the users data from User Table

    private DBDesigner dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBDesigner(this);

        updateButton = (Button) findViewById(R.id.btnUpdate);

        deleteButton = (Button) findViewById(R.id.btnDeleteMe);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbHelper.deleteUser(userName);
                SharedPreferences sharedPref = getSharedPreferences("currentUser", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear(); //clear all stored data
                editor.commit();
                returnToLogin();

            }
        });

    }

    /**
     * Method to return user to login screen after deleting their account.
     */
    private void returnToLogin(){
        startActivity (new Intent(this, MyAccount.class));
    }

}
