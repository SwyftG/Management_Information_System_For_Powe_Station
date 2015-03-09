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
		<script type="text/javascript" src="${pageContext.request.contextPath }/FusionCharts/FusionCharts.js"></script>
		<script type="text/javascript">
			$(function() {
				$("#btnGenerate").click(function() {
					var evaluateYear = $("#evaluateYear").combobox("getValue");
					
					if (-1 == evaluateYear) {
						showWarningMessage("请选择评价年度！");
						
						return;
					}
					
					$.post("${pageContext.request.contextPath }/generateEmpEvaluateChart.action", {
						evaluateYear: evaluateYear
					}, function(returnData) {
						if ("success" == returnData.status) {
							var xml = returnData.data;
							
							var chart = new FusionCharts("${pageContext.request.contextPath }/FusionCharts/Pie3D.swf", "empStatChart", "600", "500");

							chart.setDataXML(xml);

							chart.render("chartDiv");
						} else {
							showErrorMessage(returnData.message);
						}
					});
				});
			});
		</script>
	</head>
	
	<body>
		<br />
		
		<div>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table">
				<tbody>
					<tr>
						<td class="td_lable">评价年度：</td>
						<td class="td_edit">
							<select id="evaluateYear" class="easyui-combobox" style="width: 150px;" data-options="panelHeight: 'auto'">
								<option value='<s:property value="@org.eps.common.util.Constants@DEFAULT_OPTION_VALUE" />'>请选择</option>
								<option value="2009">2009</option>
								<option value="2010">2010</option>
								<option value="2011">2011</option>
								<option value="2012">2012</option>
								<option value="2013">2013</option>
							</select>
							&nbsp;
							<a href="#" id="btnGenerate" class="easyui-linkbutton" data-options="iconCls: 'icon-ok'">生成</a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<br />
		
		<div id="chartDiv" align="center">
			
		</div>
	</body>
</html>
