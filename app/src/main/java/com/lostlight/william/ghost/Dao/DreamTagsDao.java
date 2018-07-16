package com.lostlight.william.ghost.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.lostlight.william.ghost.models.DreamTags;
import com.lostlight.william.ghost.models.Tags;

import java.util.List;

@Dao
public interface DreamTagsDao {

    @Insert
    void insertDramTag(DreamTags dreamTags);

    @Query("SELECT title FROM Tags WHERE Id IN (SELECT tag_id FROM dreamTags WHERE dream_id = :dreamId)")
    List<String> getMyDreamTags(int dreamId);
}
