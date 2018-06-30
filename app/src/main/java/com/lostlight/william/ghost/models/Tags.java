package com.lostlight.william.ghost.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Tags {
    //This class defines our tags model

    @PrimaryKey(autoGenerate = true)
    private int Id;
    @ColumnInfo(name = "title")
    private String Title;

    //setters
    public void setId(int id){this.Id = id;}
    public void setTitle(String title){this.Title = title;}

    //getters
    public int getId(){return this.Id;}
    public String getTitle(){return this.Title;}
}
