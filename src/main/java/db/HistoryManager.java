package db;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HistoryManager {
	private final static String URL = "jdbc:sqlite:/"+Paths.get("").toAbsolutePath().toString()+"wifi.db";
	
	@SuppressWarnings("resource")
	public void createTable() {
		int affected = -1;
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM sqlite_master WHERE tbl_name = 'HISTORY'";
		try {
			conn = DriverManager.getConnection(URL);
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(!rs.next()) {
				String createTbl = "CREATE TABLE HISTORY (\r\n"
						+ "   id		INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
						+ "   LAT       REAL    NULL,\r\n"
						+ "   LNT       REAL    NULL,\r\n"
						+ "   VIEW_DATE		TEXT    NULL\r\n"
						+ ");";
				ps = conn.prepareStatement(createTbl);
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
				if(rs != null && !rs.isClosed()) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public int count() {
		int count = 0;
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) AS NUMBER FROM HISTORY;";
		
		try {
			conn = DriverManager.getConnection(URL);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("NUMBER");
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
				if(rs != null && !rs.isClosed()) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}				
		return count;
	}
	
	public void addHistory(double lat, double lnt) {
		int affected = -1;
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		LocalDateTime ldt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		
		Connection conn = null;
		PreparedStatement ps = null;

		String sql = "INSERT INTO HISTORY VALUES (?, ?, ?, ?);";
		try {
			conn = DriverManager.getConnection(URL);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, getLastId()+1);
			ps.setDouble(2,lat);
			ps.setDouble(3,lnt);
			ps.setString(4, ldt.toString());
			affected = ps.executeUpdate();
			
			if(affected > 0) {
				System.out.println("히스토리가 추가되었습니다");
			}	else {
				System.out.println("히스토리 추가 실패");
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
		}
	}
	
	public int getLastId() {
		int id = -1;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT id FROM HISTORY ORDER BY id DESC LIMIT 1;";
		
		try {
			conn = DriverManager.getConnection(URL);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				id = rs.getInt("id");
			}	else {
				id = 0;
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
		}
		
		return id;
	}
	
	public List<History> getAllHistories() {
		List<History> list = new ArrayList<>();
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM HISTORY ORDER BY id DESC;";
		
		try {
			conn = DriverManager.getConnection(URL);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				History hs = new History();
				hs.setId(rs.getInt("id"));
				hs.setLatitude(rs.getDouble("LAT"));
				hs.setLongitude(rs.getDouble("LNT"));
				hs.setViewDate(rs.getString("VIEW_DATE"));
				list.add(hs);
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
				if(rs != null && !rs.isClosed()) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public int deleteById(int id) {
		int affected = -1;
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM HISTORY WHERE id=?";
		
		try {
			conn = DriverManager.getConnection(URL);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			affected = ps.executeUpdate();
			
			if(affected > 0) {
				System.out.println("지정한 히스토리가 삭제되었습니다");
				affected = id;
			}	else {
				System.out.println("삭제에 실패했습니다");
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
		}
		
		return affected;
	}
}