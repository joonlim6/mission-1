<%@ page import="db.WifiManager" %>
<%@ page import="db.Wifi" %>
<%@ page import="db.HistoryManager" %>
<%@ page import="db.History" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>와이파이 정보 구하기</title>
    <style>
      table {
        width: 100%;
      }
      th,
      td {
        border: solid 1px #000;
      }
    </style>
	<script>
		function success({ coords, timestamp }) {
	        const latitude = coords.latitude;
	        const longitude = coords.longitude;
	        
	        document.getElementById("lat").value = latitude;
	        document.getElementById("lnt").value = longitude;
	    }
	
	    function getUserLocation() {
	        if (!navigator.geolocation) {
	            throw "현재 위치 정보가 지원되지 않습니다.";
	        }
	        navigator.geolocation.getCurrentPosition(success);
	    }
	    
	    
	</script>
  </head>
  <body>
  
    <h1>와이파이 정보 구하기</h1>

    <div>
      <a href="index.jsp">홈</a>
      |
      <a href="history.jsp">위치 히스토리 목록</a>
      |
      <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
      |
      <a href="bookmark.jsp">북마크 보기</a>
      |
      <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    </div>
    <br/>

    <form action="index.jsp" method="GET">
      <label for="lat">LAT:</label>
      <input type="text" id="lat" name="lat" value="${lat}">
      <label for="lnt">, LNT:</label>      
      <input type="text" id="lnt" name="lnt" value="${lnt}">
      <input type="button" value="내 위치 가져오기" onclick="getUserLocation()">
      <button>근처 WIFI 정보 보기</button>
    </form>
    <br />


    <table>
      <thead>
        <tr>
          <th>거리(Km)</th>
          <th>관리번호</th>
          <th>자치구</th>
          <th>와이파이명</th>
          <th>도로명주소</th>
          <th>상세주소</th>
          <th>설치위치(층)</th>
          <th>설치유형</th>
          <th>설치기관</th>
          <th>서비스구분</th>
          <th>망종류</th>
          <th>설치년도</th>
          <th>실내외구분</th>
          <th>WIFI접속환경</th>
          <th>X좌표</th>
          <th>Y좌표</th>
          <th>작업일자</th>
        </tr>
      </thead>
      <tbody>
      
		<%
	        String latitude = request.getParameter("lat");
	        String longitude = request.getParameter("lnt");
	        double lat = 0.0;
	        double lnt = 0.0;
	        
	        if(latitude != null && longitude != null) {
	        	lat = Double.parseDouble(latitude);
	        	lnt = Double.parseDouble(longitude);
	        }
	        
	        if (lat == 0.0 && lnt == 0.0) {
           %>
	            <tr>
	              <td style="text-align:center;" colspan="17">위치 정보를 입력한 후에 조회해 주세요.</td>
	            </tr>
           <%
            } else {
            	
            	HistoryManager hm = new HistoryManager();
            	hm.createTable();
            	hm.addHistory(lat, lnt);            	
            	
            	
		    	WifiManager wm = new WifiManager();
		    	List<Wifi> wifiList = wm.getClosestWifis(lat, lnt);
			
		    	for(Wifi wifi : wifiList) {
    	   %> 
    	   			<tr>  	   
	    	   		  <td>
	    	   				<%=wifi.getDistance()%>
	    	   	      </td>
	    	   		  <td>
	    	   				<%=wifi.getMgrNumber()%>
	    	   		  </td>
	    	   		  <td>
	    	   				<%=wifi.getWrdofc()%>
	    	   		  </td>    
	    	   		  <td>
				            <a href="details.jsp?mgrNumber=<%=wifi.getMgrNumber()%>&distance=<%=wifi.getDistance()%>">
				                <%=wifi.getMainNumber()%>
				            </a>
	    	   		  </td>
	    	   		  <td>
	    	   				<%=wifi.getAddress1()%>
	    	   		  </td>
	    	   		  <td>
	    	   				<%=wifi.getAddress2()%>
	    	   		  </td>  
	    	   		  <td>
	    	   				<%=wifi.getInstallFloor()%>
	    	   		  </td>
	    	   		  <td>
	    	   				<%=wifi.getInstallTy()%>
	    	   		  </td>
	    	   		  <td>
	    	   				<%=wifi.getInstallMby()%>
	    	   		  </td>  
	    	   		  <td>
	    	   				<%=wifi.getSvcSe()%>
	    	   	      </td>
	    	   		  <td>
	    	   				<%=wifi.getCmcwr()%>
	    	   		  </td>
	    	   		  <td>
	    	   				<%=wifi.getCnstcYear()%>
	    	   		  </td>
	    	   		  <td>
	    	   				<%=wifi.getInOutDoor()%>
	    	   		  </td>
	    	   		  <td>
	    	   				<%=wifi.getRemars3()%>
	    	   		  </td>
	    	   		  <td>
	    	   				<%=wifi.getLatitude()%>
	    	   		  </td>
	    	   		  <td>
	    	   				<%=wifi.getLongitude()%>
	    	   		  </td>
	    	   		  <td>
	    	   				<%=wifi.getWorkDttm()%>
	    	   		  </td> 
    	   			</tr>	   			
		   <%
		        }
		    }
		   %>		    			
      </tbody>
    </table>
  </body>
</html>
