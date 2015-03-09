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
					currentText: "����",
					closeText: "�ر�",
					formatter: formatDate
				}).datebox("calendar").calendar({
					weeks: ["��","һ","��","��","��","��","��"],
					months: ["һ��","����","����","����","����","����","����","����","����","ʮ��","ʮһ��","ʮ����"]
				});

				$("#qry_endDate").datebox({
					currentText: "����",
					closeText: "�ر�",
					formatter: formatDate
				}).datebox("calendar").calendar({
					weeks: ["��","һ","��","��","��","��","��"],
					months: ["һ��","����","����","����","����","����","����","����","����","ʮ��","ʮһ��","ʮ����"]
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
					title: "�����ļ��б�",
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
					    {field: "title", title: "�����"},
						{field:'fileNo',title:'�ļ����'},
						{field:'orgName',title:'���Ļ���'},
						{field: 'publishDateStr', title: '����ʱ��'},
						{field: "filePath", title: "����", formatter: function(value) {
							var html = "<img alt='' onclick='javascript:download(\"" + value + "\");' src='${pageContext.request.contextPath }/css/icons/filesave.png' title='����' style='cursor: hand;' />";
							
							return html;
						}, align: "center"}
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
								title: "���������ļ�",
								collapsible: false,
								minimizable: false,
								maximizable: false,
								resizable: false,
								iconCls: "icon-save",
								modal: true,
								width: 600,
								height: 240,
								buttons: [{
									text: "ȷ��",
									iconCls: "icon-ok",
									handler: function() {
										editAjaxRequest("${pageContext.request.contextPath }/addDocument.action", {
											orgId: $("#orgId").val(),
											title: $("#title").val(),
											filePath: $("#filePath").val()
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
						showErrorMessage("���ػ������˵�ʱ���ִ���");
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
							$("#tipMsg").css("display", "").text("�����ļ����ϴ���");
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
						<td class="td_lable">��ʼʱ�䣺</td>
						<td class="td_edit">
							<input id="qry_beginDate" />
						</td>
						<td class="td_lable">����ʱ�䣺</td>
						<td class="td_edit">
							<input id="qry_endDate" />
						</td>
					</tr>
					<tr>
						<td class="td_lable">���Ļ�����</td>
						<td class="td_edit">
							<input id="qry_orgName" type="text" readonly="readonly" />
							<input type="hidden" id="qry_orgId" />
						</td>
						<td class="td_lable">����ʣ�</td>
						<td class="td_edit">
							<input id="qry_title" type="text" />
						</td>
					</tr>
					<tr>
						<td class="td_lable">�ļ���ţ�</td>
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
						<a href="#" id="btnQuery" class="easyui-linkbutton" data-options='iconCls: "icon-search"'>��ѯ</a>
						<a href="#" id="btnReset" class="easyui-linkbutton" data-options='iconCls: "icon-cancel"'>����</a>
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
					<td class="td_lable">����ʣ�</td>
					<td class="td_edit">
						<input type="text" id="title" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">���Ļ�����</td>
					<td class="td_edit">
						<input type="text" id="orgName" readonly="readonly" />
						<input type="hidden" id="orgId" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">�����ļ���</td>
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
