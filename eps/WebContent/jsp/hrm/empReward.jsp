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
				$("#empName").focus(function() {
					$(this).attr("disabled", true);
					
					showSelectEmpDialog();
				});
				
				$("#tt").datagrid({
					url: "${pageContext.request.contextPath }/listAllEmpRewardInfo.action",
					title: "Ա��������Ϣ�б�",
					width: 'auto',
					height: 'auto',
					autoRowHeight: false,
					striped: true,
					fitColumns: true,
					rownumbers:true,
					idField:'id',
					nowrap: false,
					columns:[[
					    {field: "empName", title: "Ա������"},
						{field:'orgName',title:'���ڻ���'},
						{field:'post',title:'��λ'},
						{field: 'rewardType', title: '��������'},
						{field: "rewardMoney", title: "���ͽ��"},
						{field: "rewardReason", title: "����ԭ��"},
						{field: "rewardTime", title: "����ʱ��"}
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
								title: "����Ա��������Ϣ",
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
										editAjaxRequest("${pageContext.request.contextPath }/addEmpRewardInfo.action", {
											rewardType: $("input[type=radio]:checked").val(),
											reason: $("#rewardReason").val(),
											rewardMoney: $("#rewardMoney").val(),
											empId: $("#empId").val()
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
								
								loadAjaxRequest("${pageContext.request.contextPath }/loadEmpRewardInfo.action", {
									id: selectedRows[0].id
								}, function(obj) {
									$("#empName").val(obj.empName).attr("disabled", true);
									$("#empId").val("");
									
									if ("����" == obj.rewardType) {
										$("input[type=radio]:eq(0)").attr("checked", true);
									} else {
										$("input[type=radio]:eq(1)").attr("checked", true);
									}
									
									$("#rewardMoney").val(obj.rewardMoney);
									$("#rewardReason").val(obj.rewardReason);
									$("#rewardId").val(obj.id);
									
									$("#editDialog").css("display", "").dialog({
										title: "�޸�Ա��������Ϣ",
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
												editAjaxRequest("${pageContext.request.contextPath }/modifyEmpRewardInfo.action", {
													rewardType: $("input[type=radio]:checked").val(),
													reason: $("#rewardReason").val(),
													rewardMoney: $("#rewardMoney").val(),
													id: $("#rewardId").val()
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
										
										editAjaxRequest("${pageContext.request.contextPath }/removeEmpRewardInfo.action", {
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
				$("#empName").val("").attr("disabled", false);
				$("#empId").val("");
				$("input[name=rewardType]:eq(0)").attr("checked", true);
				$("#rewardMoney").val("");
				$("#rewardReason").val("");
				$("#rewardId").val();
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
			
			function showSelectEmpDialog() {
				$("#empTree").tree({
					url: "${pageContext.request.contextPath }/buildEmployeeTree.action",
					animate: true,
					lines: true,
					onLoadError: function() {
						$.messager.alert("����", "����Ա����Ϣʱ��������", "error");
					},
					onClick: function(node) {
						if (0 != node.id) {
							$("#empName").val(node.text);
							$("#empId").val(node.id);
							
							$("#empName").attr("disabled", false);
							$("#selectEmpDialog").css("display", "").dialog("close");
						}
					}
				});
				
				$("#selectEmpDialog").css("display", "").dialog({
					title: "ѡ��Ա��",
					collapsible: false,
					minimizable: false,
					maximizable: false,
					resizable: false,
					modal: true,
					width: 250,
					height: 300,
					buttons: [{
						text: "ȡ��",
						iconCls: "icon-cancel",
						handler: function() {
							$("#empName").attr("disabled", false);
							$("#selectEmpDialog").css("display", "").dialog("close");
						}
					}]
				});
			}
		</script>
	</head>
	
	<body>
		<table id="tt"></table>
		
		<div id="editDialog" style="display: none;">
			<table style="margin-top: 20px;" width="90%" align="center" class="table" border="0" cellspacing="1" cellpadding="3">
				<tbody>
					<tr>
						<td class="td_lable">Ա��������</td>
						<td class="td_edit">
							<input id="empName" type="text" readonly="readonly" />
							<input id="empId" type="hidden" />
						</td>
					</tr>
					<tr>
						<td class="td_lable">�������</td>
						<td class="td_edit">
							<input type="radio" name="rewardType" value="0" checked="checked" />����
							<input type="radio" name="rewardType" value="1" />�ͷ�
						</td>
					</tr>
					<tr>
						<td class="td_lable">���ͽ�</td>
						<td class="td_edit">
							<input id="rewardMoney" type="text" />Ԫ
						</td>
					</tr>
					<tr>
						<td class="td_lable">����ԭ��</td>
						<td class="td_edit">
							<textarea id="rewardReason" rows="5" cols="50"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
			
			<input type="hidden" id="rewardId" />
		</div>
		
		<div id="selectEmpDialog" style="display: none;">
			<ul id="empTree"></ul>
		</div>
	</body>
</html>
