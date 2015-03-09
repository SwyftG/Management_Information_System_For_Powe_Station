function checkDatagridChecked() {
	var selectedRows = $("#tt").datagrid("getChecked");
	
	var len = selectedRows.length;
	
	if (0 == len) {
		showWarningMessage("请选择要操作的记录！");

		return false;
	}
	
	if (0 != arguments.length) {
		if (len > 1) {
			showWarningMessage("只能选择一条要操作的记录！");
			
			return false;
		}
	}
	
	return true;
}