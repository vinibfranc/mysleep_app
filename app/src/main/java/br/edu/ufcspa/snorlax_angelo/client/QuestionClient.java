package br.edu.ufcspa.snorlax_angelo.client;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import br.edu.ufcspa.snorlax_angelo.SplashActivity;
import br.edu.ufcspa.snorlax_angelo.database.DataBaseAdapter;
import br.edu.ufcspa.snorlax_angelo.model.Question;

/**
 * Created by icaromsc on 28/04/2017.
 *
 * Classe responsavel por comunicar com webService e baixar uma pergunta
 */

public class QuestionClient extends HttpClient {

   public Question question;
   SplashActivity act;

    public QuestionClient(Context context) {
        super(context);
    }


    public void postJson(final JSONObject jsonBody){
        question=null;

       final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL + "get_questions.php", jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("json question", "response json: recebendo json:"+response.toString());
                    if(response.getInt("result")==1){
                        Log.e("json", "response json: ok");
                        //recover question
                        int idQuestion=Integer.parseInt(response.getString("id_question"));
                        String description=response.getString("description");
                        String[] options=decodeString(response.getString("options"));
                        question=new Question(idQuestion,jsonBody.getInt("id_user"),description,options);
                        act.mountQuestion(question,context);
                    }else {
                        Log.e("json", "JSON Post erro");
                        act.mountQuestion(question,context);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("json","error:"+volleyError.toString());
                volleyError.printStackTrace();

            }
        });
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);


    }
    public String[] decodeString(String a){
        return a.split(";");
    }





}
