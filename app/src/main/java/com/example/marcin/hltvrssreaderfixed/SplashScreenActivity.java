package com.example.marcin.hltvrssreaderfixed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.util.Random;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final int skala = 2;
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundResource(R.color.colorPrimaryDark)
                .withLogo(R.mipmap.ic_launcher_round)
                .withFooterText(losujTekst());

        config.getFooterTextView().setTextColor(getResources().getColor(R.color.black));
        config.getFooterTextView().setTextSize(12);
        config.getFooterTextView().setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        config.getLogo().setScaleX(skala); config.getLogo().setScaleY(skala);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);

    }

    private String losujTekst(){
        String[] teksty = {
                "Loading data...",
                "Loading data...",
                "Loading data...",
                "Loading data...",
                "[EN]Red meat is not bad for you. Fuzzy green meat is bad for you.",
                "Loading data...",
                "Loading data...",
                "Loading data...",
                "Loading data...",
                "[EN]Keep the dream alive. Hit the snooze button.",
                "Loading data...",
                "Loading data...",
                "Loading data...",
                "Loading data...",
                "[PL]Bunkrów nie ma, ale też jest fajnie.",
                "Loading data...",
                "Loading data...",
                "Loading data...",
                "Loading data...",
                "[PL]Przed wyruszeniem w drogę należy zebrać drużynę.",
                "Loading data...",
                "Loading data...",
                "Loading data...",
                "Loading data...",
                "[EN]Never look down on short people.",
                "Loading data...",
                "Loading data...",
                "Loading data...",
                "Loading data...",
                "[EN]Never play leapfrog with a unicorn.",
                "Loading data...",
                "Loading data...",
                "Loading data...",
                "Loading data...",
                "[PL]Może powtórzymy angielski?",
                "Loading data...",
                "Loading data...",
                "Loading data...",
                "Loading data..."
        };
        String wylosowany = teksty[0];

        Random rand = new Random();
        int index = rand.nextInt(teksty.length);
        wylosowany = teksty[index].toUpperCase() + "\n\n";
        return wylosowany;
    }
}
