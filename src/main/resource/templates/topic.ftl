<!DOCTYPE html>
<html ng-app="myApp" ng-controller="myCtrl as vm">
   <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      <title>黑名单爆料--${topic.company}--IT黑名单</title>
      <meta name="fragment" content="!">
      <meta name="keywords" content="IT黑名单,培训机构,无良公司,黑名单,北京,上海,广州,深圳,成都,大连,武汉" />
	  <meta name="description" content="IT黑名单(http://www.itblacklist.cn/)。要问公司怎么样，就查IT黑名单！前人爆料，后人弃坑。维护劳动者权益，发现人与企业关系，打造求职通讯录.IT黑名单，WWW.ITBLACKLIST.CN" />

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
					"bdText" : "我在IT黑名单[http://www.itblacklist.cn/]爆料了黑公司，快来吐槽。",
					"bdMini" : "2",
					"bdMiniList" : false,
					"bdPic" : "",
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
