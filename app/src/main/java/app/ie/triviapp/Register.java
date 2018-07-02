package app.ie.triviapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.ie.triviapp.database.DBDesigner;
import app.ie.triviapp.database.User;
import app.ie.triviapp.database.Validate;

/**
 * This Class handles the registration of new users.
 *
 * Created by Peter on 01/05/2018.
 */

public class Register extends AppCompatActivity{


    //class fields
    private EditText nameField;
    private EditText emailField;
    private EditText passField;
    private EditText confirmPassField;
    private TextView loginField;
    private Button registerBtn;


    private Validate validate;
    private DBDesigner dbHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialise fields
        validate = new Validate(this);
        dbHelper = new DBDesigner(this);
        user = new User();


        nameField = (EditText) findViewById(R.id.edtName);
        emailField = (EditText) findViewById(R.id.regEmail);
        passField = (EditText) findViewById(R.id.regPassword);
        confirmPassField = (EditText) findViewById(R.id.regConfirm);


        //set onClickListener
        loginField = (TextView) findViewById(R.id.txtLogin);
            loginField.setOnClickListener(new View.OnClickListener(){
                                            @Override
                                            public void onClick(View view){
                                                onLoginPressed();
                                            }
                                        });

        //set onClickListener
        registerBtn = (Button) findViewById(R.id.btnRegister);
            registerBtn.setOnClickListener(new View.OnClickListener(){
                                            @Override
                                            public void onClick(View view){
                                                onRegisterPressed();
                                            }
            });




    }


    /**
     * Method that takes user back to login.
     */
    private void onLoginPressed(){

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }


    /**
     * Method that handles functions needed by Register button.
     */
    private void onRegisterPressed(){

        if(!validate.nameEntered(nameField.getText().toString())){
            nameError();
        }else if(!validate.validEmail(emailField.getText().toString())){
            emailError();
        }else if(!validate.passwordMatches(passField.getText().toString(), confirmPassField.getText().toString())){
            passError();
        }else if(dbHelper.checkUser(emailField.getText().toString())){
            existingEmailError();
        }else{
            user.setName(nameField.getText().toString().trim());
            user.setEmail(emailField.getText().toString().trim());
            user.setPassword(passField.getText().toString().trim());

            dbHelper.addUser(user);
            regSuccessful();

        }

    }


    /**
     * Method to prompt correct password.
     */
    public void passError(){
        Toast toast = Toast.makeText(this, "Your Passwords do not match!", Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Method to prompt name entry.
     */
    public void nameError(){
        Toast toast = Toast.makeText(this, "You must enter a name!", Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Method to prompt a valid email entry.
     */
    public void emailError(){
        Toast toast = Toast.makeText(this, "You must enter a valid email!", Toast.LENGTH_LONG);
        toast.show();
    }


    /**
     * Method to navigate back to login after successful registration.
     */
    private void regSuccessful(){

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }

    /**
     * Method to prompt user to enter a previously unregistered email.
     */
    private void existingEmailError(){
        Toast toast = Toast.makeText(this, "This E-mail is already Registered!", Toast.LENGTH_LONG);
        toast.show();
    }





}
