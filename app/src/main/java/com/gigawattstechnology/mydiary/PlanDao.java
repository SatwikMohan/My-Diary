package com.gigawattstechnology.mydiary;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlanDao {
    @Query("SELECT * FROM planData")
    List<PlanData> getAllPlans();

    @Insert
    void InsertPlan(PlanData...planDatas);

    @Delete
    void delete(PlanData planData);

}
