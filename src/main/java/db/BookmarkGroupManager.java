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

public class BookmarkGroupManager {
	private final static String URL = "jdbc:sqlite:/"+Paths.get("").toAbsolutePath().toString()+"/wifi.db";

	public void createBookmarkGroupTable() {
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
		String sql = "SELECT * FROM sqlite_master WHERE tbl_name = 'BOOKMARK_GROUP'";
		try {
			conn = DriverManager.getConnection(URL);
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				System.out.println("테이블이 이미 존재합니다");
			}	else {
				String createTbl = "CREATE TABLE BOOKMARK_GROUP (\r\n"
						+ "	GROUP_ID INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
						+ "	GROUP_NAME TEXT NOT NULL,\r\n"
						+ "	GROUP_ORDER INTEGER NOT NULL,\r\n"
						+ "	REGISTER_DATE TEXT NULL,\r\n"
						+ "	LAST_EDIT TEXT NULL\r\n"
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
	
	public List<BookmarkGroup> getBookmarkGroups() {
		List<BookmarkGroup> list = new ArrayList<>();
		
		String sql = "SELECT * FROM BOOKMARK_GROUP;";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(URL);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				BookmarkGroup bg = new BookmarkGroup();
				bg.setGroupId(rs.getInt("GROUP_ID"));
				bg.setGroupName(rs.getString("GROUP_NAME"));
				bg.setOrder(rs.getInt("GROUP_ORDER"));
				bg.setRegisterDate(rs.getString("REGISTER_DATE"));
				bg.setLastEdit(rs.getString("LAST_EDIT"));
				list.add(bg);
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
	
	public void addBookMarkGroup(int order, String groupName) {
		updateOrder(order);
		
		int affected = -1;
		LocalDateTime ldt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO BOOKMARK_GROUP(GROUP_NAME, GROUP_ORDER, REGISTER_DATE) VALUES(?, ?, ?);";
        try {
        	conn = DriverManager.getConnection(URL);
        	ps = conn.prepareStatement(sql);
        	ps.setString(1, groupName);
        	ps.setInt(2, order);
        	ps.setString(3, ldt.toString());
        	affected = ps.executeUpdate();
        	
        	if(affected > 0) {
        		System.out.println("새로운 북마크 그룹이 성공적으로 추가되었습니다");
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
	
	public void updateOrder(int order) {
		int affected = -1;
		
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps_2 = null;
        String sql = "SELECT * FROM BOOKMARK_GROUP WHERE GROUP_ORDER >= ?";
        String updateSql = "UPDATE BOOKMARK_GROUP\r\n"
        		+ "SET GROUP_ORDER = ?\r\n"
        		+ "WHERE GROUP_ORDER = ?";
        ResultSet rs = null;
        
        try {
        	conn = DriverManager.getConnection(URL);
        	ps = conn.prepareStatement(sql);
        	ps.setInt(1, order);
        	rs = ps.executeQuery();
        	
        	while(rs.next()) {
        		int ord = rs.getInt("GROUP_ORDER");
        		ps_2 = conn.prepareStatement(updateSql);
        		ps_2.setInt(1, ord+1);
        		ps_2.setInt(2, ord);
        		
        		affected = ps_2.executeUpdate();
        		if(affected > 0) {
        			System.out.printf("Order of Bookmark Group %d has been updated\n", rs.getInt("GROUP_ID"));
        		}
        		affected = -1;
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
	
	public int deleteBookmarkGroup(String groupName) {
		int affected = -1;
		
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM BOOKMARK_GROUP WHERE GROUP_NAME = ?;";
        try {
        	conn = DriverManager.getConnection(URL);
        	ps = conn.prepareStatement(sql);
        	ps.setString(1, groupName);
        	affected = ps.executeUpdate();
        	
        	if(affected > 0) {
        		System.out.println("북마크 '"+groupName+"'이 삭제되었습니다");
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
