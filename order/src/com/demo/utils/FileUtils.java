package com.demo.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils extends org.apache.commons.io.FileUtils {
	
	/**
	 * �ж��ļ��Ƿ����
	 * @param filePath
	 * @return true-�Ѵ��� false-������
	 */
	public static boolean isExistFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		}
		return false;
	}
	
	/**
	 * �����ļ�
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
	 * ɾ���ļ�
	 * @param filePath
	 */
	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}
	
	/**
	 * ��ȡ�ļ��ַ���
	 * @param filePath
	 * @return
	 */
	public static String readFileToString(String filePath) throws IOException {
		File file = new File(filePath);
		return readFileToString(file, "UTF-8");
	}
	
	/**
	 * ���ַ���д���ļ�
	 * @param filePath
	 * @param data
	 */
	public static void writeStringToFile(String filePath, String data) throws IOException {
		File file = new File(filePath);
		writeStringToFile(file, data, "UTF-8");
	}
	/**
	 * ɾ���ļ����µ������ļ�
	 * @param folderPath
	 */
	 public static void deleteFolder(String folderPath) {
		     try {
		        delAllFile(folderPath); //ɾ����������������
		        String filePath = folderPath;
		        filePath = filePath.toString();
		        deleteFile(filePath);
		     } catch (Exception e) {
		       e.printStackTrace(); 
		     }
	  }
	 /**
	  * �ݹ����ļ����ļ��Ƿ�ɾ�����
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
	             delAllFile(path + "/" + tempList[i]);//��ɾ���ļ���������ļ�
	             deleteFolder(path + "/" + tempList[i]);//��ɾ�����ļ���
	             flag = true;
	          }
	       }
	       return flag;
	 }
}
