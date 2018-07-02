package app.ie.triviapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;


/**
 * This Class defines the operation of the Splash Screen.
 *
 * Created by Peter on 29/04/2018.
 */

public class Splash extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        SplashLauncher splash = new SplashLauncher();
        splash.start();
    }


    private class SplashLauncher extends Thread{


        /**
         * Method that runs the Splash Screen for a given time, and then navigates to login.
         */
        public void run(){

            try{
                sleep(3000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            SharedPreferences sharedPref = getSharedPreferences("currentUser", Context.MODE_PRIVATE);
            String ourUser = sharedPref.getString("user", "");

            if(ourUser.length() == 0)
            {
                // call Login Activity
                Intent intent = new Intent(Splash.this, Login.class);
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(Splash.this, Home.class);
                startActivity(intent);
            }


            Splash.this.finish();
        }

    }

    }

