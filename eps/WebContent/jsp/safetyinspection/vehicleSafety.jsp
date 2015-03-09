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
				$("#tt").datagrid({
					url: "${pageContext.request.contextPath }/listAllVehicleSafety.action",
					title: "������ȫ����б�",
					width: 'auto',
					height: 'auto',
					autoRowHeight: false,
					striped: true,
					fitColumns: true,
					rownumbers:true,
					idField:'id',
					nowrap: false,
					columns:[[
						{field:'inspectionItem',title:'�����Ŀ'},
						{field:'inspectionContent',title:'�������'},
						{field:'qualifiedFlag',title:'�Ƿ�ϸ�', formatter: function(value) {
							if (1 == value) {
								return "��";
							} else {
								return "��";
							}
						}},
						{field: 'correctiveAction', title: '�Ľ���ʩ'},
						{field:'remark',title:'��ע'},
						{field: 'inspectionDateStr', title: '���ʱ��'}
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
								title: "����������ȫ�������",
								collapsible: false,
								minimizable: false,
								maximizable: false,
								resizable: false,
								iconCls: "icon-save",
								modal: true,
								width: 800,
								height: 450,
								buttons: [{
									text: "ȷ��",
									iconCls: "icon-ok",
									handler: function() {
										editAjaxRequest("${pageContext.request.contextPath }/addVehicleSafety.action", {
											inspectionItem: $("#inspectionItem").val(),
											inspectionContent: $("#inspectionContent").val(),
											qualifiedFlag: $("input[name=qualifiedFlag]:checked").val(),
											correctiveAction: $("#correctiveAction").val(),
											remark: $("#remark").val()
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
								
								loadAjaxRequest("${pageContext.request.contextPath }/loadVehicleSafety.action", {
									id: selectedRows[0].id
								}, function(obj) {
									$("#vehicleSafetyId").val(obj.id);
									$("#inspectionItem").val(obj.inspectionItem);
									$("#inspectionContent").val(obj.inspectionContent);
									
									if (1 == obj.qualifiedFlag) {
										$("input[name=qualifiedFlag]:eq(0)").attr("checked", true);
									} else {
										$("input[name=qualifiedFlag]:eq(1)").attr("checked", true);
									}
									
									$("#correctiveAction").val(obj.correctiveAction);
									$("#remark").val(obj.remark);
									
									$("#editDialog").css("display", "").dialog({
										title: "�޸ĳ�����ȫ�������",
										collapsible: false,
										minimizable: false,
										maximizable: false,
										resizable: false,
										iconCls: "icon-save",
										modal: true,
										width: 800,
										height: 450,
										buttons: [{
											text: "ȷ��",
											iconCls: "icon-ok",
											handler: function() {
												editAjaxRequest("${pageContext.request.contextPath }/modifyVehicleSafety.action", {
													id: $("#vehicleSafetyId").val(),
													inspectionItem: $("#inspectionItem").val(),
													inspectionContent: $("#inspectionContent").val(),
													qualifiedFlag: $("input[name=qualifiedFlag]:checked").val(),
													correctiveAction: $("#correctiveAction").val(),
													remark: $("#remark").val()
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
										
										editAjaxRequest("${pageContext.request.contextPath }/removeVehicleSafety.action", {
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
				$("#vehicleSafetyId").val("");
				$("#inspectionItem").val("");
				$("#inspectionContent").val("");
				$("input[name=qualifiedFlag]:eq(0)").attr("checked", true);
				$("#correctiveAction").val("");
				$("#remark").val("");
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
					<td class="td_lable">�����Ŀ��</td>
					<td class="td_edit">
						<input type="text" id="inspectionItem" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">������ݣ�</td>
					<td class="td_edit">
						<textarea rows="5" cols="60" id="inspectionContent"></textarea>
					</td>
				</tr>
				<tr>
					<td class="td_lable">�Ƿ�ϸ�</td>
					<td class="td_edit">
						<input name="qualifiedFlag" type="radio" value="1" checked="checked" />��
						<input name="qualifiedFlag" type="radio" value="0" />��
					</td>
				</tr>
				<tr>
					<td class="td_lable">�Ľ���ʩ��</td>
					<td class="td_edit">
						<textarea rows="5" cols="60" id="correctiveAction"></textarea>
					</td>
				</tr>
				<tr>
					<td class="td_lable">��ע��</td>
					<td class="td_edit">
						<textarea rows="5" cols="60" id="remark"></textarea>
					</td>
				</tr>
			</table>
		</div>
		
		<input type="hidden" id="vehicleSafetyId" />
	</body>
</html>
