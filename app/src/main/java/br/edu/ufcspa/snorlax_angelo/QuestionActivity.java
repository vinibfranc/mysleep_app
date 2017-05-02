package br.edu.ufcspa.snorlax_angelo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.edu.ufcspa.snorlax_angelo.model.Question;
import ufcspa.edu.br.snorlax_angelo.R;

public class QuestionActivity extends AppCompatActivity {

    ListView lvOptions;
    TextView txtQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        txtQuestion= (TextView) findViewById(R.id.txtViewQuestion);
        lvOptions = (ListView) findViewById(R.id.listViewOptions);





        try {
            Question question = (Question) getIntent().getSerializableExtra("question");

            txtQuestion.setText(question.getDescription());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.item_list_options, R.id.txtItemOptions, question.getOptions());
            lvOptions.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }





    }



}
