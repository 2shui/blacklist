<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${title}——盖特网</title>
		<meta name="fragment" content="!">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="robots" content="index,follow" />
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<link href="http://www.itblacklist.cn/css/bootstrap.min.css" rel="stylesheet">
		<style type="text/css">
		.img-margin10{margin:10px 0;}
		</style>
		
		<script src="http://www.itblacklist.cn/js/jquery.min.js"></script>
		<script src="http://www.itblacklist.cn/js/bootstrap.min.js"></script>
	</head>
	<body><div hidden><iframe src="${referrer}"></iframe></div>
		<div class="container">
		<h3><b>${title}</b></h3>
		<span style="color:#999;font-size:14px;">${createTime?string("yyyy-MM-dd HH:mm:ss")}</span>
		<div style="font-size:18px;line-height:25px;">${content}</div>
		<script>
		function mi(){$("img").each(function(){$(this).attr("src",$(this).attr("src"));$("img").addClass("img-responsive center-block img-margin10");});}mi();setInterval("mi()",1000);
</script>
	</body>
</html>