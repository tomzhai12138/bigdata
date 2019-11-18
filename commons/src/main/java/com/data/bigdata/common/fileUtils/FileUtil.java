package com.data.bigdata.common.fileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: FileUtil
 * @Description: Java 文件操作工具类
 * @author: zhaiyongji
 * @date: 2019年9月23日 下午2:16:39
 * 
 * @Copyright: © 2019 版权所有
 */
public class FileUtil {
	
	 private static Logger log = LoggerFactory.getLogger(FileUtil.class);
	/**
	 * 
	 * @Title: readTxtFile @Description: Java读取txt文件的内容 @auth:
	 *         zhaiyongji @param: @param filePath @param: @return @return:
	 *         String @throws
	 */
	public static String readTxtFile(String filePath) {
		StringBuilder content = new StringBuilder("");
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					String[] result = lineTxt.split(",");
					for (String string : result) {
						content.append(string);
					}
					content.append("\r\n");// txt换行
				}
				read.close();
			} else {
				log.error("找不到指定文件！！！");
			}
		} catch (Exception e) {
			log.error("读取文件内容出错："+e);
			e.printStackTrace();
		}
		return content.toString();
	}

	// 读取json文件
	public static String readJsonFile(String fileName) {
		BufferedReader reader = null;
		String laststr = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(fileName);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr += tempString;
			}
			reader.close();
		} catch (IOException e) {
			log.error("读取文件内容异常："+e);
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					log.error("reader.close()异常："+e);
					e.printStackTrace();
				}
			}
		}
		return laststr;
	}

}
