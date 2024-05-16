<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <style>
        label {
            display: block;
        }
    </style>
</head>
<body>

<h1>프론트컨트롤러V5 회원가입 양식</h1>

<form action="/chap02/v5/save" method="post">
    <label>
        # 계정명: <input type="text" name="account">
    </label>
    <label>
        # 비밀번호: <input type="password" name="password">
    </label>
    <label>
        # 이름: <input type="text" name="userName">
    </label>
    <label>
        <button type="submit">회원가입</button>
    </label>
</form>

</body>
</html>