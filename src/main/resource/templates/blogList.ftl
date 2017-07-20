<!DOCTYPE html>
<html>
   <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      <title>博客--IT黑名单</title>
      <meta name="fragment" content="!">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <meta name="robots" content="index,follow" />
      <meta name="keywords" content="IT黑名单,黑名单,培训机构黑名单,公司黑名单,企业黑名单,北京,上海,广州,深圳,成都，大连" />
	  <meta name="description" content="要问公司怎么样，就查IT黑名单！前人爆料，后人弃坑。维护劳动者权益，发现人与企业关系，打造求职通讯录.IT黑名单，BLACKLIST.CN" />
      
      <link rel="icon" type="image/x-icon" href="/favicon.ico">
	  <link href="${site}/css/bootstrap.min.css" rel="stylesheet">
	  <style type="text/css">
	  	.hot_title{text-overflow: ellipsis;word-break: break-all;
	  		white-space: nowrap;overflow: hidden;margin-bottom:10px;}
	  </style>
	  
	  <script src="${site}/js/jquery.min.js"></script>
	  <script src="${site}/js/bootstrap.min.js"></script>
	  <script src="${site}/js/angular.min.js"></script>
	  
  <script src="${site}/js/baidutj.js"></script>
  <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
	<script>
	  (adsbygoogle = window.adsbygoogle || []).push({
	    google_ad_client: "ca-pub-8781284468242009",
	    enable_page_level_ads: true
	  });
	</script>
	<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-102733758-1', 'auto');
  ga('send', 'pageview');

</script>
   </head>
   <body class="">
	<div class="container">
   		<h3></h3>
		<nav class="navbar navbar-default" role="navigation">
			<div class="container-fluid">
				<div class="navbar-header col-lg-1 col-xs-2">
					<a class="navbar-brand" href="http://www.itblacklist.cn/">首页</a>
				</div>
				<div class="col-lg-7 col-xs-10">
					<ul class="nav navbar-nav col-lg-12">
						<li class=""><a href="http://blog.itblacklist.cn/">博 客</a></li>
						<li class=""><a href="javaScript:void(0);" data-toggle="modal" data-target="#myModal"">爆 料</a></li>
						<li class=""><a href="http://www.itblacklist.cn/question.html">常 见 问 题</a></li>
						<li class="">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								工具
								<b class="caret"></b>
							</a>
							<ul class="dropdown-menu">
								<li><a href="http://www.itblacklist.cn/almanac.html">程 序 员 老 皇 历</a></li>
								<li class="divider"></li>
								<li><a href="http://df.itblacklist.cn/">日期格式化</a></li>
								<li class="divider"></li>
								<li><a href="http://tucao.itblacklist.cn/">吐 槽</a></li>
							</ul>
						</li>
						<li class=""><a href="javaScript:void(0);" data-toggle="modal" data-target="#feedbackModal">反 馈</a></li>
					</ul>
				</div>
			</div>
		</nav>
   	</div>
   	
	<div class="container">
		
		<div class="row">
			<div class="col-lg-8 col-xs-12 blog-main" id="app" ng-app="app" ng-controller="ctrl">
				<#list articles as blog>
				<div class="panel panel-success" ng-repeat="b in as">
				    <div class="panel-heading">
				    	<a href="http://blog.itblacklist.cn/${blog.source}/${blog.id?c}.html" title="${blog.title}--IT黑名单">
				        <h3 class="panel-title">${blog.title}</h3>
				        </a>
				    </div>
				    <div class="panel-body">
				        ${subStr(jsoup(blog.content), 200)}...
				        <div class="text-right"><#-- ${blog.accessNum?c} -->
				        	${blog.createTime} 阅读数(<span id="n${blog.id?c}">${blog.accessNum?c}</span>)
				        </div>
				    </div>
				</div>
				</#list>
			</div>
			<div class="col-lg-3 col-xs-12 col-lg-offset-1 blog-sidebar">
				<div class="sidebar-module sidebar-module-inset">
					<div class="panel panel-success">
						<div class="panel-heading">热门文章</div>
						<div class="panel-body">
							<#list hot as h>
							<a class="col-lg-12 hot_title" title="${h.title}"
								href='http://blog.itblacklist.cn/${h.createTime?string("yyyyMMdd")}/${h.id?c}.html'>
								${h.title}
							</a><br/>
							</#list>
						</div>
					</div>
				</div>
				<div class="sidebar-module sidebar-module-inset text-center">
					<div class="col-lg-6 col-lg-offset-3">
						<img src="http://img.itblacklist.cn/20170621/1498025729074.png" 
							class="img-responsive" title="赞助站长-IT黑名单--www.itblacklist.cn">
						赞助站长
					</div>
					<div class="col-lg-12">
						<div class="col-lg-6">
							<img src="http://img.itblacklist.cn/20161130/1480487508328.jpg" 
								class="img-responsive" title="公众号-IT黑名单--www.itblacklist.cn">
							公众号
						</div>
						<div class="col-lg-6">
							<img src="http://img.itblacklist.cn/20170621/1498025694676.jpg" 
								class="img-responsive" title="小程序-IT黑名单--www.itblacklist.cn">
							小程序
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
		
	
	<div class="container">
		<div class="container text-center">
			© Copyright 2016 IT黑名单 Inc.All Rights Reserved. 
			<a rel="nofollow" target="_blank" href="http://www.miitbeian.gov.cn">豫ICP备15018592号-2</a>
		</div>
	</div>
	<script>
	$.ajax({
		type:'get',
		dataType:'jsonp',
		url:'http://www.itblacklist.cn/blog/ln',
		data:{'tsno':new Date().getTime()},
		success:function(data){
			if("100"==data.code){
				var ls = data.response;
				for(var i=0;i<ls.length;i++) {
					var id=ls[i].id;
					$("#n"+id).html(ls[i].accessNum);
				}
			}
		}
	});
	</script>
</body>
</html>