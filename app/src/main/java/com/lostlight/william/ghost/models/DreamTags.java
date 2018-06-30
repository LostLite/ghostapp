package com.lostlight.william.ghost.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class DreamTags {
    //This class defines our tags model

    @PrimaryKey(autoGenerate = true)
    private int Id;
    @ColumnInfo(name = "dream_id")
    private int DreamId;

    @ColumnInfo(name = "tag_id")
    private int TagId;

    //setters
    public void setId(int id){this.Id = id;}
    public void setDreamId(int dreamId){this.DreamId = dreamId;}
    public void setTagId(int tagId){this.TagId = tagId;}

    //getters
    public int getId(){return this.Id;}
    public int getDreamId(){return this.DreamId;}
    public int getTagId(){return this.TagId;}
}
