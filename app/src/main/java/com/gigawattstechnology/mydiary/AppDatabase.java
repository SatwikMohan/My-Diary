package com.gigawattstechnology.mydiary;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import io.realm.mongodb.App;

@Database(entities = {PlanData.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlanDao planDao();
    public static AppDatabase INSTANCE;
    public static AppDatabase getDbInstance(Context context){
        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"MyPlan_Database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

}
