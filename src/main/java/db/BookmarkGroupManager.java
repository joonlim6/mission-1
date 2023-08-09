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
	
	public void deleteTable() {
		int affected = -1;
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "DROP TABLE BOOKMARK_GROUP;";
		try {
			conn = DriverManager.getConnection(URL);
			ps = conn.prepareStatement(sql);
			
			affected = ps.executeUpdate();
			
			if(affected > 0) {
				System.out.println("table dropped");
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
		
		String sql = "SELECT * FROM BOOKMARK_GROUP ORDER BY GROUP_ORDER;";
		
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
				bg.setGroupOrder(rs.getInt("GROUP_ORDER"));
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
		updateOrder(lastOrder()+1, order);
		
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
	

	public void updateOrder(int prevOrder, int newOrder) {
		int affected = -1;
		
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps_2 = null;
        ResultSet rs = null;
        String sql = "";
        String updateSql = "UPDATE BOOKMARK_GROUP\r\n"
        		+ "SET GROUP_ORDER = GROUP_ORDER+1\r\n"
        		+ "WHERE GROUP_ID = ?";
        String updateSql2 = "UPDATE BOOKMARK_GROUP\r\n"
        		+ "SET GROUP_ORDER = GROUP_ORDER-1\r\n"
        		+ "WHERE GROUP_ID = ?";
        
        if(prevOrder > newOrder) {
        	sql = "SELECT * FROM BOOKMARK_GROUP WHERE GROUP_ORDER >= ? AND GROUP_ORDER < ?;";
        }	else if(prevOrder < newOrder) {
        	sql = "SELECT * FROM BOOKMARK_GROUP WHERE GROUP_ORDER > ? AND GROUP_ORDER <= ?;";
        }	else {
        	return;
        }
;

        try {
        	conn = DriverManager.getConnection(URL);
        	ps = conn.prepareStatement(sql);
        	if(prevOrder > newOrder) {
        		ps.setInt(1, newOrder);
        		ps.setInt(2, prevOrder);
        	}	else {
        		ps.setInt(1, prevOrder);
        		ps.setInt(2, newOrder);       		
        	}
        	rs = ps.executeQuery();
        	
        	while(rs.next()) {
        		int groupId = rs.getInt("GROUP_ID");
            	if(prevOrder > newOrder) {
            		ps_2 = conn.prepareStatement(updateSql);
            		ps_2.setInt(1, groupId);            		
            	}	else {
            		ps_2 = conn.prepareStatement(updateSql2);
            		ps_2.setInt(1, groupId);            		
            	}
        		
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
	
	
	public int updateBookmarkGroup(int groupId, String newName, int newOrder) {
		BookmarkGroup bmg = getBmg(groupId);
		int prevOrder = bmg.getGroupOrder();
		updateOrder(prevOrder, newOrder);
		int affected = -1;
		String sql = "UPDATE BOOKMARK_GROUP SET GROUP_NAME = ?, GROUP_ORDER = ?, LAST_EDIT = ? WHERE GROUP_ID = ?;";
		
		LocalDateTime ldt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Connection conn = null;
        PreparedStatement ps = null;
        try {
        	conn = DriverManager.getConnection(URL);
        	ps = conn.prepareStatement(sql);
        	ps.setString(1, newName);
        	ps.setInt(2, newOrder);
        	ps.setString(3, ldt.toString());
        	ps.setInt(4, groupId);
        	affected = ps.executeUpdate();
        	
        	if(affected > 0) {
        		System.out.println("해당 북마크가 수정되었습니다");
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
	
	public BookmarkGroup getBmg(int groupId) {
		BookmarkGroup bgm = new BookmarkGroup();
		String sql = "SELECT * FROM BOOKMARK_GROUP WHERE GROUP_ID = ?;";
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        	conn = DriverManager.getConnection(URL);
        	ps = conn.prepareStatement(sql);
        	ps.setInt(1, groupId);
        	rs = ps.executeQuery();
        	
        	if(rs.next()) {
        		bgm.setGroupId(rs.getInt("GROUP_ID"));
        		bgm.setGroupName(rs.getString("GROUP_NAME"));
        		bgm.setGroupOrder(rs.getInt("GROUP_ORDER"));
        		bgm.setRegisterDate(rs.getString("REGISTER_DATE"));
        		bgm.setLastEdit(rs.getString("LAST_EDIT"));		
        	}	else {
        		System.out.println("해당 북마크가 존재하지 않습니다");
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
		return bgm;
	}
	
	public int lastOrder() {
		int lastOrder = -1;
		String sql = "SELECT GROUP_ORDER FROM BOOKMARK_GROUP ORDER BY GROUP_ORDER DESC LIMIT 1;";
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        	conn = DriverManager.getConnection(URL);
        	ps = conn.prepareStatement(sql);
        	rs = ps.executeQuery();
        	
        	if(rs.next()) {
        		lastOrder = rs.getInt("GROUP_ORDER");
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
        
        return lastOrder;
	}
	
	public int deleteBookmarkGroup(String groupName) {
		int affected = -1, after = Integer.MAX_VALUE;
		
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps_1 = null;
        PreparedStatement ps_2 = null;
        String get = "SELECT GROUP_ORDER FROM BOOKMARK_GROUP WHERE GROUP_NAME = ?;";
        String sql = "DELETE FROM BOOKMARK_GROUP WHERE GROUP_NAME = ?;";
        
        try {
        	conn = DriverManager.getConnection(URL);
        	ps_1 = conn.prepareStatement(get);
        	ps_1.setString(1, groupName);
        	rs = ps_1.executeQuery();
        	
        	if(rs.next()) {
        		after = rs.getInt("GROUP_ORDER");
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
				if(ps_1 != null && !ps_1.isClosed()) ps_1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    try {
				if(rs != null && !rs.isClosed()) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
     
        try {
        	conn = DriverManager.getConnection(URL);
        	ps_2 = conn.prepareStatement(sql);
        	ps_2.setString(1, groupName);
        	affected = ps_2.executeUpdate();
        	
        	if(affected > 0) {
        		System.out.printf("북마크 %s이 삭제되었습니다\n", groupName);
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
				if(ps_2 != null && !ps_2.isClosed()) ps_2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        
        postDelete(after);
        
        return affected > 0 ? 1 : -1;
	}
	
	public void postDelete(int after) {
		updateOrder(after, lastOrder());
	}
}
