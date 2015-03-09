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
				$("#acquireTime").datebox({
					currentText: "����",
					closeText: "�ر�",
					formatter: formatDate
				}).datebox("calendar").calendar({
					weeks: ["��","һ","��","��","��","��","��"],
					months: ["һ��","����","����","����","����","����","����","����","����","ʮ��","ʮһ��","ʮ����"]
				});
				
				$("#empTree").tree({
					url: "${pageContext.request.contextPath }/buildEmployeeTree.action",
					animate: true,
					lines: true,
					onLoadError: function() {
						showErrorMessage("����Ա�����˵�ʱ���ִ���");
					},
					onClick: function(node) {
						if (0 != node.id) {
							$("#empId").val(node.id);
							
							showEmpSkillTable(node.id);
						} 
					}
				});
			});
			
			function clearInput() {
				$("#recordId").val("");
				$("#skillName").val("");
				$("#skillLevel").combobox("setValue", -1);
				$("#certNo").val("");
				$("#certUnit").val("");
				$("#acquireTime").datebox("setText", "");
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
			
			function showEmpSkillTable(empId) {
				$("#tt").datagrid({
					url: "${pageContext.request.contextPath }/listSelectedEmpSkill.action",
					queryParams: { empId: empId },
					title: 'Ա��ְҵ������Ϣ',
					width: 'auto',
					height: 'auto',
					autoRowHeight: false,
					striped: true,
					fitColumns: true,
					rownumbers:true,
					nowrap: false,
					idField:'id',
					columns:[[
						{field:'skillName',title:'��������'},
						{field:'skillLevelStr',title:'���ܵȼ�'},
						{field:'certNo',title:'֤����'},
						{field:'certUnit',title:'��֤��λ'},
						{field:'acquireTimeStr',title:'֤����ʱ��'}
					]],
					frozenColumns:[[
		                {field:'ck',checkbox:true}
					]],
					toolbar: [{
						text: "����",
						iconCls: "icon-add",
						handler: function() {
							clearInput();
							
							$("#editDialog").css("display", "").dialog({
								title: "����Ա��ְҵ������Ϣ",
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
										editAjaxRequest("${pageContext.request.contextPath }/addEmpSkill.action", {
											empId: $("#empId").val(),
											skillName: $("#skillName").val(),
											skillLevel: $("#skillLevel").combobox("getValue"),
											certNo: $("#certNo").val(),
											certUnit: $("#certUnit").val(),
											acquireTime: $("#acquireTime").datebox("getText")
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
					}, {
						text: "�޸�",
						iconCls: "icon-edit",
						handler: function() {
							if (checkDatagridChecked("update")) {
								var selectedRows = $("#tt").datagrid("getChecked");
								
								loadAjaxRequest("${pageContext.request.contextPath }/loadEmpSkill.action", {
									id: selectedRows[0].id
								}, function(obj) {
									$("#recordId").val(obj.id);
									$("#skillName").val(obj.skillName);
									$("#skillLevel").combobox("setValue", obj.skillLevel);
									$("#certNo").val(obj.certNo);
									$("#certUnit").val(obj.certUnit);
									$("#acquireTime").datebox("setText", obj.acquireTimeStr);
								
									$("#editDialog").css("display", "").dialog({
										title: "�޸�Ա����Ϣ",
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
												editAjaxRequest("${pageContext.request.contextPath }/modifyEmpSkill.action", {
													id: $("#recordId").val(),
													empId: $("#empId").val(),
													skillName: $("#skillName").val(),
													skillLevel: $("#skillLevel").combobox("getValue"),
													certNo: $("#certNo").val(),
													certUnit: $("#certUnit").val(),
													acquireTime: $("#acquireTime").datebox("getText")
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
					}, {
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
										
										editAjaxRequest("${pageContext.request.contextPath }/removeEmpSkill.action", {
											selectedIds: ids.join(",")
										});
									}
								});
							}
						}
					}]
				});
			}
		</script>
	</head>
	
	<body class="easyui-layout">
		<div data-options="region:'west',title:'Ա����Ϣ'" style="width:180px;">
	    	<ul id="empTree"></ul>
	    </div>
	    
	    <div data-options="region:'center'" style="width: 100%; height: 100%;">
	    	<table id="tt" width="100%"></table>
	    </div>
	    
	    <div id="editDialog" style="display: none;">
	    	<table style="margin-top: 20px; margin-bottom: 20px;" width="90%" align="center" class="table" border="0" cellspacing="1" cellpadding="3">
	    		<tbody>
					<tr>
						<td class="td_lable">�������ƣ�</td>
						<td class="td_edit">
							<input type="text" id="skillName" />
						</td>
					</tr>
					<tr>
						<td class="td_lable">���ܵȼ���</td>
						<td class="td_edit">
							<select id="skillLevel" class="easyui-combobox" style="width: 150px;" data-options="panelHeight: 'auto'">
								<option value='<s:property value="@org.eps.common.util.Constants@DEFAULT_OPTION_VALUE" />'>��ѡ��</option>
								<option value="1">һ��</option>
								<option value="2">����</option>
								<option value="3">����</option>
								<option value="4">�ļ�</option>
								<option value="5">�弶</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="td_lable">֤���ţ�</td>
						<td class="td_edit">
							<input type="text" id="certNo" />
						</td>
					</tr>
					<tr>
						<td class="td_lable">��֤��λ��</td>
						<td class="td_edit">
							<input type="text" id="certUnit" />
						</td>
					</tr>
					<tr>
						<td class="td_lable">֤�������ڣ�</td>
						<td class="td_edit">
							<input type="text" id="acquireTime" />
						</td>
					</tr>
				</tbody>
	    	</table>
	    </div>
	    
	    <input type="hidden" id="recordId" />
	    <input type="hidden" id="empId" />
	</body>
</html>
