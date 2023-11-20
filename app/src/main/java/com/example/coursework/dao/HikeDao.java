package com.example.coursework.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursework.models.Hike;

import java.util.List;
@Dao
public interface HikeDao {
    @Insert
    long insertHike(Hike hike);

    @Query("SELECT * FROM hikes ORDER BY nameHike")
    List<Hike> getAllHikes();

    @Delete
    void deleteHike(Hike hike);

    @Query("DELETE FROM hikes")
    void deleteAllHike();

    @Update
    void updateHike(Hike hike);

    @Query("SELECT * FROM hikes WHERE nameHike LIKE :name")
    List<Hike> searchHikes(String name);
}
