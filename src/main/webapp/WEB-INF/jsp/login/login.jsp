<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<link href="${BASE_URL}/images/login.css" rel="stylesheet" type="text/css" />
<meta name="renderer" content="webkit" />
</head>
<body>

<div class="login_head">
	<div class="login_title"><img src="${BASE_URL}/images/goin_h.png" width="301" height="57" /></div>
</div>
<div class="login_box">
	<ul>
    	<li class="border_bj margin_top20">
            <span class="login_img"><img src="${BASE_URL}/images/goin_yh.png" /></span>
            <input id="loginName" name="loginName" type="text" placeholder="请输入用户名" class="login_input_text" />
        </li>
        <li class="border_bj margin_top20">
            <span class="login_img"><img src="${BASE_URL}/images/goin_mm.png" /></span>
            <input id="loginPwd" name="loginPwd" type="password" placeholder="请输入密码" class="login_input_text" />
        </li>
        <li>
            <span class="login_wjmm"><a href="#">忘记密码</a></span>
            <span><input name="" type="checkbox" value="" /> 记住密码</span>
        </li>
        <li>
            <span class="login_wjmm"><a href="#">验证码</a></span>
            <span><input name="" type="text" placeholder="请输入验证码" class="login_input_text"  style="width:100px;" /><img id="img" src="${BASE_URL}/login/genVerifyCode.html" /><label style="color:white;">看不清？</label></span>
        </li>
        <li>
            <span class="login_wjmm"><a href="${BASE_URL}/login/toRegister.html">立即注册</a></span>
        </li>
        <li><input name="" type="button" value="登 录" class="login_button" /></li>
    </ul>
</div>

<div class="login_fotter">江苏省泰兴虹桥工业园区管理委员会©版权所有</div>



 

<script type="text/javascript">
    function changeImg(){
        var img = document.getElementById("img");
        img.src = "${BASE_URL}/login/genVerifyCode.html?date=" + new Date();;
    }
</script>
