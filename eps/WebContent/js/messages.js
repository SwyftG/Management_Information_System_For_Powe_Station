function showInformationMessage(msg) {
	$.messager.alert("��Ϣ", msg, "info");
}

function showConfirmMessage(msg, callback) {
	$.messager.confirm("ȷ��", msg, callback);
}

function showWarningMessage(msg) {
	$.messager.alert("��ʾ", msg, "warning");
}

function showErrorMessage(msg) {
	$.messager.alert("����", msg, "error");
}
