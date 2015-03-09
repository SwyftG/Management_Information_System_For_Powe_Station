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
					showSelectEmpDialog();
				});
				
				$("#newOrg").focus(function() {
					showSelectOrgDialog();
				});
				
				$("#tt").datagrid({
					url: "${pageContext.request.contextPath }/listAllEmpExchangeInfo.action",
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
						{field:'empName',title:'Ա������'},
						{field:'oldOrg',title:'ԭ����'},
						{field: 'newOrg', title: '�»���'},
						{field: "oldPost", title: "ԭ��λ"},
						{field: "newPost", title: "�¸�λ"},
						{field: "oldSalary", title: "ԭ�̶�н�꣨Ԫ��"},
						{field: "newSalary", title: "�¹̶�н�꣨Ԫ��"},
						{field: "exchangeNo", title: "�����ĺ�"},
						{field: "exchangeReason", title: "����ԭ��"},
						{field: "exchangeDateStr", title: "��������"}
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
								modal: true,
								iconCls: "icon-save",
								width: 800,
								height: 430,
								buttons: [{
									text: "ȷ��",
									iconCls: "icon-ok",
									handler: function() {
										editAjaxRequest("${pageContext.request.contextPath }/addEmpExchangeInfo.action", {
											empId: $("#empId").val(),
											oldOrgId: $("#oldOrgId").val(),
											newOrgId: $("#newOrgId").val(),
											oldPost: $("#oldPost").text(),
											newPost: $("#newPost").val(),
											oldSalary: $("#oldSalary").text(),
											newSalary: $("#newSalary").val(),
											exchangeNo: $("#exchangeNo").val(),
											exchangeReason: $("#exchangeReason").val(),
											newSalary: $("#newSalary").val()
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
										
										editAjaxRequest("${pageContext.request.contextPath }/removeEmpExchangeInfo.action", {
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
				$("#empName").val("");
				$("#empId").val("");
				$("#empCode").text("");
				$("#oldOrg").text("");
				$("#oldOrgId").val("");
				$("#oldPost").text("");
				$("#oldSalary").text("");
				$("#birthday").text("");
				$("#gender").text("");
				$("#age").text("");
				$("#newOrg").val("");
				$("#newOrgId").val("");
				$("#newPost").val("");
				$("#exchangeNo").val("");
				$("#exchangeReason").val("");
				$("#newSalary").val("");
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
						showErrorMessage("����Ա�����˵�ʱ���ִ���");
					},
					onClick: function(node) {
						if (0 != node.id) {
							$("#empName").val(node.text);
							$("#empId").val(node.id);
							
							loadAjaxRequest("${pageContext.request.contextPath }/loadEmployee.action", {
								id: node.id
							}, function(obj) {
								$("#empCode").text(obj.personCode);
								$("#oldOrg").text(obj.orgName);
								$("#oldOrgId").val(obj.orgId);
								$("#oldPost").text(obj.post);
								$("#oldSalary").text(obj.salary);
								$("#birthday").text(obj.birthdayStr);
								$("#gender").text(obj.gender);
								$("#age").text(obj.age);
								
								$("#selectEmpDialog").css("display", "none").dialog("close");
							});
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
					width: 200,
					height: 230,
					buttons: [{
						text: "ȡ��",
						iconCls: "icon-cancel",
						handler: function() {
							$("#selectEmpDialog").css("display", "none").dialog("close");
						}
					}]
				});
			}
			
			function showSelectOrgDialog() {
				$("#orgTree").tree({
					url: "${pageContext.request.contextPath }/buildOrgTree.action",
					animate: true,
					lines: true,
					onLoadError: function() {
						showErrorMessage("���ػ������˵�ʱ���ִ���");
					},
					onClick: function(node) {
						if (-1 != node.id) {
							$("#newOrg").val(node.text);
							$("#newOrgId").val(node.id);
							
							$("#selectOrgDialog").css("display", "none").dialog("close");
						}
					}
				});
				
				$("#selectOrgDialog").css("display", "").dialog({
					title: "ѡ�����",
					collapsible: false,
					minimizable: false,
					maximizable: false,
					resizable: false,
					modal: true,
					width: 200,
					height: 230,
					buttons: [{
						text: "ȡ��",
						iconCls: "icon-cancel",
						handler: function() {
							$("#selectOrgDialog").css("display", "none").dialog("close");
						}
					}]
				});
			}
		</script>
	</head>
	
	<body>
		<table id="tt"></table>
		
		<div id="editDialog" style="display: none;">
			<table width="90%" border="0" align="center" class="td_title" style="margin-top: 20px;">
				<tr>
					<td>
						Ա��������Ϣ
					</td>
				</tr>
			</table>
			
			<table width="90%" border="0" align="center" class="table">
				<tr>
					<td width="20%" class="td_lable">
						Ա��������
					</td>
					<td width="30%" class="td_edit">
						<input type="text" id="empName" readonly="readonly" />
						<input type="hidden" id="empId" />
					</td>
					<td width="20%" class="td_lable">
						Ա����ţ�
					</td>
					<td width="30%" class="td_edit">
						<span id="empCode"></span>
					</td>
				</tr>
				<tr>
					<td class="td_lable">
						ԭ������
					</td>
					<td colspan="3" class="td_edit">
						<span id="oldOrg"></span>
						<input type="hidden" id="oldOrgId" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="td_lable">
						ԭ��λ��
					</td>
					<td width="30%" class="td_edit">
						<span id="oldPost"></span>
					</td>
					<td width="20%" class="td_lable">
						ԭ�̶�н�꣺
					</td>
					<td width="30%" class="td_edit">
						<span id="oldSalary"></span>
					</td>
				</tr>
				<tr>
					<td class="td_lable">
						�������ڣ�
					</td>
					<td class="td_edit">
						<span id="birthday"></span>
					</td>
					<td class="td_lable">
						�Ա�
					</td>
					<td class="td_edit">
						<span id="gender"></span>
					</td>
				</tr>
				<tr>
					<td class="td_lable">
						���䣺
					</td>
					<td class="td_edit">
						<span id="age"></span>
					</td>
					<td class="td_lable">
						&nbsp;
					</td>
					<td class="td_edit">
						&nbsp;
					</td>
				</tr>
			</table>
			
			<br />
			
			<table width="90%" border="0" align="center" class="td_title">
				<tr>
					<td>
						������Ϣ
					</td>
				</tr>
			</table>
			
			<table width="90%" border="0" align="center" class="table">
				<tr>
					<td width="20%" class="td_lable">
						�»�����
					</td>
					<td width="30%" class="td_edit">
						<input type="text" id="newOrg" readonly="readonly" />
						<input type="hidden" id="newOrgId" />
					</td>
					<td width="20%" class="td_lable">
						�¸�λ��
					</td>
					<td width="30%" class="td_edit">
						<input type="text" id="newPost" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="td_lable">
						�����ĺţ�
					</td>
					<td width="30%" class="td_edit">
						<input type="text" id="exchangeNo" />
					</td>
					<td width="20%" class="td_lable">
						�¹̶�н�꣺
					</td>
					<td width="30%" class="td_edit">
						<input type="text" id="newSalary" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">
						����ԭ��
					</td>
					<td class="td_edit" colspan="3">
						<textarea rows="5" cols="60" id="exchangeReason"></textarea>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="selectEmpDialog" style="display: none;">
			<ul id="empTree"></ul>
		</div>
		
		<div id="selectOrgDialog" style="display: none;">
			<ul id="orgTree"></ul>
		</div>
	</body>
</html>
