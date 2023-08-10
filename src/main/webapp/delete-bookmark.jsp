<%@ page import="db.BookmarkManager" %>
<%@ page import="db.Bookmark" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
</head>
<body>
	<h1>북마크 삭제</h1>
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
    <p>북마크를 삭제하시겠습니까?</p>
    
    <% 
	    String groupName = request.getParameter("gName");
		String mainNumber = request.getParameter("mNum");
		String registerDate = request.getParameter("regDate");    	
    %>
    
    <form action="delete-bookmark-2.jsp?gName=<%=groupName%>&mNum=<%=mainNumber%>&regDate=<%=registerDate%>" method="post">
		<table>
	        <tr>
	            <th>북마크 이름</th>
	            <td>
	                <%=groupName%>
	            </td>
	        </tr>
	        <tr>
	            <th>와이파이명</th>
	            <td>
	                <%=mainNumber%>
	            </td>
	        </tr>
	        <tr>
	            <th>등록일자</th>
	            <td>
	                <%=registerDate%>
	            </td>
	        </tr>
	        <tr>
	            <td colspan="2">
	            	<a href="bookmark-list.jsp">돌아가기</a>
	            	|
	                <input type="submit" value="삭제">
	            </td>
	        </tr>
	    </table>
    </form>
</body>
</html>