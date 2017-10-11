package com.kate.sqliteexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Kasia on 24.06.2017.
 */

public class AddActivity extends AppCompatActivity {
    DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    public void addNewQuestion(View v) {
        db = new DBHandler(this);
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        CheckBox checkBox = (CheckBox) findViewById(R.id.check_box);
        if (checkBox.isChecked()) {
            db.addQuestion(new Question(nameEditText.getText().toString(), "true"));
            Toast.makeText(getApplicationContext(), "True question add!", Toast.LENGTH_SHORT).show(); //alerty
        } else {
            db.addQuestion(new Question(nameEditText.getText().toString(), "false"));
            Toast.makeText(getApplicationContext(), "Flase question add!", Toast.LENGTH_SHORT).show(); //alerty
        }
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);


    }
}
