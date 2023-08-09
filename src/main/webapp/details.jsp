<%@ page import="db.WifiManager" %>
<%@ page import="db.Wifi" %>
<%@ page import="db.BookmarkGroupManager" %>
<%@ page import="db.BookmarkGroup" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
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
      <a href="bookmark-list.jsp">북마크 보기</a>
      |
      <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    </div>
    <br/>
    
    <%	
    	String mgrNumber = request.getParameter("mgrNumber");
   	 	String distance = request.getParameter("distance");
    	
    	WifiManager wm = new WifiManager();
    	Wifi wifi = wm.get(mgrNumber);
    	BookmarkGroupManager bgm = new BookmarkGroupManager();
    	bgm.createBookmarkGroupTable();
    	List<BookmarkGroup> bgList = bgm.getBookmarkGroups();
    %>
    
    <form action="add-bookmark.jsp?mgrNumber=<%=mgrNumber%>" method="post">
        <select name="groupId">
            <option value="북마크 그룹 이름 선택" selected="selected" disabled>북마크 그룹 이름 선택</option>
            <%
                for (BookmarkGroup bg : bgList) {
            %>
		            <option value="<%= bg.getGroupId()%>"><%= bg.getGroupName()%></option>
            <%
                }
            %>
        </select>

        <input type="submit" value="북마크 추가하기">
    </form>
    
   	<table>
	    <tr>
	        <th>거리(Km)</th>
	        <td><%=distance%></td>
	    </tr>
        <tr>
	        <th>관리번호</th>
	        <td><%= wifi.getMgrNumber()%></td>
	    </tr>
        <tr>
	        <th>자치구</th>
	        <td><%= wifi.getWrdofc()%></td>
	    </tr>
        <tr>
	        <th>와이파이명</th>
	   		<td>
	            <a href="details.jsp?mgrNumber=<%=wifi.getMgrNumber()%>&distance=<%=distance%>">
	                <%=wifi.getMainNumber()%>
	            </a>
	 	    </td>
	    </tr>
        <tr>
	        <th>도로명주소</th>
	        <td><%= wifi.getAddress1()%></td>
	    </tr>
        <tr>
	        <th>상세주소</th>
	        <td><%= wifi.getAddress2()%></td>
	    </tr>
        <tr>
	        <th>설치위치(층)</th>
	        <td><%= wifi.getInstallFloor()%></td>
	    </tr>
        <tr>
	        <th>설치유형</th>
	        <td><%= wifi.getInstallTy()%></td>
	    </tr>
        <tr>
	        <th>설치기관</th>
	        <td><%= wifi.getInstallMby()%></td>
	    </tr>
        <tr>
	        <th>서비스구분</th>
	        <td><%= wifi.getSvcSe()%></td>
	    </tr>	    
        <tr>
	        <th>망종류</th>
	        <td><%= wifi.getCmcwr()%></td>
	    </tr>	
        <tr>
	        <th>설치년도</th>
	        <td><%= wifi.getCnstcYear()%></td>
	    </tr>
        <tr>
	        <th>실내외구분</th>
	        <td><%= wifi.getInOutDoor()%></td>
	    </tr>
        <tr>
	        <th>와이파이접속환경</th>
	        <td><%= wifi.getRemars3()%></td>
	    </tr>
        <tr>
	        <th>X좌표</th>
	        <td><%= wifi.getLatitude()%></td>
	    </tr>
        <tr>
	        <th>Y좌표</th>
	        <td><%= wifi.getLongitude()%></td>
	    </tr>
        <tr>
	        <th>작업일자</th>
	        <td><%= wifi.getWorkDttm()%></td>
	    </tr>
   	</table>    
</body>
</html>