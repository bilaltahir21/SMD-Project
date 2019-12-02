
package com.fast.tiffan_project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppVersion {

    @SerializedName("version:")
    @Expose
    private Double version;
    @SerializedName("app_meta_name")
    @Expose
    private String appMetaName;

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public String getAppMetaName() {
        return appMetaName;
    }

    public void setAppMetaName(String appMetaName) {
        this.appMetaName = appMetaName;
    }

}
