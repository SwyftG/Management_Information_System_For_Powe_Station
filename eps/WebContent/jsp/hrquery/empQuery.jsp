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
		<script type="text/javascript">
			$(function() {
				$("#btnQuery").click(function() {
					var param = buildQueryParam();

					$("#tt").datagrid("options").queryParams = param;
					
					$("#tt").datagrid("options").url = "${pageContext.request.contextPath }/listEmployeeByCondition.action";
					$("#tt").datagrid("load");
				});
				
				$("#btnReset").click(function() {
					$("#personCode").val("");
					$("#name").val("");
					$("#orgName").val("");
					$("#orgId").val("");
				});
				
				$("#orgName").focus(function() {
					showSelectOrgDialog();
				});
				
				$("#tt").datagrid({
					url: "${pageContext.request.contextPath }/listEmployeeByCondition.action",
					title: "Ա����Ϣ�б�",
					width: 'auto',
					height: 'auto',
					autoRowHeight: false,
					striped: true,
					fitColumns: true,
					rownumbers:true,
					nowrap: false,
					queryParams: buildQueryParam(),
					columns:[[
						{field: "personCode", title: "Ա�����"},
						{field:'name',title:'����'},
						{field:'birthdayStr',title:'��������'},
						{field: 'age', title: '����'},
						{field: "gender", title: "�Ա�"},
						{field: "orgName", title: "���ڻ���"},
						{field: "post", title: "��λ"},
						{field: "salary", title: "�̶�н�꣨Ԫ��"}
					]]
				});
			});
			
			function buildQueryParam() {
				var param = {
					personCode: $("#personCode").val(),
					name: $("#name").val(),
					orgId: ("" == $("#orgId").val() ? -1 : $("#orgId").val()),
				};
				
				return param;
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
							$("#orgName").val(node.text);
							$("#orgId").val(node.id);
							
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
		<br />
		<div>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table">
				<tbody>
					<tr>
						<td class="td_lable">Ա����ţ�</td>
						<td class="td_edit">
							<input id="personCode" />
						</td>
						<td class="td_lable">Ա��������</td>
						<td class="td_edit">
							<input id="name" />
						</td>
					</tr>
					<tr>
						<td class="td_lable">����������</td>
						<td class="td_edit">
							<input id="orgName" type="text" readonly="readonly" />
							<input type="hidden" id="orgId" />
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
	</body>
</html>
