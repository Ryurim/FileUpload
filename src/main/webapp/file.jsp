<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces = "true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<script>
	function frmOk(frm) {
		if (frm.title.value == "") {
			alert("제목을 입력하세요.");
			frm.title.focus();
			return false;
		}
		if (frm.in_file.value == "") {
			alert("첨부파일을 선택하세요.");
			frm.in_file.focus();
			return false;
		}
	}
</script>

	<h1>파일 업로드</h1>
	<form name="frmFile" id="frmFile" 
	method="post" enctype="multipart/form-data" 
	action="/file/upload"
	onsubmit="return frmOk(this);"
	>
	<!--  파일 업로드는 반드시 post! enctype이 멀티파트 폼 데이터여야 함!-->
		<div>
			<span>제목 : <input type="text" name="title" id="title" maxlength="20"></span>
		</div>
		<div>
			<span>첨부파일 : <input type="file" name="in_file" id="in_file" ></span>
		</div>
		<div>
			<input type="submit" value="전송">
		</div>
	</form>
</body>
</html>