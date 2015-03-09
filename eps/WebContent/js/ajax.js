function editAjaxRequest(url, data) {
	$.post(url, data, function(returnData) {
		if ("success" == returnData.status) {
			showInformationMessage("操作成功！");
			
			$("#tt").datagrid("uncheckAll");
			$("#tt").datagrid("reload");
			
			$("#editDialog").css("display", "none").dialog("close");
		} else {
			showErrorMessage(returnData.message);
		}
	});
}

function editAjaxRequestRefreshTree(url, data, treeId) {
	$.post(url, data, function(returnData) {
		if ("success" == returnData.status) {
			showInformationMessage("操作成功！");
			
			$("#tt").datagrid("uncheckAll");
			$("#tt").datagrid("reload");
			
			$("#editDialog").css("display", "none").dialog("close");
			
			$("#" + treeId).tree("reload");
		} else {
			showErrorMessage(returnData.message);
		}
	});
}

function loadAjaxRequest(url, data, successHandler) {
	$.post(url, data, function(returnData) {
		if ("success" == returnData.status) {
			if ($.isFunction(successHandler)) {
				successHandler.call(this, returnData.data);
			}
		} else {
			showErrorMessage(returnData.message);
		}
	});
}