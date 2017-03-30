package com.anchorren.image.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
/**
 * 数据库操作工具类
 * @author REN
 * @time:2017年3月27日 下午3:08:45
 */
public class DBCPUtil {
	private static DataSource dataSource;
	static{
		try {
			//读取配置文件，初始化数据源
			InputStream in = DBCPUtil.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
			Properties props = new Properties();
			props.load(in);
			dataSource = BasicDataSourceFactory.createDataSource(props);
		}  catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
		
	}
	
	public static DataSource getDataSource(){
		return dataSource;
	}
	public static Connection getConnection(){
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("获取数据库连接失败");
		}
	}
	
	public static void release(PreparedStatement stmt,Connection conn,ResultSet rs){  
        if(rs!=null){  
            try {  
                rs.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
            rs = null;  
        }  
        if(stmt!=null){  
            try {  
                stmt.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
            stmt = null;  
        }  
        if(conn!=null){  
            try {  
                conn.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
            conn = null;  
        }  
    }  
	
	public static void release(PreparedStatement stmt,Connection conn){  
        
        if(stmt!=null){  
            try {  
                stmt.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
            stmt = null;  
        }  
        if(conn!=null){  
            try {  
                conn.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
            conn = null;  
        }  
    }  
}
