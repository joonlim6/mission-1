package db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class WifiManager {
	private final static String URL = "jdbc:sqlite:resources:wifi.db";
	
	public void insertAll(int totalNum) throws IOException {
		String sql = "INSERT INTO WIFI VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);\n";
		
		try {
	            Class.forName("org.sqlite.JDBC");
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }

	        Connection conn = null;
	        PreparedStatement ps = null;

        
        	try {
	        	conn = DriverManager.getConnection(URL);
	        	conn.setAutoCommit(false);
	        	ps = conn.prepareStatement(sql);
        	
			for(int i = 0; i <= totalNum/1000; i++) {
				int start = i*1000+1;
				
				StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
				urlBuilder.append("/" +  URLEncoder.encode("44476967516c736232355371566d61","UTF-8") );
				urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") );
				urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8"));
				urlBuilder.append("/" + URLEncoder.encode(String.valueOf(start),"UTF-8"));
				urlBuilder.append("/" + URLEncoder.encode(String.valueOf(start+999),"UTF-8"));
				
				URL url = new URL(urlBuilder.toString());
				HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
				httpConn.setRequestMethod("GET");
				httpConn.setRequestProperty("Content-type", "application/json");
				
				BufferedReader rd;
	
				if(httpConn.getResponseCode() >= 200 && httpConn.getResponseCode() <= 300) {
						rd = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
				} else {
						rd = new BufferedReader(new InputStreamReader(httpConn.getErrorStream()));
				}
				
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = rd.readLine()) != null) {
						sb.append(line);
				}
				
				rd.close();
				httpConn.disconnect();
				
				Gson gson = new Gson();
				JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);
				JsonArray rows = jsonObject.getAsJsonObject("TbPublicWifiInfo").getAsJsonArray("row");
				
				for(int j = 0; j < rows.size(); j++) {
					JsonObject row = rows.get(j).getAsJsonObject();
					
					ps.setString(1, row.get("X_SWIFI_MGR_NO").getAsString());
					ps.setString(2, row.get("X_SWIFI_WRDOFC").getAsString());
					ps.setString(3, row.get("X_SWIFI_MAIN_NM").getAsString());
					ps.setString(4, row.get("X_SWIFI_ADRES1").getAsString());
					ps.setString(5, row.get("X_SWIFI_ADRES2").getAsString());
					ps.setString(6, row.get("X_SWIFI_INSTL_FLOOR").getAsString());
					ps.setString(7, row.get("X_SWIFI_INSTL_TY").getAsString());
					ps.setString(8, row.get("X_SWIFI_INSTL_MBY").getAsString());
					ps.setString(9, row.get("X_SWIFI_SVC_SE").getAsString());
					ps.setString(10, row.get("X_SWIFI_CMCWR").getAsString());
					ps.setInt(11, row.get("X_SWIFI_CNSTC_YEAR").getAsInt());
					ps.setString(12, row.get("X_SWIFI_INOUT_DOOR").getAsString());
					ps.setString(13, row.get("X_SWIFI_REMARS3").getAsString());
					ps.setDouble(14, row.get("LAT").getAsDouble());
					ps.setDouble(15, row.get("LNT").getAsDouble());
					ps.setString(16, row.get("WORK_DTTM").getAsString());
					
					ps.addBatch();
					ps.clearParameters();
				}
				
               			ps.executeBatch();
                		ps.clearBatch();
                		conn.commit();
                
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

	public Wifi get(String MgrNumber) {
		String sql = "select * from WIFI where X_SWIFI_MGR_NO = ? ";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Wifi wifi = new Wifi();
		
		try {
			conn = DriverManager.getConnection(URL);
			ps = conn.prepareStatement(sql);
		    	ps.setString(1, MgrNumber);
		    
		   	rs = ps.executeQuery();
			
			if(!rs.next()) {
				System.out.println("데이터가 존재하지 않습니다");
				wifi = null;
			}	else {
				
				wifi.setMgrNumber(rs.getString("X_SWIFI_MGR_NO"));
				wifi.setWrdofc(rs.getString("X_SWIFI_WRDOFC"));
				wifi.setMainNumber(rs.getString("X_SWIFI_MAIN_NM"));
				wifi.setAddress1(rs.getString("X_SWIFI_ADRES1"));
				wifi.setAddress2(rs.getString("X_SWIFI_ADRES2"));
				wifi.setInstallFloor(rs.getString("X_SWIFI_INSTL_FLOOR"));
				wifi.setInstallTy(rs.getString("X_SWIFI_INSTL_TY"));
				wifi.setInstallMby(rs.getString("X_SWIFI_INSTL_MBY"));
				wifi.setSvcSe(rs.getString("X_SWIFI_SVC_SE"));
				wifi.setCmcwr(rs.getString("X_SWIFI_CMCWR"));
				wifi.setCnstcYear(rs.getInt("X_SWIFI_CNSTC_YEAR"));
				wifi.setInOutDoor(rs.getString("X_SWIFI_INOUT_DOOR"));
				wifi.setRemars3(rs.getString("X_SWIFI_REMARS3"));
				wifi.setLatitude(rs.getDouble("LAT"));
				wifi.setLongitude(rs.getDouble("LNT"));
				wifi.setWorkDttm(rs.getString("WORK_DTTM"));
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
		
		return wifi;
	}	
	
	public int update(Wifi wifi) {
		int affected = -1;
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String updateSql = "Update WIFI\n"
				+ "SET X_SWIFI_WRDOFC = ?,"
				+ "X_SWIFI_MAIN_NM = ?,"
				+ "X_SWIFI_ADRES1 = ?,"
				+ "X_SWIFI_ADRES2 = ?,"
				+ "X_SWIFI_INSTL_FLOOR = ?,"
				+ "X_SWIFI_INSTL_TY = ?,"
				+ "X_SWIFI_INSTL_MBY = ?,"
				+ "X_SWIFI_SVC_SE = ?,"
				+ "X_SWIFI_CMCWR = ?,"
				+ "X_SWIFI_CNSTC_YEAR = ?,"
				+ "X_SWIFI_INOUT_DOOR = ?,"
				+ "X_SWIFI_REMARS3 = ?,"
				+ "LAT = ?,"
				+ "LNT = ?,"
				+ "WORK_DTTM = ?\n"
				+ "WHERE X_SWIFI_MGR_NO = ?\n";

		try {
			conn = DriverManager.getConnection(URL);
			ps = conn.prepareStatement(updateSql);
			ps.setString(1, wifi.getWrdofc());
			ps.setString(2, wifi.getMainNumber());
			ps.setString(3, wifi.getAddress1());
			ps.setString(4, wifi.getAddress2());
			ps.setString(5, wifi.getInstallFloor());
			ps.setString(6, wifi.getInstallTy());
			ps.setString(7, wifi.getInstallMby());
			ps.setString(8, wifi.getSvcSe());
			ps.setString(9, wifi.getCmcwr());
			ps.setInt(10, wifi.getCnstcYear());
			ps.setString(11, wifi.getInOutDoor());
			ps.setString(12, wifi.getRemars3());
			ps.setDouble(13, wifi.getLatitude());
			ps.setDouble(14, wifi.getLongitude());
			ps.setString(15, wifi.getWorkDttm());
			ps.setString(16, wifi.getMgrNumber());
		    
			affected = ps.executeUpdate();
			
			if(affected > 0) {
				System.out.println("업데이트가 됐습니다");
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
		String sql = "SELECT * FROM sqlite_master WHERE tbl_name = 'WIFI'";
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
	
	public void clean() {
		
		int affected = -1;
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		String clean = "DELETE FROM WIFI";
		
		try {
			conn = DriverManager.getConnection(URL);
			ps = conn.prepareStatement(clean);
			
			affected = ps.executeUpdate();
			
			if(affected > 0) System.out.println("데이터를 모두 삭제했습니다");
		
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
	
	public List<Wifi> getClosestWifis(double userLat, double userLnt) {
		List<Wifi> list = new ArrayList<>();
		
		String sql = "SELECT X_SWIFI_MGR_NO, \r\n"
				+ "		ROUND(6371*ACOS(COS(RADIANS(?))*COS(RADIANS(LAT))*COS(radians(LNT)-RADIANS(?))\r\n"
				+ "     +SIN(RADIANS(?))*SIN(RADIANS(LAT))),4) AS distance\r\n"
				+ "FROM WIFI\r\n"
				+ "ORDER BY distance\r\n"
				+ "LIMIT 20;";
		
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
			ps.setDouble(1, userLat);
			ps.setDouble(2, userLnt);
			ps.setDouble(3, userLat);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Wifi wifi = get(rs.getString("X_SWIFI_MGR_NO"));
				wifi.setDistance(rs.getDouble("distance"));
				list.add(wifi);
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

}
