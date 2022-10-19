package com.gigawattstechnology.mydiary;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PlanData {

    @PrimaryKey(autoGenerate = true)
    public int ID;

    @ColumnInfo(name = "Date")
    public String Date;

    @ColumnInfo(name = "PlanText")
    public String plan;

    public int getID() {
        return ID;
    }
}
