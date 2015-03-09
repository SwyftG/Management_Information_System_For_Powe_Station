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
		<script type='text/javascript' src='${pageContext.request.contextPath }/dwr/interface/FileUploadService.js'></script>
  		<script type='text/javascript' src='${pageContext.request.contextPath }/dwr/engine.js'></script>
  		<script type='text/javascript' src='${pageContext.request.contextPath }/dwr/util.js'></script>
		<script type="text/javascript">
			$(function() {
				$("#accidentTime").datebox({
					currentText: "����",
					closeText: "�ر�",
					formatter: formatDate
				}).datebox("calendar").calendar({
					weeks: ["��","һ","��","��","��","��","��"],
					months: ["һ��","����","����","����","����","����","����","����","����","ʮ��","ʮһ��","ʮ����"]
				});
				
				$("#tt").datagrid({
					url: "${pageContext.request.contextPath }/listAllAccidentInfo.action",
					title: "���������б�",
					width: 'auto',
					height: 'auto',
					autoRowHeight: false,
					striped: true,
					fitColumns: true,
					rownumbers:true,
					idField:'id',
					nowrap: false,
					columns:[[
						{field:'accidentTitle',title:'�¹ʱ���'},
						{field:'accidentContent',title:'�¹�����'},
						{field: 'accidentSolution', title: '�������'},
						{field: 'accidentTimeStr', title: '����ʱ��'}
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
								title: "�����¹���Ϣ",
								collapsible: false,
								minimizable: false,
								maximizable: false,
								resizable: false,
								iconCls: "icon-save",
								modal: true,
								width: 800,
								height: 380,
								buttons: [{
									text: "ȷ��",
									iconCls: "icon-ok",
									handler: function() {
										editAjaxRequest("${pageContext.request.contextPath }/addAccidentInfo.action", {
											accidentTitle: $("#accidentTitle").val(),
											accidentContent: $("#accidentContent").val(),
											accidentSolution: $("#accidentSolution").val(),
											accidentTime: $("#accidentTime").datebox("getText")
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
								
								loadAjaxRequest("${pageContext.request.contextPath }/loadAccidentInfo.action", {
									id: selectedRows[0].id
								}, function(obj) {
									$("#accidentInfoId").val(obj.id);
									$("#accidentTitle").val(obj.accidentTitle);
									$("#accidentContent").val(obj.accidentContent);
									$("#accidentTime").datebox("setText", obj.accidentTimeStr);
									$("#accidentSolution").val(obj.accidentSolution);
								
									$("#editDialog").css("display", "").dialog({
										title: "�޸��¹���Ϣ",
										collapsible: false,
										minimizable: false,
										maximizable: false,
										resizable: false,
										iconCls: "icon-save",
										modal: true,
										width: 800,
										height: 380,
										buttons: [{
											text: "ȷ��",
											iconCls: "icon-ok",
											handler: function() {
												editAjaxRequest("${pageContext.request.contextPath }/modifyAccidentInfo.action", {
													id: $("#accidentInfoId").val(),
													accidentTitle: $("#accidentTitle").val(),
													accidentContent: $("#accidentContent").val(),
													accidentTime: $("#accidentTime").datebox("getText"),
													accidentSolution: $("#accidentSolution").val()
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
										
										editAjaxRequest("${pageContext.request.contextPath }/removeAccidentInfo.action", {
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
				$("#accidentInfoId").val("");
				$("#accidentTitle").val("");
				$("#accidentContent").val("");
				$("#accidentTime").datebox("setText", "");
				$("#accidentSolution").val("");
			}
			
			function cancelEdit() {
				$("#tt").datagrid("uncheckAll");
				
				$("#editDialog").css("display", "none").dialog("close");
			}
			
			function formatDate(date) {
				var y = date.getFullYear();
				var m = date.getMonth() + 1;
				var d = date.getDate();
				
				if (m < 10) {
					m = "0" + m;
				}
				
				if (d < 10) {
					d = "0" + d;
				}

				return y + "-" + m + "-" + d;
			}
		</script>
	</head>
	
	<body>
		<table id="tt"></table>
		
		<div id="editDialog" style="display: none;">
			<table style="margin-top: 20px; margin-bottom: 20px;" width="90%" align="center" class="table" border="0" cellspacing="1" cellpadding="3">
				<tr>
					<td class="td_lable">�¹ʱ��⣺</td>
					<td class="td_edit">
						<input type="text" id="accidentTitle" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">�¹����ݣ�</td>
					<td class="td_edit">
						<textarea rows="5" cols="60" id="accidentContent"></textarea>
					</td>
				</tr>
				<tr>
					<td class="td_lable">����ʱ�䣺</td>
					<td class="td_edit">
						<input type="text" id="accidentTime" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">���������</td>
					<td class="td_edit">
						<textarea rows="5" cols="60" id="accidentSolution"></textarea>
					</td>
				</tr>
			</table>
		</div>
		
		<input type="hidden" id="accidentInfoId" />
	</body>
</html>
