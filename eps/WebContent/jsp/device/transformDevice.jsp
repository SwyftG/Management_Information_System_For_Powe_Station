<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

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
				$("#tt").datagrid({
					url: "${pageContext.request.contextPath }/listDevices.action",
					queryParams: { deviceType: $("#deviceType").val() },
					title: "����豸�б�",
					width: 'auto',
					height: 'auto',
					autoRowHeight: false,
					striped: true,
					fitColumns: true,
					rownumbers:true,
					idField:'id',
					nowrap: false,
					columns:[[
						{field:'deviceName',title:'�豸����'},
						{field:'deviceNo',title:'�豸���'},
						{field:'deviceMoney',title:'�豸�۸�'},
						{field: 'deviceDesc', title: '�豸����'},
						{field:'createTimeStr',title:'¼��ʱ��'}
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
								title: "��������豸",
								collapsible: false,
								minimizable: false,
								maximizable: false,
								resizable: false,
								iconCls: "icon-save",
								modal: true,
								width: 800,
								height: 330,
								buttons: [{
									text: "ȷ��",
									iconCls: "icon-ok",
									handler: function() {
										editAjaxRequest("${pageContext.request.contextPath }/createDevice.action", {
											deviceName: $("#deviceName").val(),
											deviceNo: $("#deviceNo").val(),
											deviceMoney: $("#deviceMoney").val(),
											deviceType: $("#deviceType").val(),
											deviceDesc: $("#deviceDesc").val()
										});
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
								
								loadAjaxRequest("${pageContext.request.contextPath }/loadDeviceInfo.action", {
									id: selectedRows[0].id
								}, function(obj) {
									$("#deviceId").val(obj.id);
									$("#deviceName").val(obj.deviceName);
									$("#deviceNo").val(obj.deviceNo);
									$("#deviceMoney").val(obj.deviceMoney);
									$("#deviceDesc").val(obj.deviceDesc);
									
									$("#editDialog").css("display", "").dialog({
										title: "�޸ı���豸",
										collapsible: false,
										minimizable: false,
										maximizable: false,
										resizable: false,
										iconCls: "icon-save",
										modal: true,
										width: 800,
										height: 300,
										buttons: [{
											text: "ȷ��",
											iconCls: "icon-ok",
											handler: function() {
												editAjaxRequest("${pageContext.request.contextPath }/modifyDeivice.action", {
													id: $("#deviceId").val(),
													deviceName: $("#deviceName").val(),
													deviceNo: $("#deviceNo").val(),
													deviceMoney: $("#deviceMoney").val(),
													deviceDesc: $("#deviceDesc").val()
												});
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
										
										editAjaxRequest("${pageContext.request.contextPath }/removeDevice.action", {
											selectedIds: ids.join(",")
										});
									}
								});
							}
						}
					}]
				});
			});
			
			function clearInput() {
				$("#deviceId").val("");
				$("#deviceName").val("");
				$("#deviceNo").val("");
				$("#deviceMoney").val("");
				$("#deviceDesc").val("");
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
					<td class="td_lable">�豸���ƣ�</td>
					<td class="td_edit">
						<input type="text" id="deviceName" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">�豸��ţ�</td>
					<td class="td_edit">
						<input type="text" id="deviceNo" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">�豸�۸�</td>
					<td class="td_edit">
						<input type="text" id="deviceMoney" />Ԫ
					</td>
				</tr>
				<tr>
					<td class="td_lable">�豸���ܣ�</td>
					<td class="td_edit">
						<textarea rows="5" cols="60" id="deviceDesc"></textarea>
					</td>
				</tr>
			</table>
		</div>
		
		<input type="hidden" id="deviceId" />
		<input type="hidden" id="deviceType" value='<s:property value="@org.eps.common.util.Constants@DEVICE_TYPE_TRANSFORM" />' />
	</body>
</html>
