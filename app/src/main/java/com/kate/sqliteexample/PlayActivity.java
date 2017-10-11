package com.kate.sqliteexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Kasia on 24.06.2017.
 */

public class PlayActivity extends AppCompatActivity {

    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestionTextView;
    TextView mScoreTextView;
    ProgressBar mProgressBar;
    int mIndex;
    int mQuestion;
    int mScore;

    DBHandler db;
    List<Question> mQuestionList;
    Question[] mQuestionBank;
    int PROGRESS_BAR_INCREMET = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
        } else {
            mScore = 0;
            mIndex = 0;
        }

        db = new DBHandler(this);
        // Inserting Questions/Rows
        /*Log.d("Insert: ", "Inserting ..");
        db.addQuestion(new Question("Valentine\'s day is banned in Saudi Arabia.", "true"));
        db.addQuestion(new Question("A slug\'s blood is green.", "true"));
        db.addQuestion(new Question("Approximately one quarter of human bones are in the feet.", "true"));
        db.addQuestion(new Question("No piece of square dry paper can be folded in half more than 7 times.", "false"));*/

        mQuestionList = db.getAllQuestions();
        /*
        for (Question question : mQuestionList) {
            String log = "Id: " + question.getId() + " ,Name: " + question.getName() + " ,Answer: " + question.getAnswer();
            // Writing questions  to log
            Log.d("Question: : ", log);
        }*/


        mQuestionBank = mQuestionList.toArray(new Question[mQuestionList.size()]); //zmiana listy na tablice
        PROGRESS_BAR_INCREMET = (int) Math.ceil(100.0/mQuestionBank.length);


        mTrueButton = (Button) findViewById(R.id.true_button); //polaczenie z elementem z xmla, konwersja view na button
        mFalseButton = (Button) findViewById(R.id.false_button); //polaczenie z elementem z xmla, konwersja view na button
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mScoreTextView = (TextView) findViewById(R.id.score);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);
        mQuestion = mQuestionBank[mIndex].getId();
        //Log.d("pyt: ", mQuestionBank[mIndex].getName());
        mQuestionTextView.setText(mQuestionBank[mIndex].getName()); //nadpisane tamto pyt



        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "True choosed!", Toast.LENGTH_SHORT).show(); //alerty
                checkAnswer("true");

                CountDownTimer timer;
                timer = new CountDownTimer(2000, 20) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        try{
                            updateQuestion();
                        }catch(Exception e){
                            Log.e("Error", "Error: " + e.toString());
                        }
                    }
                }.start();
            }



        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "False choosed!", Toast.LENGTH_SHORT).show(); //alerty
                checkAnswer("false");

                CountDownTimer timer;
                timer = new CountDownTimer(2000, 20) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        try{
                            updateQuestion();
                        }catch(Exception e){
                            Log.e("Error", "Error: " + e.toString());
                        }
                    }
                }.start();
            }

        });



    }

    private void updateQuestion() {
        mIndex = (mIndex+1)% mQuestionBank.length;
        if (mIndex == 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);//getApplicationContext()
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("You scored " + mScore + " points!");
            alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        }
        mQuestion = mQuestionBank[mIndex].getId();
        mQuestionTextView.setText(mQuestionBank[mIndex].getName());
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMET);
        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);
    }

    private void checkAnswer (String userSelection) {
        String correctAnswer = mQuestionBank[mIndex].isAnswer();
        if (userSelection.equals(correctAnswer)) {
            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
            mScore = mScore + 1;
        } else {
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

    //to by po obrocie ekranu nie cyzscily sie dane
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("ScoreKey", mScore); //pierwszy arg to dow string
        outState.putInt("IndexKey", mIndex);
    }


}
