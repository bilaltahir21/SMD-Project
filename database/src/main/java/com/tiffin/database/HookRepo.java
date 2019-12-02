package com.tiffin.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Room;

import java.util.List;

public class HookRepo {

    private String DB_NAME = "app_core_db";
    private DatabaseClass fenceDatabase;

    public HookRepo(Context context) {
        getInstance(context);
    }

    private DatabaseClass getInstance(Context context) {
        if (fenceDatabase != null) {
            return fenceDatabase;
        } else {
            fenceDatabase = Room.databaseBuilder(context, DatabaseClass.class, DB_NAME).build();
        }
        return fenceDatabase;
    }

    public void insertTask(String statusCode,String version,String appMetaName) {
        Hook hook = new Hook();
        hook.setStatusCode(statusCode);
        hook.setVersion(version);
        hook.setAppMetaName(appMetaName);
        insertTask(hook);
    }

    @SuppressLint("StaticFieldLeak")
    public void insertTask(final Hook hook) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Log.e("HookRepo", "Inserting!!!! from Background Thread: " + Thread.currentThread().getId());
                fenceDatabase.hookDaoAccess().insertTask(hook);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void updateTask(final Hook hook) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                fenceDatabase.hookDaoAccess().updateTask(hook);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteTask(final Hook hook) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                fenceDatabase.hookDaoAccess().deleteTask(hook);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private Hook getTask(final int id) {
        new AsyncTask<Void, Void, Hook>() {
            @Override
            protected Hook doInBackground(Void... voids) {
                return fenceDatabase.hookDaoAccess().getTask(id);
            }

            @Override
            protected void onPostExecute(Hook fence) {
                super.onPostExecute(fence);
            }
        }.execute();
        return null;
    }

    @SuppressLint("StaticFieldLeak")
    public List<Hook> getTasks() {
        new AsyncTask<Void, Void, List<Hook>>() {
            @Override
            protected List<Hook> doInBackground(Void... voids) {
                return fenceDatabase.hookDaoAccess().fetchAllTasks();
            }

            @Override
            protected void onPostExecute(List<Hook> fences) {
                Log.e("HookRepo", "Db size: " + fences.size());
                for (Hook element : fences) {
                    Log.e("HookRepo", Integer.toString(element.getId()));
                }
                super.onPostExecute(fences);
            }
        }.execute();
        return null;
    }

}
