package com.rain.utils;

import java.io.File;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public class FileUpload {
    /**
     * @param file 上传的文件
     * @param path 文件的虚拟路径，例如 /upload
     * @param fileLocation 文件的物理路径，例如d:/upload
     * @return 文件的虚拟路径+文件名
     *
     * fileName.lastIndexOf(".")为获取后缀名
     * UUID.randomUUID()为获取唯一标识，保证文件的唯一性
     */
    public static String upload(MultipartFile file, String path, String fileLocation) {
        String fileFinishName = null;
        try {
            // 如果目录不存在则创建
            File uploadDir = new File(fileLocation);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            //获取源文件名称
            String fileName = file.getOriginalFilename();
            fileFinishName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."), fileName.length());
            //上传文件到指定目录下
            File uploadFile = new File(uploadDir + uploadDir.separator + fileFinishName);
            file.transferTo(uploadFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return path + "/" + fileFinishName;
    }
}