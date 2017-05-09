package br.edu.ufcspa.snorlax_angelo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import br.edu.ufcspa.snorlax_angelo.managers.SharedPreferenceManager;
import ufcspa.edu.br.snorlax_angelo.R;

/**
 * Created by Silmara on 15/04/2017.
 */

public class InfoActivity extends AppIntro {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance("", "Please, ensure the ambient silence", R.drawable.dica_01, getResources().getColor(R.color.pallete_black)));
        addSlide(AppIntroFragment.newInstance("", "Plug the smartphone on the battery charger", R.drawable.dica_02, getResources().getColor(R.color.pallete_black)));
        addSlide(AppIntroFragment.newInstance("", "Let the smartphone beside the bed", R.drawable.dica_03, getResources().getColor(R.color.pallete_black)));

        //addSlide(AppIntroFragment.newInstance(title, description, image, backgroundColor));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(getResources().getColor(R.color.pallete_black));
        setSeparatorColor(getResources().getColor(R.color.pallete_blue_weak));

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        /*setVibrate(true);
        setVibrateIntensity(30);*/
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
       /* Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);*/
        SharedPreferenceManager.getSharedInstance().saveSeeInstructions(true);


        // Magic goes here
        this.setResult(42);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

}
