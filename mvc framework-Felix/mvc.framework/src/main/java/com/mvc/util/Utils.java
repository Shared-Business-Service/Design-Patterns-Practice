package com.mvc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Utils {

	public static String getProjectPath(){
		return Utils.class.getResource("/").getPath();
	}
	
	public static Properties getConfig(){
		InputStream in=null;
		Properties config=new Properties();
		try {
			File file=new File(Utils.getProjectPath() + "config.properties");
			in = new FileInputStream(file);
			config.load(in);
		} catch (Exception e) {
			System.out.println("Can not find config file.");
		}
		return config;
	}
}
