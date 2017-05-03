package br.edu.ufcspa.snorlax_angelo.client;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import br.edu.ufcspa.snorlax_angelo.QuestionActivity;
import br.edu.ufcspa.snorlax_angelo.model.Answer;

/**
 * Created by icaromsc on 03/05/2017.
 */

public class AnswerClient extends HttpClient{


    public AnswerClient(Context context) {
        super(context);
    }


    public void postJson(JSONObject jsonBody){


        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL + "insert_answer.php", jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("result").equals(0) || response.getString("result").equals(-1)){
                        Log.d("json", "JSON Post erro");
                    }else {
                        Log.d("json", "response json: recebendo json:"+response.toString());
                        Log.d("json", "response json: enviada resposta");
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
