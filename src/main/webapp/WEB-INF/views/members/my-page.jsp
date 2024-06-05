<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>스프링 마이페이지</title>
    <%@ include file="../include/static-head.jsp" %>
    <link rel="stylesheet" href="/assets/css/member.css" />
</head>
<body>
<%@ include file="../include/header.jsp" %>
<div class="container wrap">
  <div class="row">
    <div class="offset-md-2 col-md-4">
      <div class="card">
        <div class="card-header text-white">
          <h2><span class="text-gray">MVC</span> 마이페이지</h2>
        </div>
        <div class="card-body">
          <form
                  action="/members/my-page"
                  name="mypage"
                  id="myPageForm"
                  method="post"
                  enctype="multipart/form-data"
          >
            <div class="profile">
              <div class="thumbnail-box">
                <img src="${login.profile != null ? login.profile : "/assets/img/anonymous.jpg"}" alt="프로필 썸네일">
              </div>

              <label>프로필 이미지 변경</label>

              <input
                      type="file"
                      id="profile-img"
                      accept="image/*"
                      style="display: none;"
                      name="profileImage"
              >
            </div>

            <table class="table">
              <tr>
                <td class="text-left">
                  <p>
                    <strong>아이디</strong
                    >&nbsp;&nbsp;&nbsp;
                    <span id="idChk"></span>
                  </p>
                </td>
              </tr>
              <tr>
                <td>
                  <input
                          type="text"
                          name="account"
                          id="user_id"
                          class="form-control tooltipstered"
                          maxlength="14"
                          required="required"
                          aria-required="true"
<%--                          placeholder="숫자와 영어로 4-14자"--%>
                          value="${login.account}"
                          readonly
                  />
                </td>
              </tr>
<%--              <tr>--%>
<%--                <td class="text-left">--%>
<%--                  <p>--%>
<%--                    <strong>기존 비밀번호를 입력해주세요.</strong--%>
<%--                    >&nbsp;&nbsp;&nbsp;<span id="pwChk"></span>--%>
<%--                  </p>--%>
<%--                </td>--%>
<%--              </tr>--%>
<%--              <tr>--%>
<%--                <td>--%>
<%--                  <input--%>
<%--                          type="password"--%>
<%--                          size="17"--%>
<%--                          maxlength="20"--%>
<%--                          id="password"--%>
<%--                          name="password"--%>
<%--                          class="form-control tooltipstered"--%>
<%--                          required="required"--%>
<%--                          aria-required="true"--%>
<%--                          placeholder="영문과 특수문자를 포함한 최소 8자"--%>
<%--                  />--%>
<%--                </td>--%>
<%--              </tr>--%>
<%--              <tr>--%>
<%--                <td class="text-left">--%>
<%--                  <p>--%>
<%--                    <strong>비밀번호를 재확인해주세요.</strong--%>
<%--                    >&nbsp;&nbsp;&nbsp;<span id="pwChk2"></span>--%>
<%--                  </p>--%>
<%--                </td>--%>
<%--              </tr>--%>
<%--              <tr>--%>
<%--                <td>--%>
<%--                  <input--%>
<%--                          type="password"--%>
<%--                          size="17"--%>
<%--                          maxlength="20"--%>
<%--                          id="password_check"--%>
<%--                          name="pw_check"--%>
<%--                          class="form-control tooltipstered"--%>
<%--                          required="required"--%>
<%--                          aria-required="true"--%>
<%--                          placeholder="비밀번호가 일치해야합니다."--%>
<%--                  />--%>
<%--                </td>--%>
<%--              </tr>--%>
              <tr>
                <td class="text-left">
                  <p>
                    <strong>이름</strong
                    >&nbsp;&nbsp;&nbsp;<span id="nameChk"></span>
                  </p>
                </td>
              </tr>
              <tr>
                <td>
                  <input
                          type="text"
                          name="name"
                          id="user_name"
                          class="form-control tooltipstered"
                          maxlength="6"
                          required="required"
                          aria-required="true"
<%--                          placeholder="한글로 최대 6자"--%>
                          value="${login.nickName}"
                  />
                </td>
              </tr>
              <tr>
                <td class="text-left">
                  <p>
                    <strong>이메일</strong
                    >&nbsp;&nbsp;&nbsp;<span id="emailChk"></span>
                  </p>
                </td>
              </tr>
              <tr>
                <td>
                  <input
                          type="email"
                          name="email"
                          id="user_email"
                          class="form-control tooltipstered"
                          required="required"
                          aria-required="true"
<%--                          placeholder="ex) abc@mvc.com"--%>
                          value="${login.email}"
                  />
                </td>
              </tr>
              <tr>
                <td class="text-center">
                  <p>
<%--                    <strong--%>
<%--                    >회원가입하셔서 더 많은 서비스를 사용하세요~~!</strong--%>
<%--                    >--%>
                  </p>
                </td>
              </tr>
              <tr>
                <td class="text-center" colspan="2">
                  <input
                          type="button"
                          value="개인정보 수정"
                          class="btn form-control tooltipstered"
                          id="mypage-btn"
                  />
                </td>
              </tr>
            </table>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<script>
  <%--  프로필 사진 관련 스크립트  --%>
  // 프로필 사진 동그라미 썸네일 부분
  const $profile = document.querySelector('.profile');
  // 실제 프로필 사진이 첨부될 input
  const $fileInput = document.getElementById('profile-img');

  $profile.addEventListener('click', e => {
    $fileInput.click();
  });

  // 프로필 사진 선택시 썸네일 보여주기
  $fileInput.addEventListener('change', e => {
    console.log('file changed!');
    // 사용자가 첨부한 파일 데이터 읽기
    const fileData = $fileInput.files[0];
    // console.log(fileData);

    // 첨부파일 이미지의 로우데이터(바이트)를 읽는 객체 생성
    const reader = new FileReader();

    // 파일의 데이터를 읽어서 img태그에 src속성에 넣기 위해
    // 파일을 URL형태로 변경
    reader.readAsDataURL(fileData);

    // 첨부파일이 등록되는 순간 img태그에 이미지를 세팅
    reader.onloadend = e => {
      const $img = document.querySelector('.thumbnail-box img');
      $img.src = reader.result;
    };

  });
</script>
<script>
  const form = document.getElementById('myPageForm');
  const myButton = document.getElementById('mypage-btn');

  myButton.addEventListener('click', e => {
    console.log(form.submit().value)
    form.submit();
    console.log("마이페이지 폼 서버로 제출")
  })

</script>
</body>
</html>