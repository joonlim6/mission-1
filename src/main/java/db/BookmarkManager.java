package db;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookmarkManager {
	private final static String URL = "jdbc:sqlite:/"+Paths.get("").toAbsolutePath().toString()+"/wifi.db";
	
	public void createBookmarkTable() {
		int affected = -1;
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps_2 = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM sqlite_master WHERE tbl_name = 'BOOKMARK'";
		try {
			conn = DriverManager.getConnection(URL);
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				System.out.println("테이블이 이미 존재합니다");
			}	else {
				String createTbl = "CREATE TABLE WIFI (\r\n"
						+ "    X_SWIFI_MGR_NO      TEXT    PRIMARY KEY NOT NULL,\r\n"
						+ "    X_SWIFI_WRDOFC      TEXT    NULL,\r\n"
						+ "    X_SWIFI_MAIN_NM     TEXT    NULL,\r\n"
						+ "    X_SWIFI_ADRES1      TEXT    NULL,\r\n"
						+ "    X_SWIFI_ADRES2      TEXT    NULL,\r\n"
						+ "    X_SWIFI_INSTL_FLOOR TEXT    NULL,\r\n"
						+ "    X_SWIFI_INSTL_TY    TEXT    NULL,\r\n"
						+ "    X_SWIFI_INSTL_MBY   TEXT    NULL,\r\n"
						+ "    X_SWIFI_SVC_SE      TEXT    NULL,\r\n"
						+ "    X_SWIFI_CMCWR       TEXT    NULL,\r\n"
						+ "    X_SWIFI_CNSTC_YEAR  INTEGER NULL,\r\n"
						+ "    X_SWIFI_INOUT_DOOR  TEXT    NULL,\r\n"
						+ "    X_SWIFI_REMARS3     TEXT    NULL,\r\n"
						+ "    LAT                 REAL    NULL,\r\n"
						+ "    LNT                 REAL    NULL,\r\n"
						+ "    WORK_DTTM           TEXT    NULL\r\n"
						+ ");";
				ps_2 = conn.prepareStatement(createTbl);
				affected = ps.executeUpdate();
			}
		
		}	catch (SQLException e1) {
			e1.printStackTrace();
		} 	finally {
		    try {
				if(conn != null && !conn.isClosed()) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}		    
		    try {
				if(ps != null && !ps.isClosed()) ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    try {
				if(ps_2 != null && !ps_2.isClosed()) ps_2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    try {
				if(rs != null && !rs.isClosed()) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public void addBookmark(String groupName, String mainNumber) {
		String sql = "INSERT INTO BOOKMARK('GROUP_NAME', 'MAIN_NUMBER', 'REG_DATE') VALUES(?,?,?)";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
	}
}
