package com.tiffin.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Hook.class}, version = 1, exportSchema = false)
public abstract class DatabaseClass extends RoomDatabase {
    public abstract HookDaoAccess hookDaoAccess();
}