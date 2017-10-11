package com.example.bittukumar.lab07.RecyclerView;

/**
 * Created by Bittu Kumar on 11-10-2017.
 */

public class Comment {
   public String uid,name,text,timestamp;
    public Comment(String uid, String name, String text, String timestamp)
    {
        this.name = name;
        this.text = text;
        this.timestamp = timestamp;
        this.uid = uid;
    }
}

