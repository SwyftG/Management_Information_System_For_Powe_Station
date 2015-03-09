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
					title: "员工奖惩信息列表",
					width: 'auto',
					height: 'auto',
					autoRowHeight: false,
					striped: true,
					fitColumns: true,
					rownumbers:true,
					idField:'id',
					nowrap: false,
					columns:[[
					    {field: "empName", title: "员工姓名"},
						{field:'orgName',title:'所在机构'},
						{field:'post',title:'岗位'},
						{field: 'rewardType', title: '奖惩类型'},
						{field: "rewardMoney", title: "奖惩金额"},
						{field: "rewardReason", title: "奖惩原因"},
						{field: "rewardTime", title: "奖惩时间"}
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
								title: "新增员工奖惩信息",
								collapsible: false,
								minimizable: false,
								maximizable: false,
								resizable: false,
								iconCls: "icon-save",
								modal: true,
								width: 800,
								height: 300,
								buttons: [{
									text: "确定",
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
									text: "取消",
									iconCls: "icon-cancel",
									handler: function() {
										cancelEdit();
									}
								}]
							});
						}
					}, '-', {
						text: "修改",
						iconCls: "icon-edit",
						handler: function() {
							if (checkDatagridChecked("update")) {
								var selectedRows = $("#tt").datagrid("getChecked");
								
								loadAjaxRequest("${pageContext.request.contextPath }/loadEmpRewardInfo.action", {
									id: selectedRows[0].id
								}, function(obj) {
									$("#empName").val(obj.empName).attr("disabled", true);
									$("#empId").val("");
									
									if ("奖励" == obj.rewardType) {
										$("input[type=radio]:eq(0)").attr("checked", true);
									} else {
										$("input[type=radio]:eq(1)").attr("checked", true);
									}
									
									$("#rewardMoney").val(obj.rewardMoney);
									$("#rewardReason").val(obj.rewardReason);
									$("#rewardId").val(obj.id);
									
									$("#editDialog").css("display", "").dialog({
										title: "修改员工奖惩信息",
										collapsible: false,
										minimizable: false,
										maximizable: false,
										resizable: false,
										iconCls: "icon-save",
										modal: true,
										width: 800,
										height: 300,
										buttons: [{
											text: "确定",
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
											text: "取消",
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
						$.messager.alert("错误", "加载员工信息时发生错误！", "error");
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
					title: "选择员工",
					collapsible: false,
					minimizable: false,
					maximizable: false,
					resizable: false,
					modal: true,
					width: 250,
					height: 300,
					buttons: [{
						text: "取消",
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
						<td class="td_lable">员工姓名：</td>
						<td class="td_edit">
							<input id="empName" type="text" readonly="readonly" />
							<input id="empId" type="hidden" />
						</td>
					</tr>
					<tr>
						<td class="td_lable">奖惩类别</td>
						<td class="td_edit">
							<input type="radio" name="rewardType" value="0" checked="checked" />奖励
							<input type="radio" name="rewardType" value="1" />惩罚
						</td>
					</tr>
					<tr>
						<td class="td_lable">奖惩金额：</td>
						<td class="td_edit">
							<input id="rewardMoney" type="text" />元
						</td>
					</tr>
					<tr>
						<td class="td_lable">奖惩原因</td>
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
