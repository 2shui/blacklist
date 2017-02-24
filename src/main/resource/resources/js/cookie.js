function getCookie(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}
function setCookie(name, value, times) {
	var exp = new Date();
	exp.setTime(exp.getTime() + times);
	document.cookie = name + "=" + escape(value) + ";expires="
			+ exp.toGMTString();
}