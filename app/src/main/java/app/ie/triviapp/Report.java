package app.ie.triviapp;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * This class provides you with the results of your quiz using TextFields.
 *
 *  * Created by Peter on 20/03/2018.
 */

public class Report extends Base {


    //define fields.
    private TextView    lDiff;
    private TextView    lNoQ;
    private TextView    lScore;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialise fields.
        lDiff = (TextView)  findViewById(R.id.textView4);
        lNoQ = (TextView)   findViewById(R.id.textView5);
        lScore = (TextView) findViewById(R.id.textView6);


        //change values to reflect your quiz performance.
        lDiff.setText(diff);
        lNoQ.setText(String.valueOf(noOfQuestions));
        lScore.setText(String.valueOf(score));
    }

}