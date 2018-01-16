var myApp = angular.module('myApp',[]);
myApp.controller('myCtrl', function($scope, $http) {
	$scope.citys = citys;
	$scope.search = function() {
		if(typeof($scope.name) != "undefined") {
			window.location.href=encodeURI('/s?wd='+$scope.name);
		}
	}
	// feedback.add
	$scope.feedback = function() {
		var message = $('.bt-md').data('markdown').parseContent();
		$http({
			method:'post',
			url:'/feedback/add',
			data:{'tsno':new Date().getTime(),'content':message,'contact':$scope.vm.contact},
			headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
			transformRequest:function(data){return $.param(data);}
		}).success(function(data){
			var msg = data.desc;
			if("100"==data.code) {
				msg = 'success!';
			}
			$("#feedbackModal").modal('hide');
			$("#msgModal .modal-body").html(msg);
			$("#msgModal").modal();
			$('#feedback-add')[0].reset();
		}).error(function(){
			$("#myModal").modal('hide');
			$("#msgModal .modal-body").html('提交失败');
			$("#msgModal").modal();
		});
	}
	// topic.add
	$scope.submit = function() {
		var message = $('.bt-md').data('markdown').parseContent();
		$http({
			method:'post',
			url:'/topic/add',
			data:{'tsno':new Date().getTime(),'city':t,'company':$scope.vm.company,
					'sketch':$scope.vm.sketch,'intro':message,'contact':$scope.vm.contact},
			headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
			transformRequest:function(data){return $.param(data);}
		}).success(function(data){
			var msg = data.desc;
			if("100"==data.code) {
				msg = 'success!';
				var createTime = new Date(data.response.createTime);
				var year = createTime.getFullYear();
				var month = createTime.getMonth()+1;
				var day = createTime.getDate();
				var timePath = '/'+year+(month<10?'0'+month:month)+(day<10?'0'+day:day)+'/'
				var toUrl = 'http://www.itblacklist.cn/detail'+timePath+data.response.id+'.html';
				setTimeout("window.location.href="+toUrl, 3000);
			}
			$("#myModal").modal('hide');
			$("#msgModal .modal-body").html(msg);
			$("#msgModal").modal();
			$('#topic-add')[0].reset();
		}).error(function(){
			$("#myModal").modal('hide');
			$("#msgModal .modal-body").html('提交失败');
			$("#msgModal").modal();
		});
	}
	
	
	$scope.t = getURLID();
	$scope.topic = {};
	$http({
		method:'post',
		url:'/topic/details',
		data:{'tsno':new Date().getTime(),'id':$scope.t},
		headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
		transformRequest:function(data){return $.param(data);}
	}).success(function(data){
		if("100"==data.code) {
			$scope.topic = data.response;
		} else if("404"==data.code) {
			alert("无效的ID");
			window.location.href=encodeURI('/');
		}
	});
	$scope.list = [];
	$http({
		method:'post',
		url:'/reply/comments',
		data:{'tsno':new Date().getTime(),'id':$scope.t},
		headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
		transformRequest:function(data){return $.param(data);}
	}).success(function(data){
		if("100"==data.code) {
			var res = data.response;
			for(var i=0;i<res.length;i++) {
				var comment = {};
				comment.id = res[i].id;
				comment.citySN = res[i].citySN;
				comment.time = res[i].time;
				comment.intro = res[i].intro;
				comment.upNum = res[i].upNum;
				comment.downNum = res[i].downNum;
				$scope.list[i] = comment;
			}
		}
	});
	$scope.associated = []
	$http({
		method:'post',
		url:'/topic/associated',
		data:{'tsno':new Date().getTime(),'id':$scope.t},
		headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
		transformRequest:function(data){return $.param(data);}
	}).success(function(data){
		if("100"==data.code) {
			var res = data.response;
			for(var i=0;i<res.length;i++) {
				var topic = {};
				topic.ref = res[i].id;
				topic.sketch = res[i].sketch;
				topic.company = res[i].company;
				topic.accessNum = res[i].accessNum;
				topic.city = res[i].city;
				topic.createTime = res[i].createTime;
				topic.path = res[i].shortPath;
				$scope.associated[i] = topic;
			}
		}
	});
	
	$scope.reply = function(){
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
	$scope.up = function(t) {
		up(t);
		for(var i=0;i<$scope.list.length;i++){
			if(t==$scope.list[i].id) {
				$scope.list[i].upNum += 1;
				break;
			}
		}
	}
	$scope.down = function(t) {
		down(t);
		for(var i=0;i<$scope.list.length;i++){
			if(t==$scope.list[i].id) {
				$scope.list[i].downNum += 1;
				break;
			}
		}
	}
}).filter(
	'to_trusted', ['$sce', function ($sce) {
		return function(text){
			var el = $('<div></div>');
			el.html(text);
			var _list = $("[href^='http://']",el);
			$.each(_list, function() {
				var _url = $(this).attr('href');
				if(!testStr(_url)) {
					console.log(_url);
					$(this).attr('href', 'http://www.1.com/');
				}
			});
			_list = $("[src^='http://']",el);
			$.each(_list, function() {
				var _url = $(this).attr('src');
				if(!testStr(_url)) {
					console.log(_url);
					$(this).attr('src', 'http://www.1.com/');
				}
			});
			return $sce.trustAsHtml(el.html());
			
			//return $sce.trustAsHtml(text);
		}
	}]
);
function testStr(url) {
	return url.indexOf('itblacklist.cn') != -1 || (new RegExp("jpg$")).test(url) || (new RegExp("png$")).test(url) || (new RegExp("gif$")).test(url) || (new RegExp("jpeg$")).test(url);
}