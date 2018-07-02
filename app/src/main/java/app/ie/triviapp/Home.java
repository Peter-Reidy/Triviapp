package app.ie.triviapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * This activity launches after login is confirmed.
 * It allows you to select the number of questions to be answered, and the difficulty
 *
 * Created by Peter on 20/03/2018.
 */


public class Home extends Base {

    private Button          startButton; //to advance to quiz.
    private NumberPicker    numberQuestions; //to select the number of questions.
    private RadioGroup      difficulty; //to select difficulty.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //to ensure that each quiz begins from scratch we set the Base variables to zero.
        progress = 0;
        noOfQuestions = 0;
        score = 0;

        //set up NumberPicker
        numberQuestions  = (NumberPicker) findViewById(R.id.QPicker);
        numberQuestions.setMinValue(1);
        numberQuestions.setMaxValue(16);

        //set up radioGroup and set ChangeListener on it.
        difficulty = (RadioGroup) findViewById(R.id.difficulty);
        difficulty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                diff = radioButton.getText().toString();
            }


        });

        //set up startButton, with OnClickListener.
        startButton = (Button) findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               if(difficulty.getCheckedRadioButtonId() != -1){
                                                   startQuiz();
                                               }else{
                                                   unchecked();
                                               }
                                           }
                                       });




    }


    /**
     * Method to advance to quiz.
     */
    private void startQuiz() {

            noOfQuestions = numberQuestions.getValue();

            Intent intent = new Intent(Home.this, Quiz.class);
            startActivity(intent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to prompt the user to select difficulty
     */
    private void unchecked(){
        Toast toast = Toast.makeText(this, "Please Select Your Difficulty.", Toast.LENGTH_LONG);
        toast.show();
    }



}
