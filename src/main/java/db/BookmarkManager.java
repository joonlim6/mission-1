package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BookmarkManager {
	private final static String URL = "jdbc:sqlite:resources:wifi.db";
	
	
	public void dropTable() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "DROP TABLE BOOKMARK;'";
		try {
			conn = DriverManager.getConnection(URL);
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				System.out.println("테이블을 삭제했습니다");
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
				String createTbl = "CREATE TABLE BOOKMARK (\r\n"
						+ "	BOOKMARK_ID INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
						+ "	GROUP_NAME TEXT NOT NULL,\r\n"
						+ "	MAIN_NUMBER TEXT NOT NULL,\r\n"
						+ "	REGISTER_DATE TEXT NULL,\r\n"
						+ "	FOREIGN KEY(GROUP_NAME) REFERENCES BOOKMARK_GROUP(GROUP_NAME) ON DELETE CASCADE,\r\n"
						+ "	FOREIGN KEY(MAIN_NUMBER) REFERENCES WIFI(X_SWIFI_MAIN_NM) ON DELETE CASCADE\r\n"
						+ ");"; 
				ps_2 = conn.prepareStatement(createTbl);
				affected = ps_2.executeUpdate();
				
				if(affected > 0) {
					System.out.println("북마크 그룹 테이블이 생성되었습니다");
				}
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
	
	public int addBookmark(String groupName, String mainNumber) {
		String sql = "INSERT INTO BOOKMARK('GROUP_NAME', 'MAIN_NUMBER', 'REGISTER_DATE') VALUES(?,?,?)";
		LocalDateTime ldt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		int affected = -1;
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = DriverManager.getConnection(URL);
			ps = conn.prepareStatement(sql);
			ps.setString(1, groupName);
			ps.setString(2, mainNumber);
			ps.setString(3, ldt.toString());
			
			affected = ps.executeUpdate();
			
			if(affected > 0) {
				System.out.println("북마크가 성공적으로 추가되었습니다");
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
		
		return affected > 0 ? 1 : -1;
	}
	
	public List<Bookmark> getAllBookmarks() {
		List<Bookmark> list = new ArrayList<>();
		String sql = "SELECT * FROM BOOKMARK;";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Bookmark bm = new Bookmark();
				bm.setBookmarkId(rs.getInt("BOOKMARK_ID"));
				bm.setGroupName(rs.getString("GROUP_NAME"));
				bm.setMainNumber(rs.getString("MAIN_NUMBER"));
				bm.setRegisterDate(rs.getString("REGISTER_DATE"));
				
				list.add(bm);
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
	
	public int deleteBookmark(String groupName, String mainNumber, String registerDate) {
		System.out.println(groupName+" "+mainNumber+" "+registerDate);
		int affected = -1;
		String sql = "DELETE FROM BOOKMARK WHERE GROUP_NAME = ? AND MAIN_NUMBER = ? AND REGISTER_DATE = ?;";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = DriverManager.getConnection(URL);
			ps = conn.prepareStatement(sql);
			ps.setString(1, groupName);
			ps.setString(2, mainNumber);
			ps.setString(3, registerDate);
			
			affected = ps.executeUpdate();
			
			if(affected > 0) {
				System.out.println("북마크를 성공적으로 삭제했습니다");
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
		
		return affected > 0 ? 1 : -1;
	}
}
