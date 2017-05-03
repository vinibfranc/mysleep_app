package br.edu.ufcspa.snorlax_angelo.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by icaromsc on 03/05/2017.
 */

public class Answer {
    private int idUser;
    private int idQuestion;
    private String answer;


    public Answer(int idUser, int idQuestion, String answer) {
        this.idUser = idUser;
        this.idQuestion = idQuestion;
        this.answer = answer;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    @Override
    public String toString() {
        return "Answer{" +
                "answer='" + answer + '\'' +
                ", idQuestion=" + idQuestion +
                ", idUser=" + idUser +
                '}';
    }



    public JSONObject toJson(){
        JSONObject json= new JSONObject();
        try {
            json.put("id_user",idUser);
            json.put("id_question",idQuestion);
            json.put("answer",answer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
