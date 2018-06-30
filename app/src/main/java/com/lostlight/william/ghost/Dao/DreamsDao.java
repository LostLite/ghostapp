package com.lostlight.william.ghost.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.lostlight.william.ghost.models.Dreams;
import com.lostlight.william.ghost.models.Tags;

import java.util.List;

@Dao
public interface DreamsDao {
    //This interface will contain the main operations we can do on our database
    @Query("SELECT * FROM dreams")
    List<Dreams> getAll();

    @Query("SELECT * FROM dreams WHERE details LIKE :key_word")
    Dreams findByName(String key_word);

    @Query("SELECT * FROM dreams WHERE id IN (SELECT dream_id from dreamtags WHERE tag_id = :tag)")
    Dreams findByTag(int tag);

    @Query("SELECT * FROM dreams WHERE date LIKE :date")
    Dreams findByDate(String date);

    @Insert
    void insertAll(List<Dreams> dreams);

    @Insert
    long insertDream(Dreams dreams);
}
