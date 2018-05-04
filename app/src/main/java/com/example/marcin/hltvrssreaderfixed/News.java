package com.example.marcin.hltvrssreaderfixed;

/**
 * Created by Marcin on 2018-03-31.
 */

public class News {
    private String tytul="", opis="", link="", data="", imgLink="", imgEventLink="", eventLink="";
    public News(String tytul, String opis, String link, String data, String imgLink, String eventLink, String imgEventLink){
        this.tytul = tytul;
        this.opis = opis;
        this.link = link;
        this.data = data;
        this.imgLink = imgLink;
        this.eventLink = eventLink;
        this.imgEventLink = imgEventLink;
    }

    public void printNews(){
        System.out.println("---------------");
        System.out.println(tytul + "\n" + opis + "\n" + data );
        System.out.println("---------------");
        System.out.println("");
        System.out.println("");
    }

    public String getTytul(){
        return tytul;
    }

    public String getOpis(){
        return opis;
    }

    public String getData(){
        return data;
    }

    public String getLink(){
        return link;
    }

    public String getImgLink(){
        return imgLink;
    }

    public String toString(){
        return tytul;
    }

    public String getEventLink() {return eventLink;}

    public String getImgEventLink() {return imgEventLink;}
}
