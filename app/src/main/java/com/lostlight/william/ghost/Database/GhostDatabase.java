package com.lostlight.william.ghost.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

import com.lostlight.william.ghost.Dao.DreamTagsDao;
import com.lostlight.william.ghost.Dao.DreamsDao;
import com.lostlight.william.ghost.Dao.TagsDao;
import com.lostlight.william.ghost.models.DreamTags;
import com.lostlight.william.ghost.models.Dreams;
import com.lostlight.william.ghost.models.Tags;

//This class will be the bridge between our app and the SQLite database where we shall
//persist our data
@Database(entities = {Tags.class, Dreams.class, DreamTags.class}, version=1, exportSchema = false)
public abstract class GhostDatabase extends RoomDatabase {
    public abstract TagsDao tagsDao();
    public abstract DreamsDao dreamsDao();
    public abstract DreamTagsDao dreamTagsDao();
}
