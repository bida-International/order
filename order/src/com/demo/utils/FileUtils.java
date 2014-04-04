package com.demo.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils extends org.apache.commons.io.FileUtils {
	
	/**
	 * 判断文件是否存在
	 * @param filePath
	 * @return true-已存在 false-不存在
	 */
	public static boolean isExistFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 创建文件
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	public static void createFile(String fileDirectory, String fileName) throws IOException {
		File dir = new File(fileDirectory);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		if (fileName != null && !fileName.equals("")) {
			fileDirectory = fileDirectory + "/" + fileName;
			File file = new File(fileDirectory);
			if (!file.exists()) {
				file.createNewFile();
			}
		}
	}
	
	/**
	 * 删除文件
	 * @param filePath
	 */
	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}
	
	/**
	 * 读取文件字符串
	 * @param filePath
	 * @return
	 */
	public static String readFileToString(String filePath) throws IOException {
		File file = new File(filePath);
		return readFileToString(file, "UTF-8");
	}
	
	/**
	 * 将字符串写入文件
	 * @param filePath
	 * @param data
	 */
	public static void writeStringToFile(String filePath, String data) throws IOException {
		File file = new File(filePath);
		writeStringToFile(file, data, "UTF-8");
	}
	/**
	 * 删除文件夹下的所有文件
	 * @param folderPath
	 */
	 public static void deleteFolder(String folderPath) {
		     try {
		        delAllFile(folderPath); //删除完里面所有内容
		        String filePath = folderPath;
		        filePath = filePath.toString();
		        deleteFile(filePath);
		     } catch (Exception e) {
		       e.printStackTrace(); 
		     }
	  }
	 /**
	  * 递归检测文件下文件是否删除完毕
	  * @param path
	  * @return
	  */
	 public static boolean delAllFile(String path) {
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	             deleteFolder(path + "/" + tempList[i]);//再删除空文件夹
	             flag = true;
	          }
	       }
	       return flag;
	 }
}
