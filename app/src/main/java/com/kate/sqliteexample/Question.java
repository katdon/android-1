package com.kate.sqliteexample;

/**
 * Created by Kasia on 24.06.2017.
 */

public class Question {
    private int id;
    private String name;
    private String answer;

    public Question()
    {
    }

    public Question(int id,String name,String answer)
    {
        this.id=id;
        this.name=name;
        this.answer=answer;
    }

    public Question(String name,String answer)
    {
        this.name=name;
        this.answer=answer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getId() {

        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public String getName() {
        return name;
    }

    public String isAnswer() {
        return answer;
    }
}
