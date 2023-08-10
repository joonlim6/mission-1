<%@ page import="db.BookmarkManager" %>
<%@ page import="db.Bookmark" %>
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
      }
    </style>
</head>
<body>
    <h1>북마크 목록</h1>

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
    <table>
	    <thead>
			<tr>
				 <th>ID</th>
				 <th>북마크 이름</th>
				 <th>와이파이명</th>
				 <th>등록일자</th>
				 <th>비고</th>
			</tr>
	    </thead>
	    <tbody>
	    	<%
	    		BookmarkManager bmm = new BookmarkManager();
	    		bmm.createBookmarkTable();
	    		List<Bookmark> bmList = bmm.getAllBookmarks();
	    		
	    		if(bmList.size() == 0) {
	    	%>
	            <tr>
	              <td style="text-align:center;" colspan="5">북마크가 존재하지 않습니다</td>
	            </tr>	    	
	    	<%
	    		}
	    		
	    		for(Bookmark bm : bmList) {
	    			String gName = bm.getGroupName();
	    			String mNum = bm.getMainNumber();
	    			String regDate = bm.getRegisterDate();
	    	%>
	    			<tr>  	   
	    	   		  <td>
	    	   				<%=bm.getBookmarkId()%>
	    	   	      </td>
	    	   	      <td>
	    	   	      		<%=gName%>
	    	   		  </td>
	    	   		  <td>
	    	   				<%=mNum%>
	    	   		  </td>
	    	   		  <td>
	    	   				<%=regDate%>
	    	   		  </td>
	    	   		  <td>
	    	   				<a href="delete-bookmark.jsp?gName=<%=gName%>&mNum=<%=mNum%>&regDate=<%=regDate%>">삭제</a>
	    	   		  </td> 
    	   			</tr>
	    	
 		   	<%
	    		}
	    	%>
	    </tbody>
     </table>
</body>
</html>