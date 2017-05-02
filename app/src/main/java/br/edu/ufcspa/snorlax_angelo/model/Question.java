package br.edu.ufcspa.snorlax_angelo.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by icaromsc on 28/04/2017.
 */

public class Question implements Serializable{
    private int idQuestion;
    private int idUser;
    private String description;
    private String[] options;

    @Override
    public String toString() {
        return "Question{" +
                "idQuestion=" + idQuestion +
                ", idUser=" + idUser +
                ", description='" + description + '\'' +
                ", options=" + Arrays.toString(options) +
                '}';
    }

    public Question(int idQuestion, int idUser, String description, String[] options) {
        this.idQuestion = idQuestion;
        this.idUser = idUser;
        this.description = description;
        this.options = options;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }





}
