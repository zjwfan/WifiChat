package com.zjwfan.wifichat.util;

import java.io.File;

public class FileUtil {
	
	public static void delete(File file){
		if(file.isDirectory()){
			File[] childs=file.listFiles();
			for(File file2:childs)
				delete(file2);
		}
		file.delete();
	}
	
	public static String getExtension(String fileName){
		return fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
	}

}
