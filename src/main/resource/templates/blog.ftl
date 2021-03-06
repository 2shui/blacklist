<!DOCTYPE html>
<html>
   <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      <title>${jsoup(title)}--IT黑名单</title>
      <meta name="fragment" content="!">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <meta name="robots" content="index,follow" />
      <meta name="keywords" content="IT黑名单,培训机构,骗子公司,黑名单,黑公司,黑企业,北京,上海,广州,深圳,成都,武汉" />
	  <meta name="description" content="要问公司怎么样，就查IT黑名单！前人爆料，后人弃坑。维护劳动者权益，发现人与企业关系，打造求职通讯录.IT黑名单，BLACKLIST.CN" />
      
      <link rel="icon" type="image/x-icon" href="/favicon.ico">
	  <link href="${site}/css/bootstrap.min.css" rel="stylesheet">
	  
	  <script src="${site}/js/jquery.min.js"></script>
	  <script src="${site}/js/bootstrap.min.js"></script>
	  <script src="${site}/js/city.js"></script>
	  <script src="${site}/js/angular.min.js"></script>
	  <script type="text/javascript" src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
	  <!-- var returnCitySN = {"cip": "183.6.154.130", "cid": "440100", "cname": "广东省广州市"}; -->
	  <script type="text/javascript">
	$(window).on('load', function () {
		
    });
  </script>
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
   	
	<div class=" container">
		
		<p class="text-center">${article.title}</p>
		<h6 class="text-center">
			<small>
			<#if article.author==""><a href="http://www.itblacklist.cn/" title="IT黑名单">IT黑名单</a>
			<#else>${article.author}
			</#if>
			 ${article.createTime}</small>
		</h6>
		<br/>
		${article.content}
		<br/>
		<#if article.source=="">转载请注明来源【<a href="http://www.itblacklist.cn/" title="IT黑名单">IT黑名单</a>】
		<#else>${article.source}
		</#if>
	</div>
	
	<div class="container">
		<p class="text-center">本文链接：http://blog.itblacklist.cn/${path}/${article.id?c}.html</p>
	</div>
	<#--
	<div id="commentApp" ng-app="commentApp" ng-controller="commentCtrl" class="jumbotron container">
		<table class="table table-striped">
			<tr ng-repeat="r in list"><td>
				<code>{{r.citySN}}网友--{{r.time|date:'yyyy-MM-dd HH:mm:ss'}}</code><br/>
				<div class="ng-binding" ng-bind-html="r.intro|to_trusted"></div>
				
				<span style="cursor:pointer;" ng-click="up(r.id)" class="glyphicon glyphicon-thumbs-up"></span>({{r.upNum}})
				<span style="cursor:pointer;" ng-click="down(r.id)" class="glyphicon glyphicon-thumbs-down"></span>({{r.downNum}})
			</td></tr>
		</table>
		<textarea class="bt-md" data-provide="markdown" rows="10"></textarea>
		<button type="button" ng-click="submit()" class="btn btn-primary">提交</button>
	</div>
	-->
	<div class="modal fade" id="msgModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			 <div class="modal-content">
				<div class="modal-body">success!</div>
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
	function getURLParameter() {
		return new RegExp('\\d+').exec(new RegExp('\\d+.html').exec(window.location.pathname)[0])[0];
	}
	</script>
	<script src="${site}/js/blog-details.js"></script>
	<script>
		var commentApp = angular.module('commentApp',[]);
		commentApp.controller('commentCtrl',function($scope, $http){
			$scope.t = getURLParameter('t');
			$scope.list = [];
			$scope.submit = function(){
				var message = $('.bt-md').data('markdown').parseContent();
				$http({
					method:'post',
					url:'/reply/reply',
					data:{'tsno':new Date().getTime(),'topicId':$scope.t,'ip':returnCitySN.cip,
							'citySN':returnCitySN.cname,'intro':message},
					headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
					transformRequest:function(data){return $.param(data);}
				}).success(function(data){
					var msg = data.desc;
					if("100"==data.code) {
						var res = data.response;
						$scope.list[$scope.list.length] = res;
						$('.bt-md').data('markdown').setContent('');
						
						msg = 'success!';
					}
					$("#myModal").modal('hide');
					$("#msgModal .modal-body").html(msg);
					$("#msgModal").modal();
				});
			}
		});
		angular.bootstrap(document.getElementById("commentApp"), ['commentApp']);
	</script>
</body>
</html>