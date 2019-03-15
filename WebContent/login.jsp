<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>欢迎</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="robots" content="nofollow" />
<link href="resources/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" />
<script type="text/javascript">
	var logining = false;
	function showStatus(type, status) {
		document.getElementById("status").innerText = '';
		var h5 = document.createElement("h5");
		var span = document.createElement("span");
		span.className = "label label-" + type;
		span.innerText = status;
		h5.appendChild(span);
		document.getElementById("status").appendChild(h5);
	}
	function check() {
		if (!document.getElementById("username").value) {
			showStatus("danger", "用户名不能为空。");
			return false;
		}
		if (!document.getElementById("password").value) {
			showStatus("danger", "密码不能为空。");
			return false;
		}
		if (logining) {
			return false;
		} else {
			logining = true;
			showStatus("warning", "登录中，请稍后...");
			return true;
		}
	}
</script>

</head>
<body onload="document.f.username.focus();">
	<div class="container">
		<form name="f" id="f" method="post" onsubmit="return check();" class="well" style="width: 30em; margin: auto; margin-top: 150px;"
			action="<c:url value='./login'/>">
			<img src="./resources/images/logo.jpg" class="center-block img-rounded">
			<h2 class="text-center">POI属性批量编辑</h2>
			<br />
			<div class="input-group input-group-md">
				<span class="input-group-addon">
					<i class="glyphicon glyphicon-user" ></i>
				</span>
				<input type="text"
					class="form-control required" id="username" name="username"
					placeholder="请输入用户名" data-trim maxlength="35" />
			</div>
			<br />
			<div class="input-group input-group-md">
				 <span class="input-group-addon">
				 	<i class="glyphicon glyphicon-lock"></i>
				 </span>
				<input type="password"
					class="form-control required" id="password" name="password"
					placeholder="请输入密码" data-trim maxlength="35"
					onkeypress="if (event.keyCode==13) { if(check())	document.f.submit(); }" />
			</div>
			<br />
			<button type="submit" class="btn btn-default" name="login">登录</button>
			<div style="float: right;" id="status">
				<c:if test="${not empty param.login_error }">
					<c:choose>
						<c:when test="${param.login_error == 1}">
							<h5><span class="label label-danger">用户不存在，请重新输入。</span></h5>
						</c:when>
						<c:when test="${param.login_error == 2}">
							<h5><span class="label label-danger">密码错误，请重新输入。</span></h5>
						</c:when>
						<c:when test="${param.login_error == 3}">
							<h5><span class="label label-danger">重复登录，请等待30分钟后，重新登录。</span></h5>
						</c:when>
						<c:when test="${param.login_error == 4}">
							<h5><span class="label label-danger">没有权限，请联系管理员分配权限。</span></h5>
						</c:when>
						<c:otherwise>
							<h5><span class="label label-danger">操作没有成功。</span></h5>
						</c:otherwise>
					</c:choose>
					<%
						session.invalidate();
					%>
				</c:if>
				<c:if test="${not empty param.logout }">
					<c:choose>
						<c:when test="${param.logout == 1}">
							<h5><span class="label label-warning">已退出登录，如需要，请重新登录。</span></h5>
						</c:when>
						<c:when test="${param.logout == 2}">
							<h5><span class="label label-warning">登录超时，请重新登录。</span></h5>
						</c:when>
						<c:otherwise>
							<h5><span class="label label-warning">退出异常。</span></h5>
						</c:otherwise>
					</c:choose>
					<%
						session.invalidate();
					%>
				</c:if>
			</div>
		</form>
	</div>
</body>
</html>
