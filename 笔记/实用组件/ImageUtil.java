package com.jishixin.o2o.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/*
  User: 晨梦意志
  Date: 2019/6/11
  Time: 19:20
  Notes:图片处理与封装
*/
public class ImageUtil {
    //获取当前相对路径
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    //格式化日期
    private static final SimpleDateFormat sDate=new SimpleDateFormat("yyyyMMddHHmmss");
    //随机数对象
    private static final Random r=new Random();
    //生成带水印并且压缩的图片
    public static String generateThumbnail(InputStream thumbnailInputStream,String fileName , String targetAddr){
        String realFileName=getRandomFileName();
        String extension = getFileExtension(fileName);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest=new File(PathUtil.getImgBasePath()+relativeAddr);
        System.out.println(PathUtil.getImgBasePath()+relativeAddr);
        try{
            Thumbnails.of(thumbnailInputStream).size(200,200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/201612u01pldoqa.jpg")),0.25f)
                    .outputQuality(0.8f).toFile(dest);
        }catch (Exception e){
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        return relativeAddr;
    }

    //如果未创建路径,即创建路径
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath()+targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    //获得文件后缀
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    //生成随机文件名
    private static String getRandomFileName() {
        int rannum = r.nextInt(89999)+10000;
        String nowTimeStr=sDate.format(new Date());
        return nowTimeStr+rannum;
    }

    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("E:\\Ideastudy\\o2o\\src\\main\\resources\\IU.jpg")).size(200,200)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/201612u01pldoqa.jpg")),0.25f)
        .outputQuality(0.8f).toFile("E:\\Ideastudy\\o2o\\src\\main\\resources\\IUnew.jpg");

    }

    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
        if (fileOrPath.exists()){
            if (fileOrPath.isDirectory()){
                File files[] = fileOrPath.listFiles();
                for (int i=0;i<files.length;i++){
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }

}
