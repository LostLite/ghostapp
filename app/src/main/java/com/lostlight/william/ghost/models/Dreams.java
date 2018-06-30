package com.lostlight.william.ghost.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Dreams {
    //This class defines our dreams model

    @PrimaryKey(autoGenerate = true)
    private int Id;
    @ColumnInfo(name = "date")
    private String Date;

    @ColumnInfo(name = "details")
    private String Details;
    //setters
    public void setId(int id){this.Id = id;}
    public void setDate(String date){this.Date = date;}
    public void setDetails(String details){this.Details = details;}

    //getters
    public int getId(){return this.Id;}
    public String getDate(){return this.Date;}
    public String getDetails(){return this.Details;}
}
