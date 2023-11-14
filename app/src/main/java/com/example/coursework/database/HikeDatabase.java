package com.example.coursework.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.coursework.dao.HikeDao;
import com.example.coursework.models.Hike;

@Database(entities = {Hike.class}, version = 1)
public abstract class HikeDatabase extends RoomDatabase {
    public abstract HikeDao hikeDao();
}
