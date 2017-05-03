package br.edu.ufcspa.snorlax_angelo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ufcspa.snorlax_angelo.client.AnswerClient;
import br.edu.ufcspa.snorlax_angelo.managers.SharedPreferenceManager;
import br.edu.ufcspa.snorlax_angelo.model.Answer;
import br.edu.ufcspa.snorlax_angelo.model.Question;
import br.edu.ufcspa.snorlax_angelo.model.UserModel;
import ufcspa.edu.br.snorlax_angelo.R;

public class QuestionActivity extends AppCompatActivity {

    ListView lvOptions;
    TextView txtQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        txtQuestion = (TextView) findViewById(R.id.txtViewQuestion);
        lvOptions = (ListView) findViewById(R.id.listViewOptions);


        try {
            final Question question = (Question) getIntent().getSerializableExtra("question");

            txtQuestion.setText(question.getDescription());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.item_list_options, R.id.txtItemOptions, question.getOptions());
            lvOptions.setAdapter(adapter);

            lvOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getBaseContext(), question.getOptions()[i], Toast.LENGTH_SHORT).show();
                    Answer answer = new Answer(question.getIdUser(), question.getIdQuestion(), question.getOptions()[i]);
                    sendAnswer(answer);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        public void sendAnswer(Answer answer){
            AnswerClient client = new AnswerClient(getBaseContext());
            client.postJson(answer.toJson());


            UserModel userModel = SharedPreferenceManager.getSharedInstance().getUserModelFromPreferences();
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra(UserModel.class.getSimpleName(), userModel);
            startActivity(intent);
            finish();
        }














}
