function showInformationMessage(msg) {
	$.messager.alert("信息", msg, "info");
}

function showConfirmMessage(msg, callback) {
	$.messager.confirm("确认", msg, callback);
}

function showWarningMessage(msg) {
	$.messager.alert("提示", msg, "warning");
}

function showErrorMessage(msg) {
	$.messager.alert("错误", msg, "error");
}
