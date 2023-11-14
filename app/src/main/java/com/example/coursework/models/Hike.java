package com.example.coursework.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "hikes")
public class Hike {
    @PrimaryKey(autoGenerate = true)
    public int hike_id;

    public String nameHike;

    public String locationHike;

    public String dateHike;

    public String parking;

    public String lengthHike;

    public String difficultyLevel;

    public String description;

    public Hike(int hike_id, String nameHike, String locationHike, String dateHike, String parking,
                String lengthHike, String difficultyLevel, String description) {
        this.hike_id = hike_id;
        this.nameHike = nameHike;
        this.locationHike = locationHike;
        this.dateHike = dateHike;
        this.parking = parking;
        this.lengthHike = lengthHike;
        this.difficultyLevel = difficultyLevel;
        this.description = description;
    }

    public Hike() {

    }

    public int getHike_id() {
        return hike_id;
    }

    public void setHike_id(int hike_id) {
        this.hike_id = hike_id;
    }

    public String getNameHike() {
        return nameHike;
    }

    public void setNameHike(String nameHike) {
        this.nameHike = nameHike;
    }

    public String getLocationHike() {
        return locationHike;
    }

    public void setLocationHike(String locationHike) {
        this.locationHike = locationHike;
    }

    public String getDateHike() {
        return dateHike;
    }

    public void setDateHike(String dateHike) {
        this.dateHike = dateHike;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getLengthHike() {
        return lengthHike;
    }

    public void setLengthHike(String lengthHike) {
        this.lengthHike = lengthHike;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
