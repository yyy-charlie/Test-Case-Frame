package com.testcase.frame.common.bean;


import lombok.Data;

@Data
public class BusFile {


   private String   fileName;

   private String   realName;

   private String   fileType;

   private String   filepath;

   public BusFile() {
   }

   public BusFile(String fileName, String realName, String fileType, String filepath) {
      this.fileName = fileName;
      this.realName = realName;
      this.fileType = fileType;
      this.filepath = filepath;
   }
}
