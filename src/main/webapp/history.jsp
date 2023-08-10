<%@ page import="db.HistoryManager" %>
<%@ page import="db.History" %>
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
	<script>
	    function deleteHistory(id) {
	    	location.href = "delete-history.jsp?id="+id;
	    }    
	</script>	
</head>
<body>
	<h1>위치 히스토리 목록</h1>

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
          <th>X좌표</th>
          <th>Y좌표</th>
          <th>조회일자</th>
          <th>비고</th>
        </tr>
      </thead>
      <tbody>
	      <%
	        HistoryManager hm = new HistoryManager();
	      	hm.createTable();
	        if (hm.count() == 0) {
	   	  %>
	            <tr>
	              <td style="text-align:center;" colspan="17">히스토리가 존재하지 않습니다</td>
	            </tr>
           <%
            }	else {
            	List<History> historyList = hm.getAllHistories();
            	
            	for(History hs : historyList) {
            		int hsId = hs.getId();
           %>	
	               <tr>
				        <td>
				            <%=hsId%>
				        </td>
				        <td>
				            <%=hs.getLatitude()%>
				        </td>
				        <td>
				            <%=hs.getLongitude()%>
				        </td>
				        <td>
				            <%=hs.getViewDate()%>
				        </td>
				        <td>
				            <button style="margin: 0 auto; display: block;" onclick="deleteHistory(<%=hsId%>)">삭제</button>
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