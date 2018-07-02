package app.ie.triviapp.database;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * This Class is used to provide very basic validation of user data.
 *
 * Created by Peter on 03/05/2018.
 */

public class Validate {

    private Context context;


    //constructor
    public Validate(Context context) {
        this.context = context;
    }


    /**
     * Method to check that the user has entered a name.
     *
     * @param name A String that holds the user name.
     * @return Returns true or false.
     */
    public boolean nameEntered(String name) {

        return (!name.isEmpty());
    }


    /**
     * Method to check that a password has been entered.
     *
     * @param password String that holds the password.
     * @return Returns true or false.
     */
    public boolean passwordEntered(String password){
        return (!password.isEmpty());
    }


    /**
     * Method to check for valid email using the standard android.util.Patterns
     *
     * @param email String represeting the email.
     * @return Returns true or false.
     */
    public boolean validEmail(String email){

        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());

    }

    /**
     * Method to check that the password and confirmPassword match.
     *
     * @param password String password as first entered
     * @param confirm String confirmed password.
     * @return returns true or false.
     */
    public boolean passwordMatches(String password, String confirm) {

        if (!password.contentEquals(confirm)) {
            return false;
        } else {
            return true;
        }

    }



    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
