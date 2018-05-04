package com.example.marcin.hltvrssreaderfixed;

import android.app.ActionBar;
import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Marcin on 2018-03-31.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    private Picasso.Builder builder;
    private MainActivity mainActivity;

    public NewsAdapter(MainActivity mainActivity, int news_item, ArrayList<News> artykuly) {
        super(mainActivity, news_item, artykuly);
        this.mainActivity = mainActivity;
        getPiBuilder();
    }
    class MyViewHolder{
        ImageView ikona;
        TextView tytul;
        ImageView zdjecie;
        TextView opis;

        MyViewHolder( View v ){
            ikona = (ImageView) v.findViewById(R.id.ikonaEventu);
            tytul = (TextView) v.findViewById(R.id.tytul);
            zdjecie = (ImageView) v.findViewById(R.id.zdjecieNewsa);
            opis = (TextView) v.findViewById(R.id.opis);
        }

    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        MyViewHolder holder = null;
        if(view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mainActivity);
            view = layoutInflater.inflate(R.layout.news_item, null);
            holder = new MyViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (MyViewHolder) view.getTag();
        }

        News news = getItem(position);


        final String linkToNews = news.getLink();
        String linkEvent = "https://hltv.org";





        if(news.getImgEventLink().equals("")){
           builder.build().load(R.drawable.event_logo).into(holder.ikona);
        }else{
            linkEvent = news.getEventLink();
            final String linkToEvent = linkEvent;
            builder.build().load(news.getImgEventLink()).into(holder.ikona, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess(){
                }

                @Override
                public void onError(Exception e) {
                    Log.e("Picasso failed", "Błąd" + e.getMessage());
                }
            });
            holder.ikona.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.goToUrl(linkToEvent);
                }
            });
        }
        builder.build().load(news.getImgLink()).into(holder.zdjecie);

        int bok = (int) (mainActivity.width * 0.07),
        szerokosc = (int)(mainActivity.height/4),
        tytulSize = (int)(mainActivity.height * 0.012),
        opisSize = (int)(mainActivity.height * 0.008);

        scaleToIcon(holder.ikona, bok,bok);
        scaleToParent(holder.zdjecie, szerokosc);

        holder.zdjecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.goToUrl(linkToNews);
            }
        });
        int limit = 51;
        if(news.getTytul().length() < limit) holder.tytul.setText(news.getTytul().trim());
        else holder.tytul.setText(news.getTytul().substring(0,limit).trim() + "...");
        if(news.getTytul().length() < 35) {holder.tytul.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START); holder.tytul.setText("  " + holder.tytul.getText());}
        holder.tytul.setTextSize(tytulSize);
        holder.opis.setText(news.getOpis());
        holder.opis.setTextSize(opisSize);

        return view;
    }




    private void scaleToIcon(ImageView iv, int x, int y){
        iv.getLayoutParams().height = x;

        iv.getLayoutParams().width = y;

        iv.setScaleType(ImageView.ScaleType.FIT_XY);

    }

    private void scaleToParent(ImageView iv, int x){
        iv.getLayoutParams().width = ActionBar.LayoutParams.MATCH_PARENT;
        iv.getLayoutParams().height = x;

        iv.setScaleType(ImageView.ScaleType.FIT_XY);

    }


    private void getPiBuilder() {

        this.builder = new Picasso.Builder(mainActivity);
        builder.listener(new Picasso.Listener()
        {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
            {
                exception.printStackTrace();
            }
        });
    }
}