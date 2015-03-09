<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

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
				$("#btnLogout").click(function() {
					showConfirmMessage("确认要退出系统么？", function(r) {
						if (r) {
							var url = "${pageContext.request.contextPath }/j_spring_security_logout";

							location.href = url;
						}
					});
				});
				
				$("#userSystemMenuTree").tree({
					url: "${pageContext.request.contextPath }/buildSystemMenuTree.action",
					animate: true,
					lines: true,
					onLoadError: function() {
						showErrorMessage("加载系统菜单列表出现错误！");
					},
					onClick: function(node) {
						if ("" != node.attributes["url"]) {
							var url = "${pageContext.request.contextPath }" + node.attributes["url"];
							var text = node.text;

							if($("#centerTab").tabs("exists", text)) {
								$("#centerTab").tabs("select", text);
							} else {
								$("#centerTab").tabs("add", {
									cache: false,
									title: text,
									closable: true,
									content: '<iframe src="' + url + '" width="100%" height="100%" scrolling="auto" frameborder="0"></iframe>'
								});
							}
						}
					}
				});
			});
		</script>
	</head>
	
	<body class="easyui-layout">
		<div data-options="region:'north'" style="margin: 0px; height: 70px; top: 0; left: 0; width: 100%;">
	 		<br />
	 			<security:authentication property="principal.username" />，您好，欢迎登录系统。
	 		<br />
	 		<a href="#" id="btnLogout">用户注销</a>
	 	</div> 
	 	
	 	<div data-options="region:'west',title:'系统功能列表'" style="width:200px;">
	    	<ul id="userSystemMenuTree"></ul>
	    </div>
	    
	    <div data-options="region:'center'" style="width: 100%; height: 100%;">
	    	<div id="centerTab" class="easyui-tabs" data-options="border:false, fit:true">
   				
			</div>
	    </div>
	</body>
</html>
