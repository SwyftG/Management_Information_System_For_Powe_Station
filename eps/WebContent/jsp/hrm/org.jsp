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
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/check.js"></script>
		<script type="text/javascript">
			$(function() {
				$("#orgTree").tree({
					url: "${pageContext.request.contextPath }/buildOrgTree.action",
					animate: true,
					lines: true,
					onLoadError: function() {
						showErrorMessage("���ػ��������ִ���");
					},
					onClick: function(node) {
						if ("" != $("#orgId").val()) {
							if (node.id == $("#orgId").val()) {
								showWarningMessage("�ϼ�����ѡ����Ч��");
								
								return;
							}
						}
						
						$("#parentOrgName").val(node.text);
						$("#parentId").val(node.id);
						
						$("#parentOrgName").attr("disabled", false);
						
						$("#selectParentOrgDialog").css("display", "none").dialog("close");
					}
				});
				
				$("#parentOrgName").focus(function() {
					$(this).attr("disabled", true);
					
					$("#selectParentOrgDialog").css("display", "").dialog({
						title: "ѡ���ϼ�����",
						collapsible: false,
						minimizable: false,
						maximizable: false,
						resizable: false,
						modal: true,
						width: 300,
						height: 230,
						buttons: [{
							text: "ȡ��",
							iconCls: "icon-cancel",
							handler: function() {
								$("#parentOrgName").attr("disabled", false);
								
								$("#selectParentOrgDialog").css("display", "none").dialog("close");
							}
						}]
					});
				});
				
				$("#tt").datagrid({
					url: "${pageContext.request.contextPath }/listAllOrg.action",
					title: "�����б�",
					width: 'auto',
					height: 'auto',
					autoRowHeight: false,
					striped: true,
					fitColumns: true,
					rownumbers:true,
					idField:'id',
					nowrap: false,
					columns:[[
						{field:'orgName',title:'��������'},
						{field:'parentOrgName',title:'�ϼ�����'},
						{field: 'orgDesc', title: '��������'}
					]],
					frozenColumns:[[
		                {field:'ck', checkbox:true}
					]],
					toolbar: [{
						text: "����",
						iconCls: "icon-add",
						handler: function() {
							clearInput();
							
							$("#editDialog").css("display", "").dialog({
								title: "��������",
								collapsible: false,
								minimizable: false,
								maximizable: false,
								resizable: false,
								iconCls: "icon-save",
								modal: true,
								width: 800,
								height: 280,
								buttons: [{
									text: "ȷ��",
									iconCls: "icon-ok",
									handler: function() {
										editAjaxRequestRefreshTree("${pageContext.request.contextPath }/addOrg.action", {
											orgName: $("#orgName").val(),
											orgDesc: $("#orgDesc").val(),
											parentId: $("#parentId").val()
										}, "orgTree");
									}
								}, {
									text: "ȡ��",
									iconCls: "icon-cancel",
									handler: function() {
										cancelEdit();
									}
								}]
							});
						}
					}, '-', {
						text: "�޸�",
						iconCls: "icon-edit",
						handler: function() {
							if (checkDatagridChecked("update")) {
								var selectedRows = $("#tt").datagrid("getChecked");
								
								loadAjaxRequest("${pageContext.request.contextPath }/loadOrg.action", {
									id: selectedRows[0].id
								}, function(obj) {
									$("#orgId").val(obj.id);
									$("#orgName").val(obj.orgName);
									$("#orgDesc").val(obj.orgDesc);
									$("#parentId").val(obj.parentId);
									$("#parentOrgName").val(obj.parentOrgName);
								
									$("#editDialog").css("display", "").dialog({
										title: "�޸Ļ���",
										collapsible: false,
										minimizable: false,
										maximizable: false,
										resizable: false,
										iconCls: "icon-save",
										modal: true,
										width: 800,
										height: 280,
										buttons: [{
											text: "ȷ��",
											iconCls: "icon-ok",
											handler: function() {
												editAjaxRequestRefreshTree("${pageContext.request.contextPath }/modifyOrg.action", {
													id: $("#orgId").val(),
													orgName: $("#orgName").val(),
													orgDesc: $("#orgDesc").val(),
													parentId: $("#parentId").val()
												}, "orgTree");
											}
										}, {
											text: "ȡ��",
											iconCls: "icon-cancel",
											handler: function() {
												cancelEdit();
											}
										}]
									});
								});
							}
						}
					}, '-', {
						text: "ɾ��",
						iconCls: "icon-remove",
						handler: function() {
							if (checkDatagridChecked()) {
								showConfirmMessage("ȷ��Ҫɾ����ѡ��¼ô��", function(r) {
									if (r) {
										var selectedRows = $("#tt").datagrid("getChecked");
										
										var ids = [];
										
										for (var i = 0; i < selectedRows.length; i++) {
											ids.push(selectedRows[i].id);
										}
										
										editAjaxRequestRefreshTree("${pageContext.request.contextPath }/removeOrg.action", {
											selectedIds: ids.join(",")
										}, "orgTree");
									}
								});
							}
						}
					}]
				});
			});
			
			function clearInput() {
				$("#orgId").val("");
				$("#orgName").val("");
				$("#parentOrgName").val("");
				$("#parentId").val("");
				$("#orgDesc").val("");
			}
			
			function cancelEdit() {
				$("#tt").datagrid("uncheckAll");
				
				$("#editDialog").css("display", "none").dialog("close");
			}
		</script>
	</head>
	
	<body>
		<table id="tt"></table>
		
		<div id="editDialog" style="display: none;">
			<table style="margin-top: 20px; margin-bottom: 20px;" width="90%" align="center" class="table" border="0" cellspacing="1" cellpadding="3">
				<tr>
					<td class="td_lable">�������ƣ�</td>
					<td class="td_edit">
						<input type="text" id="orgName" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">�ϼ�������</td>
					<td class="td_edit">
						<input type="text" id="parentOrgName" />
						<input type="hidden" id="parentId" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">����������</td>
					<td class="td_edit">
						<textarea rows="5" cols="60" id="orgDesc"></textarea>
					</td>
				</tr>
			</table>
		</div>
		
		<input type="hidden" id="orgId" />
		
		<div id="selectParentOrgDialog" style="display: none;">
			<ul id="orgTree"></ul>
		</div>
	</body>
</html>
