package br.edu.ufcspa.snorlax_angelo;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import br.edu.ufcspa.snorlax_angelo.client.QuestionClient;
import br.edu.ufcspa.snorlax_angelo.client.RecordingClient;
import br.edu.ufcspa.snorlax_angelo.managers.SharedPreferenceManager;
import br.edu.ufcspa.snorlax_angelo.model.Question;
import br.edu.ufcspa.snorlax_angelo.model.SendRecording;
import br.edu.ufcspa.snorlax_angelo.model.UserModel;

/**
 * Created by Icarus on 14/01/2017.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* TESTE DE COMUNICACAO SERVIDOR
        SendRecording sendRecording = new SendRecording(5,15,897532,321654,37);
        RecordingClient client = new RecordingClient(this,sendRecording);
        client.send();*/

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "br.edu.ufcspa.snorlax_angelo",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
      /*  UserModel userModel = SharedPreferenceManager.getSharedInstance().getUserModelFromPreferences();
        if(userModel!=null) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra(UserModel.class.getSimpleName(), userModel);
            startActivity(intent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }
        }else{
            Intent intent = new Intent(this, TesteLogin.class);
            //Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }*/

        QuestionClient q = new QuestionClient(getBaseContext());
        try {
            JSONObject jsonBody = new JSONObject().put("id_user", 14);
            Log.d("json splash", jsonBody.toString());
            q.postJson(jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }





        /*Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();*/
    }


    public static void mountQuestion(Question q,Context c){
        Log.d("json splash:", q.toString());
        if(q!=null){
            Intent intent = new Intent(c,QuestionActivity.class);
            intent.putExtra("question", (Serializable) q);
            c.startActivity(intent);
        }

    }






}