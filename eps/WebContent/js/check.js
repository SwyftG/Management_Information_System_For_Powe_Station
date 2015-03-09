function checkDatagridChecked() {
	var selectedRows = $("#tt").datagrid("getChecked");
	
	var len = selectedRows.length;
	
	if (0 == len) {
		showWarningMessage("��ѡ��Ҫ�����ļ�¼��");

		return false;
	}
	
	if (0 != arguments.length) {
		if (len > 1) {
			showWarningMessage("ֻ��ѡ��һ��Ҫ�����ļ�¼��");
			
			return false;
		}
	}
	
	return true;
}