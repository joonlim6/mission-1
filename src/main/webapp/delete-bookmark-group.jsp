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
		String groupName = request.getParameter("name");
		BookmarkGroupManager bgm = new BookmarkGroupManager();
		int result = bgm.deleteBookmarkGroup(groupName);
		
		if(result == 1) {
	%>
		<h1>북마크<%=groupName%>을 성공적으로 삭제했습니다</h1>
    <%
		}	else {
    %>
    	<h1>북마크<%=groupName%>를 삭제하지 못했습니다</h1>
    <%
		}
    %>
    <a href="index.jsp">홈으로 가기</a>
    |
	<a href="bookmark-group.jsp">북마크 그룹 관리로 가기</a>
</body>
</html>