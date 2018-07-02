package app.ie.triviapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import java.security.SecureRandom;

import app.ie.triviapp.database.AndroidDatabaseManager;
import app.ie.triviapp.database.MyAccount;


/**
 * Created by Peter on 20/03/2018.
 * This Base class holds all the country and city data in a 2d array.
 * It also randomises int[] Arrays to facilitate random access of the data.
 */

public class Base extends AppCompatActivity {



    //these fields record your progress through the quiz.
    public static int             progress = 0;
    public static int             score = 0;
    public static int             noOfQuestions = 0;
    public static String          diff;

    public static int[]           nations = new int[16];
    public static int[]           polis = new int [4];
    public static int[]           correctAnswer = new int[3];
    public static String          userName;


    //the quiz data held in a 2d array. Countries are in column zero. Capitals in column 1.
    public static String[][]      cities = {{"USA", "Washington", "New York", "Chicago", "Dallas"},
                                    {"Canada", "Ottawa", "Vancouver", "Quebec City", "Montreal City"},
                                    {"Mexico", "Mexico City", "Ecatepec", "Guadalajara", "Puebla"},
                                    {"Cuba", "Havana", "Santiago de Cuba", "Camaguey", "Holguin"},
                                    {"Austria", "Vienna", "Graz", "Linz", "Salzburg"},
                                    {"Belgium", "Brussels", "Antwerp", "Ghent", "Charleroi"},
                                    {"Denmark", "Copenhagen", "Aarhus", "Odense", "Aalborg"},
                                    {"Sweden", "Stockholm", "Gothenburg", "Malmö", "Uppsala"},
                                    {"China", "Beijing", "Hong Kong", "Shanghai", "Guangzhou"},
                                    {"Vietnam", "Hanoi", "Hai Phong", "Danang", "Can Tho"},
                                    {"Japan", "Tokyo", "Yokohama", "Osaka", "Kyoto"},
                                    {"India", "New Delhi", "Mumbai", "Kolkata", "Chennai"},
                                    {"Ghana", "Accra", "Kumasi", "Ashiaman", "Sunyani"},
                                    {"Ivory Coast", "Yamoussoukro", "Abidjan", "Bouake", "Daloa"},
                                    {"Egypt", "Cairo", "Alexandria", "Giza", "Suez"},
                                    {"Nigeria", "Abuja", "Benin City", "Jos", "Ibadan"}};


    /**
     * Method used to create an array with 16 randomised un-repeating entries 0-16.
     */
    public void shuffleNations(){

        nations = createUniqueRandomNumbers(1, 16);

    }

    /**
     * Method used to create an array with 5 randomised un-repeating entries 0-4.
     */
    public void shufflePolis(){

        polis = createUniqueRandomNumbers(1, 4);

    }


    /**
     * Method used to create an array with 4 randomised un-repeating entries 0-3.
     */
    public void shuffleCorrectAnswer(){

        correctAnswer = createUniqueRandomNumbers(1, 3);

    }

    /**
     * Method that returns the value of nations[i]
     *
     * @param i Index of selected Nation.
     * @return Returns the Nation.
     */
    public int getNation(int i){

        return nations[i];
    }


    /**
     * Method to produce arrays of randomised non-repeating integers of specified size.
     *
     * @param from Starting number.
     * @param to Ending number.
     * @return Returns a randomised int array.
     */
    public int[] createUniqueRandomNumbers(int from, int to){
        int n = to - from + 1;

        int a[] = new int[n];
        for(int i = 0; i < n; i++){
            a[i] = i;
        }

        int[] result = new int[n];

        int x = n;
        SecureRandom rd = new SecureRandom();
        for(int i = 0; i < n; i++){
            int k = rd.nextInt(x);

            result[i] = a[k];

            a[k] = a[x-1];

            x--;
        }
        return result;

    }

    /**
     * Method that returns your progress. Not entirely needed. An artifact of an earlier version.
     *
     * @return Int representing your progress through the quiz.
     */
    public int getProgress(){

        return progress;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        //getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu){
        super.onPrepareOptionsMenu(menu);
        MenuItem report = menu.findItem(R.id.menuReport);
        MenuItem home = menu.findItem(R.id.menuHome);
        MenuItem manageMyAccount = menu.findItem(R.id.menuMyAccount);
        MenuItem logOut = menu.findItem(R.id.menuLogOut);

        //if you haven't done the quiz, you don't need a report.
         if(progress == 0)
               report.setVisible(false);
            else
            report.setVisible(true);

        //hide menu item report when in report.
        if(this instanceof Report){
            report.setVisible(false);
            manageMyAccount.setVisible(true);
            logOut.setVisible(true);
        }

        //hide menu item home when in home.=
        if(this instanceof  Home){
            home.setVisible(false);
            manageMyAccount.setVisible(true);
            logOut.setVisible(true);
        }

        return true;
    }


    /**
     * Method for testing only! This navigates to Activity for Database viewing.
     *
     * @param item The menu item in question.
     */
    public void settings(MenuItem item){
        //Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show();
        Intent dbmanager = new Intent(this,AndroidDatabaseManager.class);
        startActivity(dbmanager);
    }

    /**
     * Method for clearing SharedPreferences.
     *
     * @param item The menu item in question.
     */
    public void logOut(MenuItem item){
        SharedPreferences sharedPref = getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear(); //clear all stored data
        editor.commit();
        startActivity (new Intent(this, Login.class));
    }


    /**
     * Method handling menu navigation.
     *
     * @param item The menu item in question.
     */
    public void manageAccount(MenuItem item){startActivity (new Intent(this, MyAccount.class));}


    /**
     * Method handling menu navigation.
     *
     * @param item The menu item in question.
     */
    public void report(MenuItem item)
    {
        startActivity (new Intent(this, Report.class));
    }


    /**
     * Method handling menu navigation.
     *
     * @param item The menu item in question.
     */
    public void home(MenuItem item)
    {
        startActivity (new Intent(this, Home.class));
    }

}
