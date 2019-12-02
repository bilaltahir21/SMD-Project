package com.tiffin.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Hook {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String statusCode;
    private String version;
    private String appMetaName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppMetaName() {
        return appMetaName;
    }

    public void setAppMetaName(String appMetaName) {
        this.appMetaName = appMetaName;
    }
}
