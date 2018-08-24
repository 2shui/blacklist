function getURLParameter(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
}
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
			url:'http://g.itblacklist.cn/feedback/add',
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
			url:'http://g.itblacklist.cn/topic/add',
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
				setTimeout("window.location.href='"+toUrl+"'", 3000);
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
	
	$scope.details = function(i) {
		var createTime = undefined;
		for (var n = 0; n < $scope.list.length; n++) {
			if ($scope.list[n].ref == i) {
				createTime = new Date($scope.list[n].createTime);
				break;
			}
		}
		for (var n = 0; n < $scope.hot.length; n++) {
			if ($scope.hot[n].ref == i) {
				createTime = new Date($scope.hot[n].createTime);
				break;
			}
		}
		
		var year = createTime.getFullYear();
		var month = createTime.getMonth() + 1;
		var day = createTime.getDate();
		var timePath = '/' + year + (month < 10 ? '0' + month : month) + (day < 10 ? '0' + day : day) + '/';
		window.location.href = encodeURI('/detail' + timePath + i + '.html');
		//window.location.href = encodeURI('/details/' + i);
	}
	
	// topic.s
	$scope.kw = getURLParameter('wd');
	$scope.list = [];
	$http({
		method:'post',
		url:'http://g.itblacklist.cn/topic/s',
		data:{'tsno':new Date().getTime(),'key':$scope.kw},
		headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
		transformRequest:function(data){return $.param(data);}
	}).success(function(data){
		console.log(data);
		if("100"==data.code) {
			var res = data.response;
			for(var i=0;i<res.length;i++) {
				var topic = {};
				topic.ref = res[i].id;
				topic.sketch = res[i].sketch;
				topic.company = res[i].company;
				topic.createTime = res[i].createTime;
				$scope.list[i] = topic;
			}
		}
	});
	// topic.hot
	$scope.hot = [];
	$http({
		method:'post',
		url:'http://g.itblacklist.cn/topic/hot',
		data:{'tsno':new Date().getTime()},
		headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
		transformRequest:function(data){return $.param(data);}
	}).success(function(data){
		console.log(data);
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
				$scope.hot[i] = topic;
			}
		}
	});
});