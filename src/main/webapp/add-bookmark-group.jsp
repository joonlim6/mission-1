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
	<script>
	    function addBookmarkGroup() {
	    	var bName = document.getElementById("bookmarkName");
	    	var order = document.getElementById("order");
	    	location.href = "add-bookmark-group-2.jsp?bName="+bName+"&order="+order;
	    }    
	</script>
</head>
<body>
	<h1>북마크 그룹 추가</h1>
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
    
    <form action="add-bookmark-group-2.jsp" method="get"> 
		<table>
	        <tr>
	            <th>북마크 이름</th>
	            <td>
	                <input type="text" id="bookMarkName" name="bookmarkName">
	            </td>
	        </tr>
	        <tr>
	            <th>순서</th>
	            <td>
	                <input type="text" id="order" name="order">
	            </td>
	        </tr>
	        <tr>
	            <td colspan="2">
	                <button type="submit">추가</button>
	            </td>
	        </tr>
	    </table>
    </form>
</body>
</html>