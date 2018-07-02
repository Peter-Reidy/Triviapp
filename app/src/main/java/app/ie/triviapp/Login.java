package app.ie.triviapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.ie.triviapp.database.DBDesigner;
import app.ie.triviapp.database.Validate;

import static app.ie.triviapp.Base.userName;

/**
 * This Class handles Login of user.
 *
 * Created by Peter on 01/05/2018.
 */

public class Login extends AppCompatActivity{


    // class fields
    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginBtn;
    private TextView registerTxt;

    private Validate validate;
    private DBDesigner dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        validate = new Validate(this);
        dbHelper = new DBDesigner(this);


        loginEmail = (EditText) findViewById(R.id.edtEmail);
        loginPassword = (EditText) findViewById(R.id.edtPassword);



        loginBtn = (Button) findViewById(R.id.btnLogin);
            loginBtn.setOnClickListener(new View.OnClickListener(){
                                            @Override
                                            public void onClick(View view){
                                                 onLoginPressed();
                                             }
                                        });

        registerTxt = (TextView) findViewById(R.id.txtRegister);
            registerTxt.setOnClickListener(new View.OnClickListener(){
                                                @Override
                                                public void onClick(View view){
                                                   onRegisterPressed();
                                                }
                                             });



    }


    /**
     * Method that handles the Login Button.
     */
    private void onLoginPressed(){

        SharedPreferences sharedPref = getSharedPreferences("currentUser", Context.MODE_PRIVATE);


        if(!validate.validEmail(loginEmail.getText().toString())){
            emailError();
        }else if(!validate.passwordEntered(loginPassword.getText().toString())){
            passwordError();
        }else if(dbHelper.checkUser(loginEmail.getText().toString().trim(), loginPassword.getText().toString().trim())){
            userName = loginEmail.getText().toString().trim();

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("user", loginEmail.getText().toString().trim());
            editor.apply();

            loginSuccessful();
        }else{
            loginFail();
        }

    }

    /**
     * Method that handles navigation to the registration activity.
     */
    private void onRegisterPressed(){

        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }


    /**
     * Method that opens Home Class on successful login.
     */
    private void loginSuccessful(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);

    }

    /**
     * Method to prompt user to create a user account.
     */
    private void loginFail(){
        Toast toast = Toast.makeText(this, "Invalid User! Please Register.", Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Method to prompt user to enter a valid email
     */
    private void emailError(){
        Toast toast = Toast.makeText(this, "You must enter a valid email!", Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Method to prompt user to enter a password.
     */
    private void passwordError(){
        Toast toast = Toast.makeText(this, "You must enter a password!", Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Method used only in testing.
     */
    private void testLogin(){
        Toast toast = Toast.makeText(this, userName, Toast.LENGTH_LONG);
        toast.show();
    }


}
