package com.example.marcin.hltvrssreaderfixed;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Marcin on 2018-03-31.
 */

public class MakeNews extends AsyncTask<String , Void ,String> {
    private MainActivity activity;
    private ArrayList<News> artykuly = new ArrayList<News>();
    private boolean insideItem = false;
    private String str, tytul="", opis="", link="", data="", imgLink="", eventLink="", imgEventLink="";

    public MakeNews(MainActivity activity) {
        this.activity = activity;
    }

    protected String doInBackground(String... strings) {

        URLConnection connection;

        try {
            connection = new URL("https://www.hltv.org/rss/news").openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();

            BufferedReader r  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
            while ((str = r.readLine()) != null) {
                if(str.contains("<item>")){
                    insideItem=true;
                }else if(str.contains("</item>")){
                    insideItem=false;
                } else if (insideItem && str.contains("<title>")) {
                    tytul = str.substring(13, str.length()-8).replace("&quot;", "\"").replace("&amp;", "&");
                }else if (insideItem && str.contains("<link>")) {
                    link  = str.substring(12, str.length()-7);
                }else if(insideItem && str.contains("<description>")) {
                    opis = str.substring(19, str.length()-14).replace("&quot;", "\"").replace("&amp;", "&");
                }else if(insideItem && str.contains("<pubDate>")) {
                    data = str.substring(15, str.length()-10);
                    getImagesLink(link);

                    News news = new News(tytul, opis, link, data, imgLink, eventLink, imgEventLink);
                    artykuly.add(news);

                    tytul = ""; opis = ""; link = ""; data = ""; imgLink=""; eventLink=""; imgEventLink="";
                }
            }
            r.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!activity.isAppOff)
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity.getBaseContext(), "Setting images, please wait...", Toast.LENGTH_SHORT).show();
            }
        });
        activity.setList(artykuly);
        return null;
    }

    private void getImagesLink(String link) {
        URLConnection connection = null;
        try {
            connection = new URL(link).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();

            BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
            String str;
            while ((str = r.readLine()) != null) {
                if (str.contains("og:image")) {
                    imgLink = str.trim().substring(35, str.trim().length() - 2);
                } else if (str.contains("event text-ellipsis")) {
                    int ilosc = 0, linkStart = 0, linkEnd = 0, imgStart = 0, imgEnd = 0;
                    boolean tak3 = false, tak4 = false, tak7 = false, tak8 = false;
                    for (int i = 0; i < str.length(); i++) {
                        if (str.charAt(i) == '"') ilosc++;

                        if (ilosc == 3 && !tak3) {
                            linkStart = i + 1;
                            tak3 = true;
                        } else if (ilosc == 4 && !tak4) {
                            linkEnd = i;
                            tak4 = true;
                        } else if (ilosc == 7 && !tak7) {
                            imgStart = i + 1;
                            tak7 = true;
                        } else if (ilosc == 8 && !tak8) {
                            imgEnd = i;
                            break;
                        }
                    }

                    imgEventLink = str.substring(imgStart, imgEnd);
                    if(!imgEventLink.contains("hltv.org")) imgEventLink = "https://hltv.org" + imgEventLink;
                   // Log.i("LogoEventu", imgEventLink);
                    eventLink = "https://hltv.org" + str.substring(linkStart, linkEnd);
                    r.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
