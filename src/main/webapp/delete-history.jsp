<%@ page import="db.HistoryManager" %>
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
	    Integer id = Integer.parseInt(request.getParameter("id"));
	
	    HistoryManager hm = new HistoryManager();
	%>
	
	<h1><%=hm.deleteById(id)%>번 히스토리를 성공적으로 삭제했습니다</h1>
    <a href="index.jsp">홈으로 가기</a>
    |
	<a href="history.jsp">히스토리로 가기</a>
</body>
</html>