<!DOCTYPE html>
<html>
   <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      <title>IT黑名单--添加博客</title>
      <meta name="fragment" content="!">
      
      <link rel="icon" type="image/x-icon" href="/favicon.ico">
      <!-- include libraries(jQuery, bootstrap) -->
	<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.js"></script> 
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 
	
	<!-- include summernote css/js-->
	<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.2/summernote.css" rel="stylesheet">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.2/summernote.js"></script>
      
	  <script src="/js/city.js"></script>
	  <script src="/js/angular.min.js"></script>
	  <script type="text/javascript">
	  function sendFile(file){
	  	data = new FormData();
	  	data.append("file", file);
	  	$.ajax({
	  		data:data,
	  		type:"POST",
	  		url:"/blog/uploadImg",
	  		cache:false,
	  		contentType:false,
	  		processData:false,
	  		success:function(url){
	  			url = '${imgSite}'+url;
	  			$("#summernote").summernote('insertImage', url, 'img');
	  		}
	  	});
	  }
	$(document).ready(function() {
        $('#summernote').summernote({
        	height:300,
        	callbacks: {
        		onImageUpload:function(files){
        			img = sendFile(files[0]);
        		}
        	}
        });
    });
  </script>
   </head>
   <body class=""><h1></h1>
	<div class="jumbotron container">
		<div class="form-group">
			<span class="col-sm-12" id="sApp" ng-app="sApp" ng-controller="sCtrl">
				<form class="form-horizontal" id="blog">
					<div class="form-group">
						<label class="col-sm-2 control-label">标题</label>
						<div class="col-sm-8">
							<input type="text" class="col-sm-11" ng-model="title" name="title" placeholder="作者名，支持html" required>*
						</div>
					</div><br/>
					<div id="summernote"></div>
					<div class="form-group">
						<label class="col-sm-2 control-label">作者</label>
						<div class="col-sm-8">
							<input type="text" class="col-sm-11" ng-model="author" name="author" placeholder="作者名，支持html" required>*
						</div>
					</div><br/>
					<div class="form-group">
						<label class="col-sm-2 control-label">文章来源</label>
						<div class="col-sm-8">
							<input type="text" class="col-sm-11" ng-model="source" name="source" placeholder="来源，支持html">
						</div>
					</div>
					<button class="btn btn-default" ng-click="submit()" type="button">
						Commit!
					</button>
				</form>
			</span>
		</div>
	</div>
	
	<div class="modal fade" id="msgModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			 <div class="modal-content">
				<div class="modal-body">success!</div>
			</div>
		</div>
	</div>
	
	<div class="container">
		<div class="container text-center">
			© Copyright 2016 IT黑名单 Inc.All Rights Reserved. <a href="http://www.miitbeian.gov.cn">豫ICP备15018592号-2</a>
		</div>
	</div>
	<script>
	</script>
	<script>
		var sApp = angular.module('sApp',[]);
		sApp.controller('sCtrl', function($scope, $http){
			$scope.submit = function() {
				var message = $('#summernote').summernote('code');
				$http({
					method:'post',
					url:'/blog/add',
					data:{'tsno':new Date().getTime(),'title':$scope.title,'content':message,'author':$scope.author,'source':$scope.source},
					headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
					transformRequest:function(data){return $.param(data);}
				}).success(function(data){
					var msg = data.desc;
					if("100"==data.code) {
						msg = 'success!';
					}
					$("#msgModal .modal-body").html(msg);
					$("#msgModal").modal();
					$('#blog')[0].reset();
					$('#summernote').summernote('code', '')
				});
				
			};
		});
		angular.element(document).ready(function() {
			angular.bootstrap(document.getElementById("sApp"), ['sApp']);
		});
		
	</script>
</body>
</html>