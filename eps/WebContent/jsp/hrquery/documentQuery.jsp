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
				$("#qry_beginDate").datebox({
					currentText: "今天",
					closeText: "关闭",
					formatter: formatDate
				}).datebox("calendar").calendar({
					weeks: ["日","一","二","三","四","五","六"],
					months: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"]
				});

				$("#qry_endDate").datebox({
					currentText: "今天",
					closeText: "关闭",
					formatter: formatDate
				}).datebox("calendar").calendar({
					weeks: ["日","一","二","三","四","五","六"],
					months: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"]
				});
				
				$("#btnQuery").click(function() {
					var param = buildQueryParam();

					$("#tt").datagrid("options").queryParams = param;
					
					$("#tt").datagrid("options").url = "${pageContext.request.contextPath }/listDocumentByCondition.action";
					$("#tt").datagrid("load");
				});
				
				$("#btnReset").click(function() {
					$("#qry_beginDate").datebox("setText", "");
					$("#qry_endDate").datebox("setText", "");
					$("#qry_orgName").val("");
					$("#qry_orgId").val("");
					$("#qry_title").val("");
					$("#qry_fileNo").val("");
				});
				
				$("#qry_orgName").focus(function() {
					showSelectOrgDialog("qry_orgName", "qry_orgId");
				});
				
				$("#orgName").focus(function() {
					showSelectOrgDialog("orgName", "orgId");
				});
				
				$("#tt").datagrid({
					url: "${pageContext.request.contextPath }/listDocumentByCondition.action",
					title: "劳资文件列表",
					width: 'auto',
					height: 'auto',
					autoRowHeight: false,
					striped: true,
					fitColumns: true,
					rownumbers:true,
					idField:'id',
					nowrap: false,
					queryParams: buildQueryParam(),
					columns:[[
					    {field: "title", title: "主题词"},
						{field:'fileNo',title:'文件编号'},
						{field:'orgName',title:'发文机构'},
						{field: 'publishDateStr', title: '发文时间'},
						{field: "filePath", title: "操作", formatter: function(value) {
							var html = "<img alt='' onclick='javascript:download(\"" + value + "\");' src='${pageContext.request.contextPath }/css/icons/filesave.png' title='下载' style='cursor: hand;' />";
							
							return html;
						}, align: "center"}
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
								title: "新增劳资文件",
								collapsible: false,
								minimizable: false,
								maximizable: false,
								resizable: false,
								iconCls: "icon-save",
								modal: true,
								width: 600,
								height: 240,
								buttons: [{
									text: "确定",
									iconCls: "icon-ok",
									handler: function() {
										editAjaxRequest("${pageContext.request.contextPath }/addDocument.action", {
											orgId: $("#orgId").val(),
											title: $("#title").val(),
											filePath: $("#filePath").val()
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
										
										editAjaxRequest("${pageContext.request.contextPath }/removeDocument.action", {
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
				$("#title").val("");
				$("#docFile").val("");
				$("#filePath").val("");
				$("#tipMsg").css("display", "none");
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
			
			function buildQueryParam() {
				var param = {
					beginDate: $("#qry_beginDate").datebox("getText"),
					endDate: $("#qry_endDate").datebox("getText"),
					orgId: ("" == $("#qry_orgId").val() ? -1 : $("#qry_orgId").val()),
					title: $("#qry_title").val(),
					fileNo: $("#qry_fileNo").val()
				};
				
				return param;
			}
			
			function showSelectOrgDialog(orgName, orgId) {
				$("#orgTree").tree({
					url: "${pageContext.request.contextPath }/buildOrgTree.action",
					animate: true,
					lines: true,
					onLoadError: function() {
						showErrorMessage("加载机构树菜单时出现错误！");
					},
					onClick: function(node) {
						if (-1 != node.id) {
							$("#" + orgName).val(node.text);
							$("#" + orgId).val(node.id);
							
							$("#selectOrgDialog").css("display", "none").dialog("close");
						}
					}
				});
				
				$("#selectOrgDialog").css("display", "").dialog({
					title: "选择机构",
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
							$("#selectOrgDialog").css("display", "none").dialog("close");
						}
					}]
				});
			}
			
			function download(filePath) {
				$("#hidden_filePath").val(encodeURI(filePath));
				
				document.forms[0].submit();
			}
			
			function uploadDoc() {
				var file = dwr.util.getValue("docFile");
				
				if ("" != file.value) {
					var fileName = file.value;
					
					FileUploadService.uploadDocument(file, fileName, function(returnData) {
						if (returnData) {
							$("#tipMsg").css("display", "").text("劳资文件已上传！");
							$("#filePath").val(returnData);
						}
					});
				}
			}
		</script>
	</head>
	
	<body>
		<br />
		<div>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table">
				<tbody>
					<tr>
						<td class="td_lable">开始时间：</td>
						<td class="td_edit">
							<input id="qry_beginDate" />
						</td>
						<td class="td_lable">结束时间：</td>
						<td class="td_edit">
							<input id="qry_endDate" />
						</td>
					</tr>
					<tr>
						<td class="td_lable">发文机构：</td>
						<td class="td_edit">
							<input id="qry_orgName" type="text" readonly="readonly" />
							<input type="hidden" id="qry_orgId" />
						</td>
						<td class="td_lable">主题词：</td>
						<td class="td_edit">
							<input id="qry_title" type="text" />
						</td>
					</tr>
					<tr>
						<td class="td_lable">文件编号：</td>
						<td class="td_edit">
							<input id="qry_fileNo" type="text" />
						</td>
						<td class="td_lable">&nbsp;</td>
						<td class="td_edit">
							&nbsp;
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<table width="100%">
			<tr>
				<td>
					<div style="text-align: right;">
						<a href="#" id="btnQuery" class="easyui-linkbutton" data-options='iconCls: "icon-search"'>查询</a>
						<a href="#" id="btnReset" class="easyui-linkbutton" data-options='iconCls: "icon-cancel"'>重置</a>
					</div>
				</td>
			</tr>
		</table>
		
		<br />
		
		<table id="tt"></table>
		
		<div id="selectOrgDialog" style="display: none;">
			<ul id="orgTree"></ul>
		</div>
		
		<div id="editDialog" style="display: none;">
			<table style="margin-top: 20px; margin-bottom: 20px;" width="90%" align="center" class="table" border="0" cellspacing="1" cellpadding="3">
				<tr>
					<td class="td_lable">主题词：</td>
					<td class="td_edit">
						<input type="text" id="title" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">发文机构：</td>
					<td class="td_edit">
						<input type="text" id="orgName" readonly="readonly" />
						<input type="hidden" id="orgId" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">劳资文件：</td>
					<td class="td_edit">
						<input type="file" id="docFile" onchange="uploadDoc();" /><span id="tipMsg" style="display: none; color: red;"></span>
						<input type="hidden" id="filePath" />
					</td>
				</tr>
			</table>
		</div>
		
		<form id="downloadForm" action="${pageContext.request.contextPath }/downloadDocument.action" method="post">
			<input type="hidden" id="hidden_filePath" name="filePath" />
		</form>
	</body>
</html>
