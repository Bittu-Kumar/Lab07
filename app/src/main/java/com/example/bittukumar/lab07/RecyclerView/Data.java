package com.example.bittukumar.lab07.RecyclerView;


import java.sql.Timestamp;
import java.util.List;

public class Data {
    public String uid;
    public int postid;
    public String postText;
    public String comments;
    public String timestamp;
    public List<Comment> commentList;
    public boolean more;

    public Data(String uid, int postid, String postText, String comments, String timestamp, List<Comment> commentList,boolean more) {
        this.uid = uid;
        this.postid = postid;
        this.postText = postText;
        this.comments = comments;
        this.timestamp = timestamp;
        this.commentList = commentList;
        this.more = more;
    }

}

