<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Web Study</title>

    <style>
        #img-input{
            display: none;
        }
        .upload-box {
            width: 150px;
            height: 150px;
            margin: 10px;
            padding: 10px;
            border: 3px dashed skyblue;
            display: flex;
            justify-content: center;
            align-items: center;
            color: cornflowerblue;
            font-weight: 700;
            cursor: pointer;

        }
    </style>

</head>
<body>

  <h1>파일 업로드 예제</h1>

  <div class="upload-box">여기를 눌러 파일을 업로드하세요💌</div>

  <form action="/upload/file" method="post" enctype="multipart/form-data" >
<%--    <input type="file" name="thumbnail" id="img-input" multiple>--%>
    <input type="file" name="thumbnail" id="img-input" accept="image/*">
    <button type="submit">전송</button>
  </form>

    <script>
        <%-- div를 클릭하면 input 클릭되도록 --%>
        document.querySelector('.upload-box').onclick = e => {
            document.getElementById('img-input').click();
        }
    </script>

</body>
</html>