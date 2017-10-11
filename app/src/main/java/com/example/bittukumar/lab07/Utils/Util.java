package com.example.bittukumar.lab07.Utils;

import com.example.bittukumar.lab07.RecyclerView.Comment;

import java.util.List;

/**
 * Created by Bittu Kumar on 11-10-2017.
 */

public class Util {

    public static String getComment(List<Comment> commentList)
    {
        String comment="";
        for(int i=0;i<commentList.size();i++)
        {
            comment+=commentList.get(i).name +" : " + commentList.get(i).text+"\n";
        }
        return comment;
    }
}
