package app.ie.triviapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


/**
 * This is the Quiz Class. It Handles presenting the questions, and processing the answer.
 * Menu allows you to navigate from Quiz to Home, Report, and MyAccount.
 * If you go to Home your progress is lost.
 * If You go to Report you only get your score up to that point.
 *
 * Created by Peter on 20/03/2018.
 */

public class Quiz extends Base {


    //define the fields.
    private TextView        queryCountry;
    private RadioButton     firstOption;
    private RadioButton     secondOption;
    private RadioButton     thirdOption;


    private RadioGroup      options;
    private Button          next;
    private ProgressBar     pBar;

    private String          country;
    private String          city1;
    private String          city2;
    private String          city3;
    private String          answer = "answer";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (progress == 0){
            shuffleNations();
        }
        shufflePolis();
        shuffleCorrectAnswer();

        queryCountry = (TextView) findViewById(R.id.country);
        country = cities[getNation(getProgress())][0];//set country to the required value.
        queryCountry.setText(country+"?");//set the TextView to the required country.


        //if difficulty is easy then display 3 cities(non capital) from random countries.
        if(diff.equals("Easy")) {

            city1 = cities[rand()][2];
            city2 = cities[rand()][3];
            city3 = cities[rand()][4];

        }else{
         //if difficulty is hard then display 3 cities from one country.
            city1 = cities[getNation(getProgress())][2];
            city2 = cities[getNation(getProgress())][3];
            city3 = cities[getNation(getProgress())][4];
        }

        //initialise RadioButtons.
        firstOption = (RadioButton) findViewById(R.id.option1);
        secondOption = (RadioButton) findViewById(R.id.option2);
        thirdOption = (RadioButton) findViewById(R.id.option3);

        //set text for options.
        firstOption.setText(city1);
        secondOption.setText(city2);
        thirdOption.setText(city3);


        //replace arbitrary city with the correct answer.
        if(correctAnswer[0] == 1){
                firstOption.setText(cities[getNation(getProgress())][1]);
            }else if (correctAnswer[0] == 2){
                secondOption.setText(cities[getNation(getProgress())][1]);
            }else {
                thirdOption.setText(cities[getNation(getProgress())][1]);
             }


        //set up RadioGroup and set up a Listener.
        options = (RadioGroup) findViewById(R.id.options);
        options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                setAnswer(radioButton.getText().toString());//saves your answer to a String.
                displayChoice();
                next.setVisibility(View.VISIBLE);//this keeps "next" button hidden until selection is made.
            }


        });


        //initialise next button, with OnClickListener.
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {continueQuiz();}
        });


        //initialise ProgressBar.
        pBar = (ProgressBar) findViewById(R.id.progressBar);
        pBar.setMax(noOfQuestions);
        pBar.setProgress(progress);

    }


    /**
     * Method mostly used for testing, but I liked to keep it to show I can Toast.
     */
    private void displayChoice(){

        Toast toast = Toast.makeText(this, answer, Toast.LENGTH_SHORT);
        toast.show();
    }


    /**
     * Method for saving your answer.
     *
     * @param a The String representing your answer.
     */
    private void setAnswer(String a){

        answer = a;

    }

    /**
     * Method to randomise the selection of cities.
     *
     * @return Int city index.
     */
    private int rand(){
        Random rand = new Random();

        int randomNum = rand.nextInt((16));

        return randomNum;

    }


    /**
     * Method that checks your answer and if correct increments the score.
     */
    private void checkAnswer(){

        if(answer.equals(cities[getNation(getProgress())][1])){
            score++;
        }
    }


    /**
     * Method that defines the action of the next button.
     */
    private void continueQuiz(){

        if(progress < noOfQuestions-1) {

            checkAnswer();
            progress++;//increment progress; needed to track progress.
            Intent intent1 = new Intent(Quiz.this, Quiz.class);
            startActivity(intent1);


        }else {
            checkAnswer();
            progress++;//increment progress; needed to track progress.
            Intent intent2 = new Intent(Quiz.this, Report.class);
            startActivity(intent2);
        }

    }


}
