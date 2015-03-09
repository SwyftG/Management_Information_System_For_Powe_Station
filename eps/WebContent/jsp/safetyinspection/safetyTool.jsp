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
				$("#tt").datagrid({
					url: "${pageContext.request.contextPath }/listAllSafetyTool.action",
					title: "安全工具列表",
					width: 'auto',
					height: 'auto',
					autoRowHeight: false,
					striped: true,
					fitColumns: true,
					rownumbers:true,
					idField:'id',
					nowrap: false,
					columns:[[
						{field:'toolName',title:'工具名称'},
						{field:'toolDesc',title:'工具说明'},
						{field:'toolImg',title:'工具图片', formatter: function(value) {
							if ("" != value) {
								var imgPath = "${pageContext.request.contextPath }/" + value;
								
								var html = "<img alt='' src='" + imgPath + "' border='0' style='width: 170px; height: 123px;' />";
								
								return html;
							} else {
								return "";
							}
						}},
						{field: 'createTimeStr', title: '编辑时间'}
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
								title: "新增安全工具",
								collapsible: false,
								minimizable: false,
								maximizable: false,
								resizable: false,
								iconCls: "icon-save",
								modal: true,
								width: 600,
								height: 400,
								buttons: [{
									text: "确定",
									iconCls: "icon-ok",
									handler: function() {
										editAjaxRequest("${pageContext.request.contextPath }/addSafetyTool.action", {
											toolImg: $("#toolImgPath").val(),
											toolName: $("#toolName").val(),
											toolDesc: $("#toolDesc").val()
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
								
								loadAjaxRequest("${pageContext.request.contextPath }/loadSafetyTool.action", {
									id: selectedRows[0].id
								}, function(obj) {
									$("#toolId").val(obj.id);
									$("#toolName").val(obj.toolName);
									$("#toolDesc").val(obj.toolDesc);
									
									if ("" != obj.toolImg) {
										$("#toolImgPath").val(obj.toolImg);
										$("#toolImgTr td img").attr("src", "${pageContext.request.contextPath }/" + obj.toolImg);
										$("#toolImgTr").css("display", "");
									}
									
									$("#editDialog").css("display", "").dialog({
										title: "修改安全工具",
										collapsible: false,
										minimizable: false,
										maximizable: false,
										resizable: false,
										iconCls: "icon-save",
										modal: true,
										width: 600,
										height: 400,
										buttons: [{
											text: "确定",
											iconCls: "icon-ok",
											handler: function() {
												editAjaxRequest("${pageContext.request.contextPath }/modifySafetyTool.action", {
													id: $("#toolId").val(),
													toolImg: $("#toolImgPath").val(),
													toolName: $("#toolName").val(),
													toolDesc: $("#toolDesc").val()
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
										
										editAjaxRequest("${pageContext.request.contextPath }/removeSafetyTool.action", {
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
				$("#toolImgPath").val("");
				$("#toolId").val("");
				$("#toolName").val("");
				$("#toolDesc").val("");
				$("#toolImg").val("");
				$("#toolImgTr").css("display", "none");
			}
			
			function cancelEdit() {
				$("#tt").datagrid("uncheckAll");
				
				$("#editDialog").css("display", "none").dialog("close");
			}
			
			function uploadImage() {
				var file = dwr.util.getValue("toolImg");
				
				if ("" != file.value) {
					var fileName = file.value;
					
					var extname = fileName.substring(fileName.length - 4, fileName.length).toUpperCase();

					if(extname != '.JPG' && extname != ".JPEG" && extname != ".PNG" && extname != ".GIF") {
			            showWarningMessage("请上传图片格式文件！");
			            
			            return;
			        }

					FileUploadService.uploadSafetyToolImg(file, fileName, function(returnData) {
						if (returnData) {
							$("#toolImgPath").val(returnData);
							
							$("#toolImgTr td img").attr("src", "${pageContext.request.contextPath }/" + returnData);
							$("#toolImgTr").css("display", "");
						}
					});
				}
			}
		</script>
	</head>
	
	<body>
		<table id="tt"></table>
		
		<div id="editDialog" style="display: none;">
			<table style="margin-top: 20px; margin-bottom: 20px;" width="90%" align="center" class="table" border="0" cellspacing="1" cellpadding="3">
				<tr>
					<td class="td_lable">工具名称：</td>
					<td class="td_edit">
						<input type="text" id="toolName" />
					</td>
				</tr>
				<tr>
					<td class="td_lable">工具说明：</td>
					<td class="td_edit">
						<textarea rows="5" cols="60" id="toolDesc"></textarea>
					</td>
				</tr>
				<tr>
					<td class="td_lable">工具图片：</td>
					<td class="td_edit">
						<input type="file" id="toolImg" onchange="uploadImage();" />
					</td>
				</tr>
				<tr id="toolImgTr" style="display: none;">
					<td colspan="2">
						<img alt="" src="" style="border: none; width: 170px; height: 123px;" />
					</td>
				</tr>
			</table>
		</div>
		
		<input type="hidden" id="toolId" />
		<input type="hidden" id="toolImgPath" />
	</body>
</html>
