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
				$("#empName").focus(function() {
					showSelectEmpDialog();
				});
				
				$("#tt").datagrid({
					url: "${pageContext.request.contextPath }/listAllEmpEvaluate.action",
					title: "员工职称评定信息列表",
					width: 'auto',
					height: 'auto',
					autoRowHeight: false,
					striped: true,
					fitColumns: true,
					rownumbers:true,
					idField:'id',
					nowrap: false,
					columns:[[
						{field:'empName',title:'员工姓名'},
						{field:'evaluateLevel',title:'评价等级', formatter: function(value) {
							if (1 == value) {
								return "高";
							} else if (2 == value) {
								return "中";
							} else if (3 == value) {
								return "低";
							}
						}},
						{field: 'evaluateYear', title: '评价年度'}
					]],
					frozenColumns:[[
		                {field:'ck', checkbox:true}
					]],
					toolbar: [{
						text: "新增",
						iconCls: "icon-add",
						handler: function() {
							clearInput();
							
							$("#editDialog").css("display", "").dialog({
								title: "新增员工职称评定信息",
								collapsible: false,
								minimizable: false,
								maximizable: false,
								resizable: false,
								modal: true,
								iconCls: "icon-save",
								width: 600,
								height: 230,
								buttons: [{
									text: "确定",
									iconCls: "icon-ok",
									handler: function() {
										editAjaxRequest("${pageContext.request.contextPath }/addEmpEvaluate.action", {
											empId: $("#empId").val(),
											evaluateLevel: $("#evaluateLevel").combobox("getValue"),
											evaluateYear: $("#evaluateYear").combobox("getValue")
										});
									}
								}, {
									text: "取消",
									iconCls: "icon-cancel",
									handler: function() {
										cancelEdit();
									}
								}]
							});
						}
					}, '-', {
						text: "删除",
						iconCls: "icon-remove",
						handler: function() {
							if (checkDatagridChecked()) {
								showConfirmMessage("确认要删除所选记录么？", function(r) {
									if (r) {
										var selectedRows = $("#tt").datagrid("getChecked");
										
										var ids = [];
										
										for (var i = 0; i < selectedRows.length; i++) {
											ids.push(selectedRows[i].id);
										}
										
										editAjaxRequest("${pageContext.request.contextPath }/removeEmpEvaluate.action", {
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
				$("#recordId").val("");
				$("#empName").val("");
				$("#empId").val("");
				$("#evaluateLevel").combobox("setValue", -1);
				$("#evaluateYear").combobox("setValue", -1);
			}
			
			function cancelEdit() {
				$("#tt").datagrid("uncheckAll");
				
				$("#editDialog").css("display", "none").dialog("close");
			}
			
			function showSelectEmpDialog() {
				$("#empTree").tree({
					url: "${pageContext.request.contextPath }/buildEmployeeTree.action",
					animate: true,
					lines: true,
					onLoadError: function() {
						showErrorMessage("加载员工树菜单时出现错误！");
					},
					onClick: function(node) {
						if (0 != node.id) {
							$("#empName").val(node.text);
							$("#empId").val(node.id);
							
							$("#selectEmpDialog").css("display", "none").dialog("close");
						}
					}
				});
				
				$("#selectEmpDialog").css("display", "").dialog({
					title: "选择员工",
					collapsible: false,
					minimizable: false,
					maximizable: false,
					resizable: false,
					modal: true,
					width: 200,
					height: 230,
					buttons: [{
						text: "取消",
						iconCls: "icon-cancel",
						handler: function() {
							$("#selectEmpDialog").css("display", "none").dialog("close");
						}
					}]
				});
			}
		</script>
	</head>
	
	<body>
		<table id="tt"></table>
		
		<div id="editDialog" style="display: none;">
			<table style="margin-top: 20px; margin-bottom: 20px;" width="90%" align="center" class="table" border="0" cellspacing="1" cellpadding="3">
				<tr>
					<td class="td_lable">员工姓名：</td>
					<td class="td_edit">
						<input type="text" id="empName" />
						<input type="hidden" id="empId" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">等级：</td>
					<td class="td_edit">
						<select id="evaluateLevel" class="easyui-combobox" style="width: 150px;" data-options="panelHeight: 'auto'">
							<option value='<s:property value="@org.eps.common.util.Constants@DEFAULT_OPTION_VALUE" />'>请选择</option>
							<option value="1">高</option>
							<option value="2">中</option>
							<option value="3">低</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td_lable">年度：</td>
					<td class="td_edit">
						<select id="evaluateYear" class="easyui-combobox" style="width: 150px;" data-options="panelHeight: 'auto'">
							<option value='<s:property value="@org.eps.common.util.Constants@DEFAULT_OPTION_VALUE" />'>请选择</option>
							<option value="2009">2009</option>
							<option value="2010">2010</option>
							<option value="2011">2011</option>
							<option value="2012">2012</option>
							<option value="2013">2013</option>
						</select>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="selectEmpDialog" style="display: none;">
			<ul id="empTree"></ul>
		</div>
	</body>
</html>
