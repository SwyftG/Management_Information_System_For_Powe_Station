<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>用户登录</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link href="${pageContext.request.contextPath }/css/easyui.css" type="text/css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath }/css/icon.css" type="text/css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath }/css/style.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/messages.js"></script>
		<script type="text/javascript">
			$(function() {
				var msg = "${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message }";
				
				if ("" != msg) {
					showErrorMessage(msg);
				}
				
				$("#btnLogin").click(function() {
					if ("" == $("#username").val()) {
						showWarningMessage("用户名不能为空！");
						
						return;
					}
					
					if ("" == $("#password").val()) {
						showWarningMessage("密码不能为空！");
						
						return;
					}
					
					$("form:first").submit();
				});
			});
		</script>
	</head>
	
	<body>
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<form action="${pageContext.request.contextPath }/j_spring_security_check" method="post">
			<center>
				<fieldset style="width: 300px;">
					<legend>用户登录</legend>
					<table style="margin-top: 20px; margin-bottom: 20px;" width="90%" align="center" class="table" border="0" cellspacing="1" cellpadding="3">
						<tbody>
							<tr>
								<td class="td_lable">用户名：</td>
								<td class="td_edit">
									<input id="username" name="j_username" type="text" style="width: 150px;" />
								</td>
							</tr>
							<tr>
								<td class="td_lable">密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
								<td class="td_edit">
									<input id="password" name="j_password" type="password" style="width: 150px;" />
								</td>
							</tr>
							<tr>
								<td class="td_edit" colspan="2" align="center">
									<a id="btnLogin" href="#" class="easyui-linkbutton">登录</a>
								</td>
							</tr>
						</tbody>
					</table>
				</fieldset>
			</center>
		</form>
	</body>
</html>
