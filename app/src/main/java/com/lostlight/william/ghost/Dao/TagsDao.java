package com.lostlight.william.ghost.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.lostlight.william.ghost.models.Tags;

import java.util.List;

@Dao
public interface TagsDao {
    //This interface will contain the main operations we can do on our database
    @Query("SELECT * FROM tags")
    List<Tags> getAll();

    @Query("SELECT * FROM tags WHERE title LIKE :title LIMIT 1")
    Tags findByName(String title);

    @Insert
    void insertAll(List<Tags> tags);

    @Insert
    long insertTag(Tags tag);
}
