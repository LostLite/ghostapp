package com.lostlight.william.ghost.Database;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

//This application class is responsible for creating an instance of our database
public class App extends Application {

    public static App INSTANCE;
    private static final String DATABASE_NAME = "GhostDatabase";

    private GhostDatabase database;

    public static App get() {
        if(INSTANCE == null)
            INSTANCE = new App();
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // create database
        database = Room.databaseBuilder(getApplicationContext(), GhostDatabase.class, DATABASE_NAME)
                .build();

        INSTANCE = this;
    }

    public GhostDatabase getDB(Context context) {

        if(database == null){
            // create database
            database = Room.databaseBuilder(context, GhostDatabase.class, DATABASE_NAME)
                    .build();
        }

        return database;
    }
}
