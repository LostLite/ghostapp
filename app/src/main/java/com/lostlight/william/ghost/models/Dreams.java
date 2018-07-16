package com.lostlight.william.ghost.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.lostlight.william.ghost.Database.App;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

@Entity
public class Dreams implements Comparable<Dreams>{
    //This class defines our dreams model

    @PrimaryKey(autoGenerate = true)
    private int Id;
    @ColumnInfo(name = "date")
    private String Date;

    @ColumnInfo(name = "details")
    private String Details;

    @Ignore
    private String DreamTagsStr;
    //setters
    public void setId(int id){this.Id = id;}
    public void setDate(String date){this.Date = date;}
    public void setDetails(String details){this.Details = details;}
    public void setDreamTagsStr(final Context context, final int id){
        List<String> myTags =
                App.get().getDB(context).dreamTagsDao().getMyDreamTags(id);
        this.DreamTagsStr = TextUtils.join(", ",myTags);
    }

    //getters
    public int getId(){return this.Id;}
    public String getDate(){return this.Date;}
    public java.util.Date getRealDate(){
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        java.util.Date dateReturned = null;
        try {
            dateReturned = format.parse(this.getDate());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateReturned;
    }
    public String getDetails(){return this.Details;}

    public String getDreamTagsStr(){return this.DreamTagsStr;}


    @Override
    public int compareTo(Dreams compareDream) {

        int compareId = ((Dreams)compareDream).getId();
        //Descending order
        return compareId-this.getId();
    }
}
