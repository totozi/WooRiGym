<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import = "woorigym.user.model.vo.UserTable" %>
<%@page import="java.util.ArrayList"%>
  <%
  String user_id = (String)request.getAttribute("user_id");
  %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>우리짐 마이페이지 메인</title>
    <!-- 부트스트랩 CDN -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <style>
        /* 폰트 */
        /* @import url('https://fonts.googleapis.com/css2?family=Gothic+A1:wght@200&family=Nanum+Gothic&family=Noto+Sans+KR:wght@100&display=swap'); */
        @import url('https://fonts.googleapis.com/css2?family=Gothic+A1:wght@200&family=Nanum+Gothic&family=Noto+Sans+KR:wght@100&display=swap');
    </style>
    <style>
        /* reset */
        * {
            margin: 0;
            padding: 0;
        }

        /* header */
        header {
            width: 1200px;
            /* margin: 0 auto; */
            /* margin-right: 0;; */
            height: 160px;
            position: relative;
            padding: 30px;
        }

        a {
            color: #BDBDBD;
            text-decoration: none;
        }

        a:link {
            text-decoration: none;
        }

        a:hover {
            color: #333;
            text-decoration: none;
        }

        ul {
            list-style-type: none;
            position: relative;
        }

        #nav.container {
            text-align: center;
            padding: 15px;
            width: 1200px;
            height: 62px;
        }

        #nav li {
            display: inline-block;
            padding: 0px 15px;
        }

        /* 마우스 오버 시 하위메뉴 노출 */
        #nav .dropdown:hover .dropdown-menu {
            display: inline-block;
            /* margin: 0; */
            /* width: 100%; */
        }


        /* 상단바 가로 정렬 */
        .dropdown {
            display: inline-block;
            position: relative;
            /* top: 10px; */
        }

        /* 상단바 테두리 없애기 */
        .btn {
            border: 0px;
            padding: 10;
        }
       
         /* id가 있으면=로그인 상태=마이페이지 접근가능
         
         /* 로그인 전 후 화면 다름 시작 */
        #main_tnb {
            position: absolute;
            top: 10px;
            bottom: 10px;
            right: 30px;
            margin: 10px;
            overflow: hidden;
            width: 500px;
            height: 100px;
        }

        #main_tnb>ul>li {
            display: inline-block;
            padding: 5px;
            /* border: 1px solid black; */
        }

	<% if(user_id != null) %>
	
        /* OOO님 | 로그아웃 | 마이페이지 | 장바구니 | 최근본상품 */
        #main_tnb li::after {
            padding-left: 10px;
            content: "|";
        }
        #main_tnb li:last-child::after {
            padding-left: 10px;
            content: "";
        }
         /* 로그인 전 후 화면 다름  끝*/

        #search_icon a {
            position: absolute;
            top: 10px;
            right: 50px;
            margin: 15px;
            width: 25px;
            height: 25px;
        }
    </style>
    <style>
        /* content */
        section {
            width: 900px;
            padding: 30px 0 30px 0;
            position: relative;
            bottom: 170px;
            left: 300px;
        }
        aside {
            padding: 30px 0 0 30px;
        }
        #side-menu>ul>li{
            padding: 5px;
        }
        /* 마이페이지 폰트 크게 */
        #side-menu>ul>li:first-child{
            font-size: 25px;
        }

        .coupon td {
            padding: 0 100px 5px 0;
        }
        #order_info tr:first-child>td {
            font-size: 50px;
            padding: 20px;
        }
    </style>
</head>

<body>
<!-- 공통헤더 템플릿 -->
 	<%@ include file="template_header.jsp"%>
<aside>
    <div id="side-menu">
        <ul>
            <li><a href="#">마이페이지</a></li>
            <li><a href="#">주문/배송조회</a></li>
            <li><a href="#">취소/교환/반품</a></li>
            <li><a href="#">상품 후기</a></li>
            <li><a href="#">쿠폰 관리</a></li>
            <li><a href="#">상품 문의(Q&A)</a></li>
        </ul>
    </div>
</aside>
<section>
    <h1>ooo님 즐거운 쇼핑 되세요!</h1>
    <a href="#">회원정보 수정 ></a><hr>
<table class="coupon">
    <tr>
        <td colspan="2"><a href="#">쿠폰</a></td>
        <td colspan="2"><a href="#">적립금</a></td>
    </tr>
    <tr>
        <td>보유 쿠폰</td>
        <td>0장</td>
        <td>보유 적립금</td>
        <td>0P</td>
    </tr>
    <tr>
        <td>소멸 예정 쿠폰</td>
        <td>0장</td>
        <td>소멸 예정 적립금</td>
        <td>0P</td>
    </tr>
</table>
<hr>
<p>주문/배송</p>
<table id="order_info">
    <tr>
        <!-- 주문/배송조회에서 갯수 체크해서 불러오기 -->
        <!-- 아래 0은 시범용(삭제예정) -->
        <td>0</td>
        <td>0</td>
        <td>0</td>
        <td>0</td>
    </tr>
    <tr>
        <td>주문완료</td>
        <td>배송준비중</td>
        <td>배송중</td>
        <td>배송완료</td>
    </tr>
</table>

</section>

<footer>

</footer>
</body>

</html>