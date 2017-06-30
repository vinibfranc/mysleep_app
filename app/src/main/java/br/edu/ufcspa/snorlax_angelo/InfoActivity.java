package br.edu.ufcspa.snorlax_angelo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import br.edu.ufcspa.snorlax_angelo.managers.SharedPreferenceManager;
import ufcspa.edu.br.snorlax_angelo.R;

/**
 * Created by icaroms on 15/04/2017.
 *
 * Activity que mostra Slideview do tutorial para utilizar o app
 *
 *
 */

public class InfoActivity extends AppIntro {
    private static final int RESULT_INSTRUCTIONS = 42;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance("", getResources().getString(R.string.tutorial_description_1), R.drawable.bck_tutorial_1_large, getResources().getColor(R.color.pallete_black)));
        addSlide(AppIntroFragment.newInstance("",  getResources().getString(R.string.tutorial_description_2), R.drawable.bck_tutorial_2_large, getResources().getColor(R.color.pallete_black)));
        addSlide(AppIntroFragment.newInstance("",  getResources().getString(R.string.tutorial_description_3), R.drawable.bck_tutorial_3_large, getResources().getColor(R.color.pallete_black)));

        //addSlide(AppIntroFragment.newInstance(title, description, image, backgroundColor));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(getResources().getColor(R.color.pallete_night));
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
        this.setResult(RESULT_INSTRUCTIONS);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

}
