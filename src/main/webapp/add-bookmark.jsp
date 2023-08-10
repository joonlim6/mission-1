<%@ page import="db.BookmarkGroupManager" %>
<%@ page import="db.BookmarkGroup" %>
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
	    request.setCharacterEncoding("UTF-8");
	    String groupName = request.getParameter("groupName");
	    String mainNumber = request.getParameter("mainNumber");
	    
	    BookmarkManager bm = new BookmarkManager();
	    bm.createBookmarkTable();
	    int result = bm.addBookmark(groupName, mainNumber);
	    
	    if(result == 1) {
	%>
		<h1>북마크가 성공적으로 추가되었습니다</h1>
	<%
		}	else {
	%>
		<h1>북마크를 추가하지 못했습니다</h1>
	<%
		}
	%>
    <a href="index.jsp">홈으로 가기</a>
    |
	<a href="bookmark-list.jsp">북마크 목록</a>
</body>
</html>