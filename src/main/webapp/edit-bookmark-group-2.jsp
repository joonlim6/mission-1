<%@ page import="db.BookmarkGroupManager" %>
<%@ page import="db.BookmarkGroup" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<style>* { text-align: center;}</style>
</head>
<body>
	<%	
	 	request.setCharacterEncoding("UTF-8");
		Integer groupId = Integer.parseInt(request.getParameter("groupId"));
		String newName = request.getParameter("newName");
		Integer newOrder = Integer.parseInt(request.getParameter("newOrder"));
		BookmarkGroupManager bgm = new BookmarkGroupManager();
		
		int updated = bgm.updateBookmarkGroup(groupId, newName, newOrder);
		
		if(updated == 1) {
	%>
		<h1>북마크 <%=groupId%>번을 성공적으로 수정했습니다</h1>
    <%
		}	else {
    %>
    	<h1>북마크 <%=groupId%>번을 수정하지 못했습니다</h1>
    <%
		}
    %>
    <a href="index.jsp">홈으로 가기</a>
    |
	<a href="bookmark-group.jsp">북마크 그룹 관리로 가기</a>
</body>
</html>