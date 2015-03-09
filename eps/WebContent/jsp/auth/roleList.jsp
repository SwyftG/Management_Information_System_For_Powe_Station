<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
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
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/ajax.js"></script>
		<script type="text/javascript">
			$(function() {
				$("#tt").datagrid({
					url: "${pageContext.request.contextPath }/listAllRole.action",
					title: "����豸�б�",
					width: 'auto',
					height: 'auto',
					autoRowHeight: false,
					striped: true,
					fitColumns: true,
					rownumbers:true,
					nowrap: false,
					columns:[[
						{field:'roleName',title:'��ɫ����'},
						{field:'description',title:'��ɫ����'},
						{field: "id", title: "����", formatter: function(value) {
							var html = "<img alt='' onclick='javascript:showRoleSettingDialog(" + value + ");' src='${pageContext.request.contextPath }/css/icons/pencil.png' title='����' style='cursor: hand;' />";
							
							return html;
						}, align: "center"}
					]]
				});
			});
			
			function showRoleSettingDialog(roleId) {
				$("#systemMenuTree").tree({
					url: "${pageContext.request.contextPath }/buildSystemMenuTree.action",
					animate: true,
					checkbox: true,
					lines: true,
					onLoadSuccess: function(node, data) {
						loadAjaxRequest("${pageContext.request.contextPath }/loadRole.action", {
							roleId: roleId
						}, function(obj) {
							$("#roleId").val(obj.id);
							
							var length = obj.menus.length;
							
							if (0 != length) {
								for (var i = 0; i < length; i++) {
									var menu = obj.menus[i];
									
									var n = $("#systemMenuTree").tree("find", menu.id);
									
									if ($("#systemMenuTree").tree("isLeaf", n.target)) {
										$("#systemMenuTree").tree("check", n.target);
									}
								}
							} 
						});
					}
				});
				
				$("#systemMenuDialog").css("display", "block").dialog({
					title: "ϵͳ�˵�",
					collapsible: false,
					minimizable: false,
					maximizable: false,
					resizable: false,
					iconCls: "icon-logo",
					modal: true,
					width: 320,
					height: 400,
					buttons: [{
						text: "ȷ��",
						iconCls: "icon-ok",
						handler: function() {
							var selectedNodes = $("#systemMenuTree").tree("getChecked");
							
							var len = selectedNodes.length;
							
							if (0 == len) {
								showWarningMessage("��ѡ��Ҫ��Ȩ�Ĳ˵���");
								
								return;
							}
							
							var nodeIds = [];
							
							for (var i = 0; i < len; i++) {
								var selectedNode = selectedNodes[i];
								
								nodeIds.push(selectedNode.id);
							}
							
							$.post("${pageContext.request.contextPath }/settingRoleAuth.action", {
								roleId: $("#roleId").val(),
								menuIds: nodeIds.join(",")
							}, function(returnData) {
								if (returnData) {
									if ("success" == returnData.status) {
										showInformationMessage("�����ɹ���");
										
										$('#systemMenuDialog').css("display", "none").dialog('close');
										
										$("#tt").datagrid("reload");
									} else {
										showErrorMessage(returnData.message);
									}
								}
							});
						}
					}, {
						text: "ȡ��",
						iconCls: "icon-cancel",
						handler: function() {
							$("#roleId").val("");
							
							$("#systemMenuDialog").css("display", "none").dialog("close");
						}
					}]
				});
			}
		</script>
	</head>
	
	<body>
		<table id="tt"></table>
		
		<div id="systemMenuDialog" style="display: none;">
			<ul id="systemMenuTree"></ul>
		</div>
		
		<input type="hidden" id="roleId" />
	</body>
</html>
