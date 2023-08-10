<%@ page import="db.BookmarkManager" %>
<%@ page import="db.Bookmark" %>
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
	    String groupName = request.getParameter("gName");
		String mainNumber = request.getParameter("mNum");
		String registerDate = request.getParameter("regDate");
	
	    BookmarkManager bmm = new BookmarkManager();
	    int result = bmm.deleteBookmark(groupName, mainNumber, registerDate);
	    
	    if(result == 1) {
	%>
		<h1>북마크를 성공적으로 삭제했습니다</h1>
	<%
	    }	else {
	%>
		<h1>북마크를 삭제하지 못했습니다</h1>
	<%
	    }
	%>
    <a href="index.jsp">홈으로 가기</a>
    |
    <a href="bookmark-list.jsp">북마크 목록으로 가기</a>
</body>
</html>