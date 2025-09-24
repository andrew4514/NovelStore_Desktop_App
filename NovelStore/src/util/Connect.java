package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


public class Connect {
	private final String url = "jdbc:mysql://localhost:3306/novel_store";
	private final String user = "root";
	private final String password = "";
	
	public ResultSet rs;
	public ResultSetMetaData rsm;
	
	private Connection con;
	private static Connect connect;
	public PreparedStatement ps;
	
	public static Connect getInstance() {
		if(connect == null) {
			connect = new Connect();
		}
		return connect;
	}

	private Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Koneksi berhasil");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public ResultSet execQuery(String query) {
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery(query);
			rsm = rs.getMetaData();
		} catch (Exception e) {
			System.out.println("SQL Salah (executeQuery)");
		}
		
		return rs;
	}
	
	public void execUpdate(String query) {
		try {
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("SQL Salah (executeUpdate)");
			e.printStackTrace();
		}
	}
	
	public PreparedStatement prepareStatement(String query) {
		
			try {
				ps = con.prepareStatement(query);
			} catch (Exception e) {
				System.out.println("SQL salah (prepareStatement)");
			}
		
		return ps;
	}


        
	
}
