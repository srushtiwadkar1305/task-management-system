package com.taskmanager.dto;

public class StoredFileDto {

    private String localPath;
    private String cloudUrl;
    private String publicId;

    public StoredFileDto(String localPath, String cloudUrl, String publicId) {
        this.localPath = localPath;
        this.cloudUrl = cloudUrl;
        this.publicId = publicId;
    }

    public String getLocalPath() {
        return localPath;
    }

    public String getCloudUrl() {
        return cloudUrl;
    }

    public String getPublicId() {
        return publicId;
    }
}

