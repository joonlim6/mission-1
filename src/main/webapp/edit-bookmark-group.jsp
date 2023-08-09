<%@ page import="db.BookmarkGroupManager" %>
<%@ page import="db.BookmarkGroup" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
    <style>
      table {
        width: 100%;
      }
      th,
      td {
      	text-align: center;
        border: solid 1px #000;
      }
    </style>
</head>
<body>
	<%
		Integer id = Integer.parseInt(request.getParameter("id"));
		BookmarkGroupManager bgm = new BookmarkGroupManager();
		BookmarkGroup bmg = bgm.getBmg(id);
		String oldName = bmg.getGroupName();
		int oldOrder = bmg.getGroupOrder();
		int groupId = bmg.getGroupId();
		
	%>
	<h1>북마크 그룹 수정</h1>
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
    
    <form action="edit-bookmark-group-2.jsp" method="post">
		<table>
	        <tr>
	            <th>북마크 이름</th>
	            <td>
	                <input type="text" id="newName" name="newName" value="<%=oldName%>">
	            </td>
	        </tr>
	        <tr>
	            <th>순서</th>
	            <td>
	                <input type="text" id="newOrder" name="newOrder" value="<%=oldOrder%>">
	            </td>
	        </tr>
	        <tr>
	            <td colspan="2">
	            	<a href="bookmark-group.jsp">돌아가기</a>
                    <input type="submit" value="수정">
                	<input type="hidden" name="groupId" value="<%=groupId%>">
	            </td>
	        </tr>
	    </table>
    </form>
</body>
</html>