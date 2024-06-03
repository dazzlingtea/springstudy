<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Web Study</title>
</head>
<body>
  <h1>접근 권한이 없습니다.</h1>
  <a href="/">홈으로 이동</a>
  <a href="${ref}" id="go-before">이전 페이지로 이동</a>
  <script>
    const msg = '${msg}';
    if(msg === 'authorization') {
      alert("권한이 없습니다!")
    }
    document.getElementById('go-before')
    const ref = history.go(-2);

  </script>
</body>
</html>