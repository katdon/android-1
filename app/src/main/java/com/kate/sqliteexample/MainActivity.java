package com.kate.sqliteexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button mPlayButton;
    Button mAddButton;
    DBHandler db;
    List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mPlayButton = (Button) findViewById(R.id.play_button); //polaczenie z elementem z xmla, konwersja view na button
        mAddButton = (Button) findViewById(R.id.add_button);



        db = new DBHandler(this);
        questions = db.getAllQuestions();

        /*
        for (Question question : questions) {
            String log = "Id: " + question.getId() + " ,Name: " + question.getName() + " ,Answer: " + question.getAnswer();
            // Writing questions  to log
            Log.d("Questions: : ", log);
        }*/


    }
    public void letsPlay(View v) {
        questions = db.getAllQuestions();
        if(questions.size()>0) {
            Intent i = new Intent(this, PlayActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), "Database is empty! Add some questions!", Toast.LENGTH_SHORT).show(); //alerty
        }

    }

    public void letsAdd(View v) {
        Intent i = new Intent(this, AddActivity.class);
        startActivity(i);

    }
    public void letsClear(View v) {
        questions = db.getAllQuestions();
        if(questions.size()>0) {
            for (Question question : questions) {
                db.deleteQuestion(question);
            }
            Toast.makeText(getApplicationContext(), "Database is empty now!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Database is empty! Add some questions!", Toast.LENGTH_SHORT).show(); //alerty
        }

    }
}
