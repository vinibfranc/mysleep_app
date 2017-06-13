package br.edu.ufcspa.snorlax_angelo;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.GraphResponse;
import com.facebook.login.LoginFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import br.edu.ufcspa.snorlax_angelo.client.LoginClient;
import br.edu.ufcspa.snorlax_angelo.constants.AppConstants;
import br.edu.ufcspa.snorlax_angelo.database.DataBaseAdapter;
import br.edu.ufcspa.snorlax_angelo.helpers.FbConnectHelper;
import br.edu.ufcspa.snorlax_angelo.helpers.GooglePlusSignInHelper;
import br.edu.ufcspa.snorlax_angelo.managers.SharedPreferenceManager;
import br.edu.ufcspa.snorlax_angelo.model.User;
import br.edu.ufcspa.snorlax_angelo.model.UserModel;
import ufcspa.edu.br.snorlax_angelo.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogin extends Fragment implements FbConnectHelper.OnFbSignInListener, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "app";
    @Bind(R.id.progress_bar) ProgressBar progressBar;
    @Bind (R.id.login_facebook) ImageButton btFbLogin;
    @Bind (R.id.login_google) ImageButton btGmLogin;

   /* @Bind(R.id.login_layout)
    LinearLayout view;*/

    private FbConnectHelper fbConnectHelper;
    private GoogleApiClient mGoogleApiClient;
    private final static int RC_SIGN_IN = 100;


    public FragmentLogin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setup();



    }


    private void setupGoogle(){

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [END build_client]

        // [START customize_button]
        // Set the dimensions of the sign-in button.
        // [END customize_button]
    }



    private void setup() {
        setupGoogle();
        fbConnectHelper = new FbConnectHelper(this,this);
    }

    @OnClick(R.id.login_google)
    public void loginwithGoogle(View view) {
        signIn();
        setBackground();
    }

    @OnClick(R.id.login_facebook)
    public void loginwithFacebook(View view) {
        fbConnectHelper.connect();
        setBackground();
    }

    private void setBackground()
    {
        //View().setBackgroundColor(getActivity().getResources().getColor(colorId));
        progressBar.setVisibility(View.VISIBLE);
        //view.setVisibility(View.GONE);
        btFbLogin.setVisibility(View.GONE);
        btGmLogin.setVisibility(View.GONE);
    }

    private void resetToDefaultView(String message)
    {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        //getView().setBackgroundColor(getActivity().getResources().getColor(android.R.color.white));
        progressBar.setVisibility(View.GONE);
        //view.setVisibility(View.VISIBLE);
        btFbLogin.setVisibility(View.VISIBLE);
        btGmLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fbConnectHelper.onActivityResult(requestCode, resultCode, data);
        getResult(requestCode,resultCode,data);
    }

    @Override
    public void OnFbSuccess(GraphResponse graphResponse) {
        UserModel userModel = getUserModelFromGraphResponse(graphResponse);
        if(userModel!=null) {
            SharedPreferenceManager.getSharedInstance().saveUserModel(userModel);
            communicateWebService(userModel);
        }
    }

    @Override
    public void OnFbError(String errorMessage) {
        resetToDefaultView(errorMessage);
    }

    private UserModel getUserModelFromGraphResponse(GraphResponse graphResponse)
    {
        UserModel userModel = new UserModel();
        try {
            JSONObject jsonObject = graphResponse.getJSONObject();
            userModel.userName = jsonObject.getString("name");
            userModel.userEmail = jsonObject.getString("email");
            String id = jsonObject.getString("id");
            userModel.idFacebook=id;
            String profileImg = "http://graph.facebook.com/"+ id+ "/picture?type=large";
            userModel.profilePic = profileImg;
            Log.i(TAG,profileImg);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userModel;
    }







    private void communicateWebService(UserModel userModel){
        User u = new User(0,userModel.idGoogle,userModel.idFacebook,userModel.userName,userModel.userEmail,userModel.profilePic);
        u.setSmartphoneInfo("android version:"+Utilities.getAndroidVersion()+" model:"+ Utilities.getPhoneModel() + " space MB:" + Utilities.getAvailableSpaceInMB());
        if (u.getId_user_facebook()==null){
            u.setId_user_facebook("");
        }else if (u.getId_user_google()==null){
            u.setId_user_google("");
        }
        LoginClient client = new LoginClient(getActivity(),u);
        client.send();
        startHomeActivity(userModel);
    }


    // [START signIn]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    // [START onActivityResult]
    public void getResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

           /* mStatusTextView.setText();*/
            Toast.makeText(getActivity(),"logado com sucesso!",Toast.LENGTH_SHORT).show();
            Log.d("gsign",acct.getEmail()+"\n"+acct.getGivenName()+" " + acct.getFamilyName()+"\n"+acct.getId());

            UserModel userModel = new UserModel();
            userModel.userName = acct.getGivenName()+ " " + acct.getFamilyName() ;
            userModel.userEmail = acct.getEmail();
            userModel.idGoogle=acct.getId();
            Uri photoUrl = acct.getPhotoUrl();
            if(photoUrl!=null)
                userModel.profilePic = photoUrl.toString();
            else
                userModel.profilePic = "";

            SharedPreferenceManager.getSharedInstance().saveUserModel(userModel);
            communicateWebService(userModel);

        } else {
            // Signed out, show unauthenticated UI.
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }



    private void startHomeActivity(UserModel userModel)
    {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.putExtra(UserModel.class.getSimpleName(), userModel);
        startActivity(intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getActivity().finishAffinity();
        }
    }


}
