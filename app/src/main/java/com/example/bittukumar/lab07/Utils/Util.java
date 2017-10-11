package com.example.bittukumar.lab07.Utils;

import com.example.bittukumar.lab07.RecyclerView.Comment;

import java.util.List;

/**
 * Created by Bittu Kumar on 11-10-2017.
 */

public class Util {

    public static String getComment(List<Comment> commentList,boolean more)
    {
        String comment="";
        int n;
        if(more)n=3;
        else
            n=commentList.size();
        for(int i=0;i<n;i++)
        {
            comment+=commentList.get(i).name +" : " + commentList.get(i).text+"\n";
        }
        return comment;
    }
}
