

	package com.util;

	import java.sql.Connection;
import java.sql.DriverManager;

	public class DBUtil {

	    private static final String URL = "jdbc:mysql://localhost:3306/wechat_v1?useSSL=false&characterEncoding=UTF-8";
	    private static final String USER = "root";
	    private static final String PASSWORD = "123456";

	    static {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver"); // 新しいバージョン
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public static Connection getConnection() throws Exception {
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	    }
	
	
}
