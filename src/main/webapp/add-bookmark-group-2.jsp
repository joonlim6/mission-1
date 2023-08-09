<%@ page import="db.BookmarkGroupManager" %>
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
	    String groupName = request.getParameter("bookmarkName");
		Integer order = Integer.parseInt(request.getParameter("order"));
	
		BookmarkGroupManager bgm = new BookmarkGroupManager();
		bgm.addBookMarkGroup(order, groupName);
	%>
	
	<h1>"<%=groupName%>" 북마크 그룹을 성공적으로 등록했습니다</h1>
    <a href="index.jsp">홈으로 가기</a>
    |
	<a href="bookmark-group.jsp">북마크 그룹 관리로 가기</a>
</body>
</html>