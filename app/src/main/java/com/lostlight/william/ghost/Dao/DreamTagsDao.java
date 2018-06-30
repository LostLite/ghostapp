package com.lostlight.william.ghost.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.lostlight.william.ghost.models.DreamTags;

import java.util.List;

@Dao
public interface DreamTagsDao {

    @Insert
    void insertDramTag(DreamTags dreamTags);
}
