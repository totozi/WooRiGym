<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/template_header.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="woorigym.user.model.vo.UserTable"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
<style>
#head {
	text-align: center;
}

#login-btn {
	text-emphasis-color: gray;
	background-color: black;
}

#login-menu {
	text-align: center;
}

#login-menu>ul {
	list-style-type: none;
	position: relative;
}

#login-menu li {
	display: inline-block;
}

/* 아이디 | 비밀번호 찾기 | 회원가입 */
#login-menu li::after {
	padding: 10px;
	content: "|";
}

#login-menu li:last-child::after {
    padding: 15px;
    content: "";
}
.form-group{
	text-align:center;
}

input{
border: 2px solid #e7e7e7;
}
input,
.btn {
  padding: 12px;
  border-radius: 4px;
  margin: 5px 0;
  opacity: 0.85;
  display: inline-block;
  font-size: 17px;
  line-height: 20px;
  text-decoration: none; /* remove underline from anchors */
}
input:hover,
.btn:hover {
  opacity: 1;
}
</style>
</head>
<body>
<script>
	
</script>
            <!-- 공통헤더 템플릿 -->
            <%@ include file="template_header.jsp" %>

                <div class="container">
                    <div id="myCarousel" class="carousel slide" data-ride="carousel">

                        <div class="container">
                            <form id= "log-box" method="post" action="findId">
                                <h3 id="head">아이디 찾기</h3>
                                <div class="form-group">
                                   	  <input type="text" placeholder="이름" name="user_name"><br>
                                </div>
                                <div class="form-group">
                                       <input type="text" placeholder="ex)010-1111-2222" name="phone" maxlength="20"><br>
                                </div>
                                <div class="form-group">
                                <input type="submit" value="아이디 찾기">
                                </div>
                            </form>
                                <div id="login-menu">
                                    <ul>
                                        <li><a href="findId">아이디 찾기</a></li>
                                        <li><a href="findPwd">비밀번호 찾기</a></li>
                                        <li><a href="join">회원가입</a></li>
                                    </ul>
                                </div>

                        </div>
                    </div>
                </div>
        </body>

        </html>