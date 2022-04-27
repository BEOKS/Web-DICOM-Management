package com.knuipalab.dsmp.service.storage;

import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageProperties {
    /**
     * Folder location for storing files
     */
    String rootPath = System.getProperty("user.home");

    private String location = rootPath+"/Storage";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
