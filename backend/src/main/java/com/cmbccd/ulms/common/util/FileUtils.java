package com.cmbccd.ulms.common.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * 
* <p>Title: FileUtils.java</p>  
* <p>Description:文件操作工具类 </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
public class FileUtils {
    public static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    
    
    public static String getPath() {
    	String relativelyPath=System.getProperty("user.dir");  
    	return relativelyPath;
    }

    /**
     * 指定目录下创建空文件
     *
     * @param filePath
     * @param fileName
     * @return
     */
    public static Boolean createNewFile(String filePath, String fileName) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符  防止跨平台出现路径问题
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        //文件夹不存在则新建
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        //新建文件
        File myFile = new File(filePath, fileName);
        try {
            myFile.createNewFile();
        } catch (IOException e) {
            logger.error("创建文件失败: {}", fileName, e);
        }
        return true;
    }


    /**
     * 读文件
     * @param filePath /Users/baonier/Downloads/
     * @param fileName 2018.txt
     * @return
     */
    public static List<String[]> readerFile(String filePath, String fileName){
        logger.info("call[FileUtils][readerFile]PARAMETER:[" + filePath+fileName + "]");
        long currentTime = System.currentTimeMillis();
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符  防止跨平台出现路径问题
        if (!filePath.endsWith(File.separator)){
            filePath = filePath + File.separator;
        }
        // Text文件
        File file = new File(filePath+fileName);
        // 构造一个BufferedReader类来读取文件
        BufferedReader br = null;
        String s = null;
        String[] readers = null;
        List<String[]> readersList = new ArrayList<>();
        try {
            logger.info("开始读取文件路径："+filePath+"中的："+fileName);
            long readerTime = System.currentTimeMillis();
            br = new BufferedReader(new FileReader(file));
            // 使用readLine方法，一次读一行
            while ((s = br.readLine()) != null) {
                //拆分
                readers = s.split("\\|");
                //添加到集合中
                readersList.add(readers);
            }
            logger.info("读取文件路径："+filePath+"中的："+fileName+"一共耗时："+(System.currentTimeMillis()-readerTime));
        } catch (IOException e) {
            logger.error("读取文件路径："+filePath+"中的："+fileName+"发生异常，异常信息是：", e);
            return null;
        }finally {
            try {
                if (br != null){
                    br.close();
                }
            } catch (IOException e) {
                logger.error("关闭读取文件流发生异常，异常信息是：", e);
                readersList = null;
            }
        }
        logger.info("call[FileUtils][readerFile]PARAMETER:[" + filePath+fileName ,System.currentTimeMillis()-currentTime+"]");
        return readersList;
    }

    /**
     * 读文件举手系统聊天文件
     * @param filePath Downloads/
     * @param fileName 2018.txt
     * @return
     */
    public static List<JSONObject> readerChatRecordFile(String filePath, String fileName){
        logger.info("call[readerChatRecordFile][readerFile]PARAMETER:[" + filePath+fileName + "]");
        long currentTime = System.currentTimeMillis();
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符  防止跨平台出现路径问题
        if (!filePath.endsWith(File.separator)){
            filePath = filePath + File.separator;
        }
        // Text文件
        File file = new File(filePath+fileName);
        // 构造一个BufferedReader类来读取文件
        BufferedReader br = null;
        String s = null;
//        String[] readers = null;
        List<JSONObject> readersList = new ArrayList<>();
        try {
            logger.info("开始读取文件路径："+filePath+"中的："+fileName);
            long readerTime = System.currentTimeMillis();
            br = new BufferedReader(new FileReader(file));
            // 使用readLine方法，一次读一行
            while ((s = br.readLine()) != null) {

                //添加到集合中
                JSONObject jsonMsg = JSONObject.parseObject(s);
                readersList.add(jsonMsg);
            }
            logger.info("读取文件路径："+filePath+"中的："+fileName+"一共耗时："+(System.currentTimeMillis()-readerTime));
        } catch (IOException e) {
            logger.error("读取文件路径："+filePath+"中的："+fileName+"发生异常，异常信息是：", e);
            return null;
        }finally {
            try {
                if (br != null){
                    br.close();
                }
            } catch (IOException e) {
                logger.error("关闭读取文件流发生异常，异常信息是：", e);
                readersList = null;
            }
        }
        logger.info("call[FileUtils][readerFile]PARAMETER:[" + filePath+fileName ,System.currentTimeMillis()-currentTime+"]");
        return readersList;
    }
    
    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                logger.error("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                logger.error("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            logger.error("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir
     *            要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符  防止跨平台出现路径问题
        if (!dir.endsWith(File.separator)){
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            logger.error("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                /*
                    getAbsolutePath() 方法返回文件的绝对路径，如果构造的时候是全路径就直接返回全路径，
                    如果构造时是相对路径，就返回当前目录的路径 + 构造 File 对象时的路径
                 */
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag){
                    break;
                }
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag){
                    break;
                }
            }
        }
        if (!flag) {
            logger.error("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            logger.error("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }
}

