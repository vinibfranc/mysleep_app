package br.edu.ufcspa.snorlax_angelo;


import android.app.Activity;
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

import br.edu.ufcspa.snorlax_angelo.client.QuestionClient;
import br.edu.ufcspa.snorlax_angelo.database.DataBaseAdapter;
import br.edu.ufcspa.snorlax_angelo.managers.SharedPreferenceManager;
import br.edu.ufcspa.snorlax_angelo.model.Question;
import br.edu.ufcspa.snorlax_angelo.model.UserModel;

/**
 * Created by Icarus on 14/01/2017.
 */
public class SplashActivity extends AppCompatActivity {


    public UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getHash();
        //Log.d("space","available:"+Utilities.getAvailableSpaceInMB()+"MB");
        userModel = SharedPreferenceManager.getSharedInstance().getUserModelFromPreferences();

        if(userModel!=null) {
            if(Utilities.isOnline(getBaseContext()))
                downloadQuestion();
            else
                goToHomeActivity(userModel);
        }else{
            Intent intent = new Intent(this, LoginActivityApp.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * @go Question activity or HomeActivity
     */
    public static void mountQuestion(Question q,Context c){
        if(q!=null){
            Log.d("json splash:", q.toString());
            Intent intent = new Intent(c,QuestionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("question", (Serializable) q);
            c.startActivity(intent);
            /*((Activity) c).finish();*/
        }else {
            Intent intent = new Intent(c, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            UserModel userModel = SharedPreferenceManager.getSharedInstance().getUserModelFromPreferences();
            intent.putExtra(UserModel.class.getSimpleName(), userModel);
            c.startActivity(intent);
            /*((Activity) c).finish();*/
        }

    }



    private void downloadQuestion(){
        QuestionClient q = new QuestionClient(getBaseContext());
        try {
            DataBaseAdapter data = DataBaseAdapter.getInstance(getBaseContext());
            int idUser= data.getUserId();
            JSONObject jsonBody = new JSONObject().put("id_user",idUser);
            Log.d("json splash", jsonBody.toString());
            q.postJson(jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    private void goToHomeActivity(UserModel userModel) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(UserModel.class.getSimpleName(), userModel);
        startActivity(intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        finish();
    }




    public void getHash(){
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
    }





}