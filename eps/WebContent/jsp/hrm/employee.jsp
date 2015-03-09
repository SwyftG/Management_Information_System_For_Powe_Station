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
				$("#birthday").datebox({
					currentText: "����",
					closeText: "�ر�",
					formatter: formatDate,
					onSelect: function(date) {
						var age = calculateAge(date);
						
						$("#age").val(age);
					}
				}).datebox("calendar").calendar({
					weeks: ["��","һ","��","��","��","��","��"],
					months: ["һ��","����","����","����","����","����","����","����","����","ʮ��","ʮһ��","ʮ����"]
				});
				
				$("#orgTree").tree({
					url: "${pageContext.request.contextPath }/buildOrgTree.action",
					animate: true,
					lines: true,
					onLoadError: function() {
						showErrorMessage("���ػ��������ִ���");
					},
					onClick: function(node) {
						$("#orgName").val(node.text);
						$("#orgId").val(node.id);
						
						$("#orgName").attr("disabled", false);
						
						$("#selectOrgDialog").css("display", "none").dialog("close");
					}
				});
				
				$("#orgName").focus(function() {
					$(this).attr("disabled", true);
					
					$("#selectOrgDialog").css("display", "").dialog({
						title: "ѡ��Ա����������",
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
								$("#orgName").attr("disabled", false);
								
								$("#selectOrgDialog").css("display", "none").dialog("close");
							}
						}]
					});
				});
				
				$("#tt").datagrid({
					url: "${pageContext.request.contextPath }/listAllEmployee.action",
					title: "Ա����Ϣ�б�",
					width: 'auto',
					height: 'auto',
					autoRowHeight: false,
					striped: true,
					fitColumns: true,
					rownumbers:true,
					idField:'id',
					nowrap: false,
					columns:[[
					    {field: "personCode", title: "Ա�����"},
						{field:'name',title:'����'},
						{field:'birthdayStr',title:'��������'},
						{field: 'age', title: '����'},
						{field: "gender", title: "�Ա�"},
						{field: "orgName", title: "���ڻ���"},
						{field: "post", title: "��λ"},
						{field: "salary", title: "�̶�н�꣨Ԫ��"}
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
								title: "����Ա����Ϣ",
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
										editAjaxRequest("${pageContext.request.contextPath }/addEmployee.action", {
											name: $("#name").val(),
											age: $("#age").val(),
											birthday: $("#birthday").datebox("getText"),
											gender: $("input[name=gender]:checked").val(),
											orgId: $("#orgId").val(),
											post: $("#post").val(),
											salary: $("#salary").val()
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
								
								loadAjaxRequest("${pageContext.request.contextPath }/loadEmployee.action", {
									id: selectedRows[0].id
								}, function(obj) {
									$("#empId").val(obj.id);
									$("#name").val(obj.name);
									$("#age").val(obj.age);
									$("#birthday").datebox("setText", obj.birthdayStr);
									$("#post").val(obj.post);
									
									if ("��" == obj.gender) {
										$("input[name=gender]:eq(0)").attr("checked", true);
									} else if ("Ů" == obj.gender) {
										$("input[name=gender]:eq(1)").attr("checked", true);
									}
									
									$("#orgId").val(obj.orgId);
									$("#orgName").val(obj.orgName);
									$("#salary").val(obj.salary);
								
									$("#editDialog").css("display", "").dialog({
										title: "�޸�Ա����Ϣ",
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
												editAjaxRequest("${pageContext.request.contextPath }/modifyEmployee.action", {
													id: $("#empId").val(),
													name: $("#name").val(),
													age: $("#age").val(),
													birthday: $("#birthday").datebox("getText"),
													gender: $("input[name=gender]:checked").val(),
													orgId: $("#orgId").val(),
													salary: $("#salary").val(),
													post: $("#post").val()
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
										
										editAjaxRequest("${pageContext.request.contextPath }/removeEmployee.action", {
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
				$("#empId").val("");
				$("#name").val("");
				$("#age").val("");
				$("#orgName").attr("disabled", false);
				$("#birthday").datebox("setText", ""),
				$("input[name=gender]:eq(0)").attr("checked", true);
				$("#orgId").val("");
				$("#salary").val("");
				$("#orgName").val("");
				$("#post").val("");
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
			
			function calculateAge(birthday) {
				var age = -1;
			 	var today = new Date();
			 	var todayYear = today.getFullYear();
			 	var todayMonth = today.getMonth() + 1;
			 	var todayDay = today.getDate();
			 	
			 	var birthdayYear = birthday.getFullYear();
			 	var birthdayMonth = birthday.getMonth();
			 	var birthdayDay = birthday.getDate();
			 	
			 	if(todayMonth * 1 - birthdayMonth * 1 < 0) {
			 		age = (todayYear * 1 - birthdayYear * 1) - 1;
		        } else {
	               if(todayDay - birthdayDay >= 0) {
                      age = (todayYear * 1 - birthdayYear * 1);
	               } else {
	                  age = (todayYear * 1 - birthdayYear * 1) - 1;
	               }
		        }
			 	
			 	return age;
			}
		</script>
	</head>
	
	<body>
		<table id="tt"></table>
		
		<div id="editDialog" style="display: none;">
			<table style="margin-top: 20px; margin-bottom: 20px;" width="90%" align="center" class="table" border="0" cellspacing="1" cellpadding="3">
				<tr>
					<td class="td_lable">������</td>
					<td class="td_edit">
						<input type="text" id="name" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">�������ڣ�</td>
					<td class="td_edit">
						<input type="text" id="birthday" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">���䣺</td>
					<td class="td_edit">
						<input type="text" id="age" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">�Ա�</td>
					<td class="td_edit">
						<input type="radio" name="gender" value="��" checked="checked" />��
						<input type="radio" name="gender" value="Ů" />Ů
					</td>
				</tr>
				<tr>
					<td class="td_lable">������</td>
					<td class="td_edit">
						<input type="text" id="orgName" />
						<input type="hidden" id="orgId" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">��λ��</td>
					<td class="td_edit">
						<input type="text" id="post" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">нˮ��</td>
					<td class="td_edit">
						<input type="text" id="salary" />Ԫ
					</td>
				</tr>
			</table>
		</div>
		
		<input type="hidden" id="empId" />
		
		<div id="selectOrgDialog" style="display: none;">
			<ul id="orgTree"></ul>
		</div>
	</body>
</html>
