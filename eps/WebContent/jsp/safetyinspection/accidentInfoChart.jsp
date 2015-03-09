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
		<script type="text/javascript" src="${pageContext.request.contextPath }/FusionCharts/FusionCharts.js"></script>
		<script type="text/javascript">
			$(function() {
				var url = "${pageContext.request.contextPath }/buildAccidentInfoChart.action";
				
				$.post(url, {}, function(returnData) {
					if ("success" == returnData.status) {
						var xml = returnData.data;
						
						var chart = new FusionCharts("${pageContext.request.contextPath }/FusionCharts/Column3D.swf", "incomeRecordChart", "600", "500");

						chart.setDataXML(xml);

						chart.render("chartDiv");
					} else {
						showErrorMessage(returnData.message);
					}
				});
			});
		</script>
	</head>
	
	<body>
		<br />
		<br />
	
		<div id="chartDiv" align="center">
			
		</div>
	</body>
</html>
