package com.wslint.wisdomreport.domain.dto.file;

import org.springframework.web.multipart.MultipartFile;

/** 批量上传文件实体类 @Author: rzg @Date: 2018/11/23 11:54 */
public class FileBatch {
  private String filePath;
  private String fileName;
  private MultipartFile uploadFile;

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public MultipartFile getUploadFile() {
    return uploadFile;
  }

  public void setUploadFile(MultipartFile uploadFile) {
    this.uploadFile = uploadFile;
  }
}
