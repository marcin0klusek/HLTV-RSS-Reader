package com.example.marcin.hltvrssreaderfixed;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lista;
    private ArrayList<News> artykuly = new ArrayList<News>();
    private boolean pobranoArtykuly = false;
    private DisplayMetrics metrics;
    protected int width = 0, height = 0;
    protected boolean isAppOff = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //checkConnection(); // sprawdza polaczenie do internetu

        if (!isOnline()) {
            isAppOff=true;
            showToast(0,"Turn on mobile data or WiFi connection.");
            finishAndRemoveTask();
           // onBackPressed();
        }

        lista = findViewById(R.id.listaNews);

        getRes(); // pobiera rozdzielczosc
        getRss(); // pobiera wszystkie artykuly

       while(!pobranoArtykuly){
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setListView();

    }

    private void getRes() {
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        width = metrics.widthPixels; height = metrics.heightPixels;
    }

    @Override
    public void onBackPressed() {

        android.os.Process.killProcess(android.os.Process.myPid());
        // This above line close correctly
    }

    private void getRss() {
        new MakeNews(this).execute();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showToast(int time, String tresc){
        int ms = 2500;
        if(time != 0) ms = time;
        Toast.makeText(MainActivity.this, tresc, ms).show();
    }

    protected void setList(ArrayList<News> artykuly) {
        this.artykuly = artykuly;
        pobranoArtykuly=true;
    }

    private void setListView() {
        lista.setAdapter(new NewsAdapter(this, R.layout.news_item, artykuly));
    }

    protected void goToUrl(String link) {
        Intent i = new Intent(Intent.ACTION_VIEW,
                Uri.parse(link));
        startActivity(i);
    }
}
