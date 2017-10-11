package com.kate.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "questionsInfo";

    // Contacts table name
    private static final String TABLE_QUESTIONS = "questions";

    // Questions Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ANSWER = "answer";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_ANSWER + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        // Creating tables again
        onCreate(db);
    }

    // Adding new question
    public void addQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, question.getName()); // Question Name
        values.put(KEY_ANSWER, question.getAnswer()); // Answer

        // Inserting Row
        db.insert(TABLE_QUESTIONS, null, values);
        db.close(); // Closing database connection
    }

    // Getting one question
    public Question getQuestion(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_QUESTIONS, new String[]{KEY_ID,
                        KEY_NAME, KEY_ANSWER}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Question contact = new Question(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return question
        return contact;
    }

    // Getting All Questions
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(Integer.parseInt(cursor.getString(0)));
                question.setName(cursor.getString(1));
                question.setAnswer(cursor.getString(2));
                // Adding to list
                questionList.add(question);
            } while (cursor.moveToNext());
        }

        // return contact list
        return questionList;
    }



    // Getting questions Count
    public int getQuestionsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_QUESTIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating a question
    public int updateQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, question.getName());
        values.put(KEY_ANSWER, question.getAnswer());

        // updating row
        return db.update(TABLE_QUESTIONS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(question.getId())});
    }

    // Deleting a question
    public void deleteQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_QUESTIONS, KEY_ID + " = ?",
                new String[] { String.valueOf(question.getId()) });
        db.close();
    }
}

