<!DOCTYPE html>
<html ng-app="myApp" ng-controller="myCtrl as vm">
   <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="shanghai.htm" rel="parent" charset="UTF-8" hreflang="zh-Hans" />
	  <title>${topic.company}--IT黑名单--IT黑公司查询，打造IT求职通讯录</title>
      <meta name="fragment" content="!">
      <meta name="keywords" content="IT黑名单,培训机构,骗子公司,黑名单,黑公司,黑企业,北京,上海,广州,深圳,成都,武汉" />
	  <meta name="description" content="要问公司怎么样，就查IT黑名单！前人爆料，后人弃坑。维护劳动者权益，发现人与企业关系，打造求职通讯录.IT黑名单唯一官方网站，BLACKLIST.CN" />
	  <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0" />

	  <link rel="icon" type="image/x-icon" href="/favicon.ico">
	  <link href="/css/bootstrap.min.css" rel="stylesheet">
	  <style type="text/css">
	  	.roll_txt{text-align:left;white-space:nowrap; display:block;}
	  </style>
	  
	  <script src="/js/jquery.min.js"></script>
	  <script src="/js/bootstrap.min.js"></script>
	  <script src="/js/angular.min.js"></script>
	  <script src="/js/city.js"></script>
	  <script src="/js/baidutj.js"></script>
	  
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
   <div ng-include="'/top.html'"></div>
   	
	<div class="container">
		<div class="form-group">
			<span class="col-sm-12 text-right">
				<form class="form-inline">
					<input type="text" ng-model="name" placeholder="公司名" class="form-control">
					<button class="btn btn-default" ng-click="search()" type="button">
						Search!
					</button>
				</form>
			</span>
		</div>
		<br/>
		<p hidden>怎么样 薪资 待遇 好不好 黑心 垃圾 招聘 职位</p>
		<div>
			<h3 class="">${topic.sketch}——${topic.company}</h3>
			${topic.intro}
			<!--
			<div class="ng-binding" ng-bind-html="topic.intro|to_trusted"></div>
			-->
		</div>
	</div>
	
	<div class="container">
		<span>${topic.createTime?string("yyyy-MM-dd HH:mm:ss")}</span><br/>
		<code>当事人一人之言，或有失公允，请理性看待！</code><br/>


		<div class="bdsharebuttonbox">
			<a href="#" class="bds_more" data-cmd="more"></a><a href="#"
				class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#"
				class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a href="#"
				class="bds_weixin" data-cmd="weixin" title="分享到微信"></a><a href="#"
				class="bds_sqq" data-cmd="sqq" title="分享到QQ好友"></a><a href="#"
				class="bds_isohu" data-cmd="isohu" title="分享到我的搜狐"></a><a href="#"
				class="bds_fbook" data-cmd="fbook" title="分享到Facebook"></a><a
				href="#" class="bds_ty" data-cmd="ty" title="分享到天涯社区"></a><a
				href="#" class="bds_twi" data-cmd="twi" title="分享到Twitter"></a>
		</div>
		<script>
			window._bd_share_config = {
				"common" : {
					"bdSnsKey" : {},
					"bdText" : "【IT黑名单爆料】${topic.company}-${topic.city}[http://www.itblacklist.cn/]",
					"bdDesc" : "IT求职避坑，查询求职通讯录，IT黑名单官方网站HTTP://WWW.BLACKLIST.CN/",
					"bdMini" : "2",
					"bdMiniList" : false,
					"bdPic" : "http://img.itblacklist.cn/20161122/1479804218158.jpg",
					"bdStyle" : "1",
					"bdSize" : "16"
				},
				"share" : {}
			};
			with (document)
				0[(getElementsByTagName('head')[0] || body)
						.appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='
						+ ~(-new Date() / 36e5)];
		</script>



	</div>
	<br/>
	<div class="container" style="background-color:#DDD;">
		<p class="text-danger">同城其它被吐槽公司：</p>
		<p ng-repeat="t in associated" class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
			<a class="hot_txt1" title="{{t.city}}{{t.company}}-{{t.sketch}}" href="{{t.path}}">
				{{t.sketch}}--({{t.company}})
			</a>
		</p>
	</div>
	<br/>
	
	<div class="container">
		<table class="table table-striped">
			<tr ng-repeat="r in list"><td>
				<code>{{r.citySN}}网友--{{r.time|date:'yyyy-MM-dd HH:mm:ss'}}</code><br/>
				<div class="ng-binding" ng-bind-html="r.intro|to_trusted"></div>
				
				<span style="cursor:pointer;" ng-click="up(r.id)" class="glyphicon glyphicon-thumbs-up"></span>({{r.upNum}})
				<span style="cursor:pointer;" ng-click="down(r.id)" class="glyphicon glyphicon-thumbs-down"></span>({{r.downNum}})
			</td></tr>
		</table>
		<textarea class="bt-md" data-provide="markdown" rows="10"></textarea>
		<button type="button" ng-click="reply()" class="btn btn-primary">提交</button>
	</div>
	
	<div ng-include="'/footer.html'"></div>
	<ng-include src="'/modal.html'"></ng-include>
	
	<script>var topicId=${topic.id?c};</script>
	<script src="/js/app/details.js"></script>
	<script src="/js/app/detailsApp.js"></script>
	<script type="text/javascript" src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
</body>
</html>
