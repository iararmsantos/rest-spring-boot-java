package com.iarasantos.data.vo.v1;

import java.io.Serializable;

public class UploadFileResponseVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String fileName;
    private String downloadURI;
    private String fileType;
    private Long size;

    public UploadFileResponseVO() {
    }

    public UploadFileResponseVO(String fileName, String downloadURI, String fileType, Long size) {
        this.fileName = fileName;
        this.downloadURI = downloadURI;
        this.fileType = fileType;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadURI() {
        return downloadURI;
    }

    public void setDownloadURI(String downloadURI) {
        this.downloadURI = downloadURI;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
