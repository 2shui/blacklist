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
		var message = $('.feedback-ta').data('markdown').parseContent();
		$http({
			method:'post',
			url:'/feedback/add',
			data:{'tsno':new Date().getTime(),'content':message,'contact':$scope.vm.fbContact},
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
});