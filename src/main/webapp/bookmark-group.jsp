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
    <style>
      table {
        width: 100%;
      }
      th,
      td {
        border: solid 1px #000;
        text-align: center;
      }
    </style>
</head>
<body>
	<h1>북마크 그룹</h1>
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
	<input type="button" value="북마크 그룹 이름 추가" onclick="window.location='add-bookmark-group.jsp'" />
	<table>
	    <thead>
	    <tr>
	        <th>ID</th>
	        <th>북마크 이름</th>
	        <th>순서</th>
	        <th>등록일자</th>
	        <th>수정일자</th>
	        <th>비고</th>
	    </tr>
	    </thead>
	    <tbody>
	    <%
	        BookmarkGroupManager bgm = new BookmarkGroupManager();
	    	bgm.createBookmarkGroupTable();
	    	List<BookmarkGroup> bmgList = bgm.getBookmarkGroups();
	        if (bmgList.size() == 0) {
	    %>
			    <tr>
			        <td colspan="6" style="text-align:center;">
			            북마크 그룹이 존재하지 않습니다
			        </td>
			    </tr>
	    <%
	    	}	else {
		        for (BookmarkGroup bmg : bmgList) {
		        	String groupName = bmg.getGroupName();
	    %>
			    <tr>
			        <td>
			            <%=bmg.getGroupId()%>
			        </td>
			        <td>
			            <%=groupName%>
			        </td>
			        <td>
			            <%=bmg.getGroupOrder()%>
			        </td>
			        <td>
			            <%=bmg.getRegisterDate()%>
			        </td>
			        <td>
			            <%=bmg.getLastEdit() == null ? "" : bmg.getLastEdit()%>
			        </td>
			        <td>
			            <a href="edit-bookmark-group.jsp?id=<%=bmg.getGroupId()%>">
			                수정
			            </a>
			            <a href="delete-bookmark-group.jsp?name=<%=groupName%>">
			                삭제
			            </a>
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