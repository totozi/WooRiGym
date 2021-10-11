
<!-- 헤더 CSS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/template_header.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- 부트스트랩 CDN -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="woorigym.user.model.vo.UserTable"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login page</title>
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
               
                #log-box{
                	position: relative;
                	left: 500px;
                	top: -240px;
                }
            </style>
        </head>

        <body>
            <!-- 공통헤더 템플릿 -->
            <%@ include file="template_header.jsp" %>

                <div class="container">
                    <div id="myCarousel" class="carousel slide" data-ride="carousel">
                        <!-- Indicators -->
                        <ol class="carousel-indicators">
                            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                            <li data-target="#myCarousel" data-slide-to="1"></li>
                            <li data-target="#myCarousel" data-slide-to="2"></li>
                        </ol>

                        <div class="container">
                            <form id= "log-box" method="post" action="loginAction">
                                <h4 id="head">로그인</h4>

                                <div class="form-group">
                                    <input type="text" placeholder="아이디" name="user_id" maxlength="20"><br>
                                </div>
                                <div class="form-group">
                                    <input type="password" placeholder="비밀번호" name="user_pwd" maxlength="20"><br>
                                </div>
                                <input type="submit" value="로그인">
                                
                            </form>
                                <div id="login-menu">
                                    <ul>
                                        <li><a href="#">아이디</a></li>
                                        <li><a href="#">비밀번호 찾기</a></li>
                                        <li><a href="#">회원가입</a></li>
                                    </ul>
                                </div>

                        </div>
                    </div>
                </div>
        </body>

        </html>