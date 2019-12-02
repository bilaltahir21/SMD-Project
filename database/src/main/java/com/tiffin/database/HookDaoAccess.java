package com.tiffin.database;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HookDaoAccess {
    @Insert
    Long insertTask(Hook hook);


    @Query("SELECT * FROM Hook")
    List<Hook> fetchAllTasks();


    @Query("SELECT * FROM Hook WHERE id =:taskId")
    Hook getTask(int taskId);


    @Update
    void updateTask(Hook fence);


    @Delete
    void deleteTask(Hook fence);
}
